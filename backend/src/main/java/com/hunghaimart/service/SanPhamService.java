package com.hunghaimart.service;

import com.hunghaimart.dto.SanPhamRequest;
import com.hunghaimart.entity.ChiNhanh;
import com.hunghaimart.entity.DanhMuc;
import com.hunghaimart.entity.NhaCungCap;
import com.hunghaimart.entity.SanPham;
import com.hunghaimart.exception.BusinessException;
import com.hunghaimart.exception.ResourceNotFoundException;
import com.hunghaimart.repository.ChiNhanhRepository;
import com.hunghaimart.repository.DanhMucRepository;
import com.hunghaimart.repository.NhaCungCapRepository;
import com.hunghaimart.repository.SanPhamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hunghaimart.entity.NguoiDung;
import com.hunghaimart.security.CustomUserDetails;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SanPhamService {
    
    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final ChiNhanhRepository chiNhanhRepository;
    private final NhaCungCapRepository nhaCungCapRepository;

    public List<SanPham> getAll() {
        return sanPhamRepository.findAll();
    }

    public List<SanPham> getAll(CustomUserDetails userDetails, Long chiNhanhId) {
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

    public SanPham getById(Long id) {
        return sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));
    }

    @Transactional
    public SanPham create(SanPhamRequest request, CustomUserDetails userDetails) {
        DanhMuc danhMuc = null;
        if (request.getDanhMucId() != null) {
            danhMuc = danhMucRepository.findById(request.getDanhMucId())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        }

        ChiNhanh chiNhanh = null;
        if (userDetails != null && userDetails.getNguoiDung() != null) {
            NguoiDung user = userDetails.getNguoiDung();
            if (!"ADMIN".equals(user.getVaiTro()) && user.getChiNhanh() != null) {
                chiNhanh = user.getChiNhanh();
            } else if (request.getChiNhanhId() != null) {
                chiNhanh = chiNhanhRepository.findById(request.getChiNhanhId())
                    .orElseThrow(() -> new ResourceNotFoundException("Chi nhánh không tồn tại"));
            }
        } else if (request.getChiNhanhId() != null) {
            chiNhanh = chiNhanhRepository.findById(request.getChiNhanhId())
                .orElseThrow(() -> new ResourceNotFoundException("Chi nhánh không tồn tại"));
        }

        NhaCungCap nhaCungCap = null;
        if (request.getNhaCungCapId() != null) {
            nhaCungCap = nhaCungCapRepository.findById(request.getNhaCungCapId())
                .orElse(null);
        }

        if (request.getGiaBan().compareTo(request.getGiaVon()) < 0) {
            throw new BusinessException("Giá bán không được nhỏ hơn giá vốn");
        }

        SanPham sanPham = SanPham.builder()
                .tenSanPham(request.getTenSanPham())
                .maSanPham(request.getMaSanPham())
                .maVach(request.getMaVach())
                .moTa(request.getMoTa())
                .danhMuc(danhMuc)
                .chiNhanh(chiNhanh)
                .nhaCungCap(nhaCungCap)
                .giaBan(request.getGiaBan())
                .giaVon(request.getGiaVon())
                .soLuongTon(0)
                .mucTonToiThieu(request.getMucTonToiThieu() != null ? request.getMucTonToiThieu() : 0)
                .donVi(request.getDonVi())
                .anhUrl(request.getAnhUrl())
                .hoatDong(request.getHoatDong() != null ? request.getHoatDong() : true)
                .build();
        
        try {
            return sanPhamRepository.save(sanPham);
        } catch (Exception e) {
            throw new BusinessException("Mã sản phẩm đã tồn tại hoặc dữ liệu không hợp lệ");
        }
    }

    @Transactional
    public SanPham update(Long id, SanPhamRequest request) {
        SanPham sanPham = getById(id);
        
        if (request.getGiaBan().compareTo(request.getGiaVon()) < 0) {
            throw new BusinessException("Giá bán không được nhỏ hơn giá vốn");
        }

        DanhMuc danhMuc = null;
        if (request.getDanhMucId() != null) {
            danhMuc = danhMucRepository.findById(request.getDanhMucId())
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        }

        NhaCungCap nhaCungCap = null;
        if (request.getNhaCungCapId() != null) {
            nhaCungCap = nhaCungCapRepository.findById(request.getNhaCungCapId())
                .orElse(null);
        }

        sanPham.setTenSanPham(request.getTenSanPham());
        sanPham.setMaSanPham(request.getMaSanPham());
        sanPham.setMaVach(request.getMaVach());
        sanPham.setMoTa(request.getMoTa());
        sanPham.setDanhMuc(danhMuc);
        sanPham.setNhaCungCap(nhaCungCap);
        sanPham.setGiaBan(request.getGiaBan());
        sanPham.setGiaVon(request.getGiaVon());
        sanPham.setMucTonToiThieu(request.getMucTonToiThieu() != null ? request.getMucTonToiThieu() : 0);
        sanPham.setDonVi(request.getDonVi());
        sanPham.setAnhUrl(request.getAnhUrl());
        
        try {
            return sanPhamRepository.save(sanPham);
        } catch (Exception e) {
            throw new BusinessException("Mã sản phẩm đã tồn tại");
        }
    }

    @Transactional
    public void delete(Long id) {
        SanPham sanPham = getById(id);
        if (sanPham.getSoLuongTon() > 0) {
            throw new BusinessException("Không thể xóa sản phẩm đang còn tồn kho");
        }
        sanPhamRepository.delete(sanPham);
    }
}
