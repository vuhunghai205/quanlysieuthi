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

import com.hunghaimart.security.CustomUserDetails;
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

    public List<SanPham> getTonKho(CustomUserDetails userDetails, Long chiNhanhId) {
        if (userDetails != null && userDetails.getNguoiDung() != null) {
            NguoiDung user = userDetails.getNguoiDung();
            if ("ADMIN".equals(user.getVaiTro()) || user.getChiNhanh() == null) {
                if (chiNhanhId != null) {
                    return sanPhamRepository.findByChiNhanhId(chiNhanhId);
                }
                return sanPhamRepository.findAll();
            } else {
                return sanPhamRepository.findByChiNhanhId(user.getChiNhanh().getId());
            }
        }
        if (chiNhanhId != null) {
            return sanPhamRepository.findByChiNhanhId(chiNhanhId);
        }
        return sanPhamRepository.findAll();
    }

    public List<SanPham> getSapHet() {
        return sanPhamRepository.findAll().stream()
                .filter(sp -> sp.getSoLuongTon() <= sp.getMucTonToiThieu())
                .toList();
    }

    public List<SanPham> getSapHet(CustomUserDetails userDetails, Long chiNhanhId) {
        return getTonKho(userDetails, chiNhanhId).stream()
                .filter(sp -> sp.getSoLuongTon() <= sp.getMucTonToiThieu())
                .toList();
    }

    public List<GiaoDichTonKho> getGiaoDich() {
        return giaoDichTonKhoRepository.findAll();
    }

    public List<GiaoDichTonKho> getGiaoDich(CustomUserDetails userDetails, Long chiNhanhId) {
        if (userDetails != null && userDetails.getNguoiDung() != null) {
            NguoiDung user = userDetails.getNguoiDung();
            if ("ADMIN".equals(user.getVaiTro()) || user.getChiNhanh() == null) {
                if (chiNhanhId != null) {
                    return giaoDichTonKhoRepository.findByChiNhanhId(chiNhanhId);
                }
                return giaoDichTonKhoRepository.findAll();
            } else {
                return giaoDichTonKhoRepository.findByChiNhanhId(user.getChiNhanh().getId());
            }
        }
        if (chiNhanhId != null) {
            return giaoDichTonKhoRepository.findByChiNhanhId(chiNhanhId);
        }
        return giaoDichTonKhoRepository.findAll();
    }

    @Transactional
    public GiaoDichTonKho nhapKho(TonKhoRequest request, CustomUserDetails userDetails) {
        if (request.getSoLuong() <= 0) {
            throw new BusinessException("Số lượng nhập phải lớn hơn 0");
        }
        return taoGiaoDich(request, userDetails, "NHAP", request.getSoLuong());
    }

    @Transactional
    public GiaoDichTonKho dieuChinh(TonKhoRequest request, CustomUserDetails userDetails) {
        return taoGiaoDich(request, userDetails, "DIEU_CHINH", request.getSoLuong());
    }

    private GiaoDichTonKho taoGiaoDich(TonKhoRequest request, CustomUserDetails userDetails, String loai, int chenhLech) {
        SanPham sanPham = sanPhamRepository.findById(request.getSanPhamId())
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        
        NguoiDung nguoiDung = (userDetails != null && userDetails.getNguoiDung() != null)
                ? userDetails.getNguoiDung()
                : (request.getChiNhanhId() != null ? nguoiDungRepository.findAll().stream().findFirst().orElse(null) : null);

        Long targetBranchId = request.getChiNhanhId();
        if (nguoiDung != null && !"ADMIN".equals(nguoiDung.getVaiTro()) && nguoiDung.getChiNhanh() != null) {
            targetBranchId = nguoiDung.getChiNhanh().getId();
        } else if (targetBranchId == null && sanPham.getChiNhanh() != null) {
            targetBranchId = sanPham.getChiNhanh().getId();
        }

        ChiNhanh chiNhanh = (targetBranchId != null)
                ? chiNhanhRepository.findById(targetBranchId).orElse(null)
                : (sanPham.getChiNhanh() != null ? sanPham.getChiNhanh() : null);

        if (chiNhanh == null) {
            throw new BusinessException("Chi nhánh không tồn tại");
        }

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
