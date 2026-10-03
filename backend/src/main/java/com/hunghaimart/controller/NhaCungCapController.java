package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.entity.NhaCungCap;
import com.hunghaimart.repository.NhaCungCapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/nha-cung-cap")
@RequiredArgsConstructor
public class NhaCungCapController {
    private final NhaCungCapRepository nhaCungCapRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NhaCungCap>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Thành công", nhaCungCapRepository.findAll()));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NhaCungCap>> create(@RequestBody NhaCungCap req) {
        return ResponseEntity.ok(ApiResponse.success("Thành công", nhaCungCapRepository.save(req)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NhaCungCap>> update(@PathVariable Long id, @RequestBody NhaCungCap req) {
        NhaCungCap ncc = nhaCungCapRepository.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy"));
        ncc.setTenNhaCungCap(req.getTenNhaCungCap());
        ncc.setSoDienThoai(req.getSoDienThoai());
        ncc.setDiaChi(req.getDiaChi());
        return ResponseEntity.ok(ApiResponse.success("Thành công", nhaCungCapRepository.save(ncc)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        nhaCungCapRepository.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa thành công", null));
    }
}
