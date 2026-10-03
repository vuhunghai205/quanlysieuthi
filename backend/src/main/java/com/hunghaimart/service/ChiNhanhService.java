package com.hunghaimart.service;

import com.hunghaimart.dto.ChiNhanhRequest;
import com.hunghaimart.entity.ChiNhanh;
import com.hunghaimart.exception.BusinessException;
import com.hunghaimart.exception.ResourceNotFoundException;
import com.hunghaimart.repository.ChiNhanhRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiNhanhService {
    private final ChiNhanhRepository chiNhanhRepository;

    public List<ChiNhanh> getAll() {
        return chiNhanhRepository.findAll();
    }

    public ChiNhanh getById(Long id) {
        return chiNhanhRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy chi nhánh"));
    }

    @Transactional
    public ChiNhanh create(ChiNhanhRequest request) {
        if (chiNhanhRepository.findByMaChiNhanh(request.getMaChiNhanh()).isPresent()) {
            throw new BusinessException("Mã chi nhánh đã tồn tại");
        }
        ChiNhanh chiNhanh = ChiNhanh.builder()
                .tenChiNhanh(request.getTenChiNhanh())
                .maChiNhanh(request.getMaChiNhanh())
                .thanhPho(request.getThanhPho())
                .diaChi(request.getDiaChi())
                .soDienThoai(request.getSoDienThoai())
                .tenQuanLy(request.getTenQuanLy())
                .hoatDong(request.getHoatDong() != null ? request.getHoatDong() : true)
                .build();
        return chiNhanhRepository.save(chiNhanh);
    }

    @Transactional
    public ChiNhanh update(Long id, ChiNhanhRequest request) {
        ChiNhanh chiNhanh = getById(id);
        
        if (!chiNhanh.getMaChiNhanh().equals(request.getMaChiNhanh()) && 
            chiNhanhRepository.findByMaChiNhanh(request.getMaChiNhanh()).isPresent()) {
            throw new BusinessException("Mã chi nhánh đã tồn tại");
        }

        chiNhanh.setTenChiNhanh(request.getTenChiNhanh());
        chiNhanh.setMaChiNhanh(request.getMaChiNhanh());
        chiNhanh.setThanhPho(request.getThanhPho());
        chiNhanh.setDiaChi(request.getDiaChi());
        chiNhanh.setSoDienThoai(request.getSoDienThoai());
        chiNhanh.setTenQuanLy(request.getTenQuanLy());
        
        return chiNhanhRepository.save(chiNhanh);
    }

    @Transactional
    public ChiNhanh updateStatus(Long id, Boolean hoatDong) {
        ChiNhanh chiNhanh = getById(id);
        chiNhanh.setHoatDong(hoatDong);
        return chiNhanhRepository.save(chiNhanh);
    }

    @Transactional
    public void delete(Long id) {
        ChiNhanh chiNhanh = getById(id);
        try {
            chiNhanhRepository.delete(chiNhanh);
        } catch (Exception e) {
            throw new BusinessException("Không thể xóa chi nhánh vì đã có dữ liệu liên quan");
        }
    }
}
