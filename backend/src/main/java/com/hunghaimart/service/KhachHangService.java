package com.hunghaimart.service;

import com.hunghaimart.dto.KhachHangRequest;
import com.hunghaimart.entity.KhachHang;
import com.hunghaimart.exception.ResourceNotFoundException;
import com.hunghaimart.repository.KhachHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KhachHangService {
    private final KhachHangRepository khachHangRepository;

    public List<KhachHang> getAll() {
        return khachHangRepository.findAll();
    }

    public KhachHang getById(Long id) {
        return khachHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khách hàng"));
    }

    public KhachHang create(KhachHangRequest request) {
        KhachHang khachHang = KhachHang.builder()
                .hoTen(request.getHoTen())
                .soDienThoai(request.getSoDienThoai())
                .diaChi(request.getDiaChi())
                .build();
        return khachHangRepository.save(khachHang);
    }
}
