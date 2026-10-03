package com.hunghaimart.service;

import com.hunghaimart.dto.TonKhoRequest;
import com.hunghaimart.entity.ChiNhanh;
import com.hunghaimart.entity.GiaoDichTonKho;
import com.hunghaimart.entity.NguoiDung;
import com.hunghaimart.entity.SanPham;
import com.hunghaimart.exception.BusinessException;
import com.hunghaimart.exception.ResourceNotFoundException;
import com.hunghaimart.repository.ChiNhanhRepository;
import com.hunghaimart.repository.GiaoDichTonKhoRepository;
import com.hunghaimart.repository.NguoiDungRepository;
import com.hunghaimart.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TonKhoService {
    
    private final SanPhamRepository sanPhamRepository;
    private final ChiNhanhRepository chiNhanhRepository;
    private final GiaoDichTonKhoRepository giaoDichTonKhoRepository;
    private final NguoiDungRepository nguoiDungRepository;

    public List<SanPham> getTonKho() {
        return sanPhamRepository.findAll();
    }

    public List<SanPham> getSapHet() {
        return sanPhamRepository.findAll().stream()
                .filter(sp -> sp.getSoLuongTon() <= sp.getMucTonToiThieu())
                .toList();
    }

    public List<GiaoDichTonKho> getGiaoDich() {
        return giaoDichTonKhoRepository.findAll();
    }

    @Transactional
    public GiaoDichTonKho nhapKho(TonKhoRequest request, String userEmail) {
        if (request.getSoLuong() <= 0) {
            throw new BusinessException("Số lượng nhập phải lớn hơn 0");
        }
        return taoGiaoDich(request, userEmail, "NHAP", request.getSoLuong());
    }

    @Transactional
    public GiaoDichTonKho dieuChinh(TonKhoRequest request, String userEmail) {
        return taoGiaoDich(request, userEmail, "DIEU_CHINH", request.getSoLuong());
    }

    private GiaoDichTonKho taoGiaoDich(TonKhoRequest request, String email, String loai, int chenhLech) {
        SanPham sanPham = sanPhamRepository.findById(request.getSanPhamId())
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        
        ChiNhanh chiNhanh = chiNhanhRepository.findById(request.getChiNhanhId())
                .orElseThrow(() -> new ResourceNotFoundException("Chi nhánh không tồn tại"));

        NguoiDung nguoiDung = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Người dùng không hợp lệ"));

        // Cập nhật tồn kho của sản phẩm
        int tonMoi = sanPham.getSoLuongTon() + chenhLech;
        if (tonMoi < 0) {
            throw new BusinessException("Không đủ tồn kho để thực hiện giao dịch này");
        }
        sanPham.setSoLuongTon(tonMoi);
        sanPhamRepository.save(sanPham);

        // Lưu lịch sử giao dịch
        GiaoDichTonKho giaoDich = GiaoDichTonKho.builder()
                .sanPham(sanPham)
                .chiNhanh(chiNhanh)
                .soLuong(chenhLech)
                .loaiGiaoDich(loai)
                .ghiChu(request.getGhiChu())
                .nguoiTao(nguoiDung)
                .build();
        
        return giaoDichTonKhoRepository.save(giaoDich);
    }
}
