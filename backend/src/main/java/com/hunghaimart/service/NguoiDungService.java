package com.hunghaimart.service;

import com.hunghaimart.dto.NguoiDungRequest;
import com.hunghaimart.entity.ChiNhanh;
import com.hunghaimart.entity.NguoiDung;
import com.hunghaimart.exception.BusinessException;
import com.hunghaimart.exception.ResourceNotFoundException;
import com.hunghaimart.repository.NguoiDungRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NguoiDungService {
    private final NguoiDungRepository nguoiDungRepository;
    private final ChiNhanhService chiNhanhService;
    private final PasswordEncoder passwordEncoder;

    public List<NguoiDung> getAll() {
        return nguoiDungRepository.findAll();
    }

    public NguoiDung getById(Long id) {
        return nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));
    }

    @Transactional
    public NguoiDung create(NguoiDungRequest request) {
        if (nguoiDungRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email đã tồn tại");
        }

        ChiNhanh chiNhanh = null;
        if (request.getChiNhanhId() != null) {
            chiNhanh = chiNhanhService.getById(request.getChiNhanhId());
        }

        NguoiDung nguoiDung = NguoiDung.builder()
                .hoTen(request.getHoTen())
                .email(request.getEmail())
                .matKhau(passwordEncoder.encode(request.getMatKhau() != null ? request.getMatKhau() : "123456"))
                .soDienThoai(request.getSoDienThoai())
                .vaiTro(request.getVaiTro())
                .chiNhanh(chiNhanh)
                .kichHoat(request.getKichHoat() != null ? request.getKichHoat() : true)
                .build();
        return nguoiDungRepository.save(nguoiDung);
    }

    @Transactional
    public NguoiDung update(Long id, NguoiDungRequest request) {
        NguoiDung nguoiDung = getById(id);
        
        if (!nguoiDung.getEmail().equals(request.getEmail()) && 
            nguoiDungRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BusinessException("Email đã tồn tại");
        }

        ChiNhanh chiNhanh = null;
        if (request.getChiNhanhId() != null) {
            chiNhanh = chiNhanhService.getById(request.getChiNhanhId());
        }

        nguoiDung.setHoTen(request.getHoTen());
        nguoiDung.setEmail(request.getEmail());
        nguoiDung.setSoDienThoai(request.getSoDienThoai());
        nguoiDung.setVaiTro(request.getVaiTro());
        nguoiDung.setChiNhanh(chiNhanh);
        
        if (request.getMatKhau() != null && !request.getMatKhau().isEmpty()) {
            nguoiDung.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        }

        return nguoiDungRepository.save(nguoiDung);
    }

    @Transactional
    public NguoiDung updateStatus(Long id, Boolean kichHoat) {
        NguoiDung nguoiDung = getById(id);
        nguoiDung.setKichHoat(kichHoat);
        return nguoiDungRepository.save(nguoiDung);
    }

    @Transactional
    public void delete(Long id) {
        NguoiDung nguoiDung = getById(id);
        if ("ADMIN".equals(nguoiDung.getVaiTro())) {
            throw new BusinessException("Không thể xóa tài khoản ADMIN");
        }
        nguoiDungRepository.delete(nguoiDung);
    }
}
