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

        KhachHang khachHang = null;
        if (request.getKhachHangId() != null) {
            khachHang = khachHangRepository.findById(request.getKhachHangId())
                    .orElseThrow(() -> new ResourceNotFoundException("Khách hàng không tồn tại"));
        }

        DonHang donHang = DonHang.builder()
                .maDonHang("DH-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
                .chiNhanh(chiNhanh)
                .nguoiTao(nguoiTao)
                .khachHang(khachHang)
                .trangThai("HOAN_THANH")
                .trangThaiThanhToan("DA_THANH_TOAN")
                .tongTien(BigDecimal.ZERO)
                .chiTietDonHangs(new ArrayList<>())
                .build();

        BigDecimal tongTien = BigDecimal.ZERO;

        for (ChiTietDonHangRequest ctReq : request.getChiTiet()) {
            SanPham sp = sanPhamRepository.findById(ctReq.getSanPhamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại: " + ctReq.getSanPhamId()));
            
            if (!sp.getHoatDong()) {
                throw new BusinessException("Sản phẩm " + sp.getTenSanPham() + " đã ngừng kinh doanh");
            }
            
            if (sp.getSoLuongTon() < ctReq.getSoLuong()) {
                throw new BusinessException("Sản phẩm " + sp.getTenSanPham() + " không đủ số lượng tồn kho");
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
            tongTien = tongTien.add(tamTinh);

            ChiTietDonHang chiTiet = ChiTietDonHang.builder()
                    .donHang(donHang)
                    .sanPham(sp)
                    .soLuong(ctReq.getSoLuong())
                    .giaBan(sp.getGiaBan())
                    .tamTinh(tamTinh)
                    .build();
            
            donHang.getChiTietDonHangs().add(chiTiet);
        }

        donHang.setTongTien(tongTien);
        return donHangRepository.save(donHang);
    }
}
