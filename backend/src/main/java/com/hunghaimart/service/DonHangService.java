package com.hunghaimart.service;

import com.hunghaimart.dto.ChiTietDonHangRequest;
import com.hunghaimart.dto.DonHangRequest;
import com.hunghaimart.entity.*;
import com.hunghaimart.exception.BusinessException;
import com.hunghaimart.exception.ResourceNotFoundException;
import com.hunghaimart.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DonHangService {
    
    private final DonHangRepository donHangRepository;
    private final ChiNhanhRepository chiNhanhRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final KhachHangRepository khachHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final GiaoDichTonKhoRepository giaoDichTonKhoRepository;

    public List<DonHang> getAll() {
        return donHangRepository.findAll();
    }

    public DonHang getById(Long id) {
        return donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
    }

    @Transactional
    public DonHang create(DonHangRequest request, String userEmail) {
        NguoiDung nguoiTao = nguoiDungRepository.findByEmail(userEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không hợp lệ"));
        
        ChiNhanh chiNhanh = chiNhanhRepository.findById(request.getChiNhanhId())
                .orElseThrow(() -> new ResourceNotFoundException("Chi nhánh không tồn tại"));

        // Xử lý Khách hàng (Tạo mới hoặc Lấy cũ)
        KhachHang khachHang = null;
        if (request.getSoDienThoai() != null && !request.getSoDienThoai().trim().isEmpty()) {
            Optional<KhachHang> khOpt = khachHangRepository.findBySoDienThoai(request.getSoDienThoai());
            if (khOpt.isPresent()) {
                khachHang = khOpt.get();
                // Cập nhật tên nếu có truyền
                if (request.getTenKhachHang() != null && !request.getTenKhachHang().trim().isEmpty()) {
                    khachHang.setHoTen(request.getTenKhachHang());
                }
            } else {
                khachHang = KhachHang.builder()
                        .hoTen(request.getTenKhachHang() != null && !request.getTenKhachHang().trim().isEmpty() ? request.getTenKhachHang() : "Khách hàng mới")
                        .soDienThoai(request.getSoDienThoai())
                        .diemTichLuy(0)
                        .build();
                khachHang = khachHangRepository.save(khachHang);
            }
        }

        DonHang donHang = DonHang.builder()
                .maDonHang("DH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .chiNhanh(chiNhanh)
                .nguoiTao(nguoiTao)
                .khachHang(khachHang)
                .trangThai("HOAN_THANH")
                .trangThaiThanhToan("DA_THANH_TOAN")
                .tongTien(BigDecimal.ZERO)
                .giamGia(BigDecimal.ZERO)
                .chiTietDonHangs(new ArrayList<>())
                .build();

        BigDecimal tongTienHang = BigDecimal.ZERO;

        for (ChiTietDonHangRequest ctReq : request.getChiTiet()) {
            SanPham sp = sanPhamRepository.findById(ctReq.getSanPhamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + ctReq.getSanPhamId()));
            
            if (!sp.getHoatDong()) {
                throw new BusinessException("Sản phẩm " + sp.getTenSanPham() + " đã ngừng kinh doanh");
            }
            
            if (sp.getSoLuongTon() < ctReq.getSoLuong()) {
                throw new BusinessException("Sản phẩm " + sp.getTenSanPham() + " không đủ tồn kho (Còn: " + sp.getSoLuongTon() + ")");
            }

            // Trừ tồn kho
            sp.setSoLuongTon(sp.getSoLuongTon() - ctReq.getSoLuong());
            sanPhamRepository.save(sp);

            // Ghi nhận xuất kho
            GiaoDichTonKho xuatKho = GiaoDichTonKho.builder()
                    .sanPham(sp)
                    .chiNhanh(chiNhanh)
                    .soLuong(-ctReq.getSoLuong())
                    .loaiGiaoDich("XUAT_BAN")
                    .ghiChu("Bán hàng đơn: " + donHang.getMaDonHang())
                    .nguoiTao(nguoiTao)
                    .build();
            giaoDichTonKhoRepository.save(xuatKho);

            BigDecimal tamTinh = sp.getGiaBan().multiply(BigDecimal.valueOf(ctReq.getSoLuong()));
            tongTienHang = tongTienHang.add(tamTinh);

            ChiTietDonHang chiTiet = ChiTietDonHang.builder()
                    .donHang(donHang)
                    .sanPham(sp)
                    .soLuong(ctReq.getSoLuong())
                    .giaBan(sp.getGiaBan())
                    .tamTinh(tamTinh)
                    .build();
            
            donHang.getChiTietDonHangs().add(chiTiet);
        }

        // Xử lý giảm giá từ điểm
        BigDecimal giamGia = BigDecimal.ZERO;
        if (khachHang != null && request.getDiemSuDung() != null && request.getDiemSuDung() > 0) {
            if (khachHang.getDiemTichLuy() < request.getDiemSuDung()) {
                throw new BusinessException("Khách hàng không đủ điểm tích lũy");
            }
            // Quy đổi 1 điểm = 1000 VNĐ
            giamGia = BigDecimal.valueOf(request.getDiemSuDung() * 200L);
            if (giamGia.compareTo(tongTienHang) > 0) {
                giamGia = tongTienHang; // Không giảm vượt quá giá trị đơn hàng
            }
            khachHang.setDiemTichLuy(khachHang.getDiemTichLuy() - request.getDiemSuDung());
        }

        BigDecimal tongTienThanhToan = tongTienHang.subtract(giamGia);
        donHang.setTongTien(tongTienThanhToan);
        donHang.setGiamGia(giamGia);

        // Tích điểm mới (Ví dụ: 10,000 VND = 1 điểm)
        if (khachHang != null && tongTienThanhToan.compareTo(BigDecimal.ZERO) > 0) {
            int diemMoi = tongTienThanhToan.divide(BigDecimal.valueOf(10000), 0, java.math.RoundingMode.DOWN).intValue();
            khachHang.setDiemTichLuy(khachHang.getDiemTichLuy() + diemMoi);
            khachHangRepository.save(khachHang);
        }

        return donHangRepository.save(donHang);
    }
}
