package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.SanPhamRequest;
import com.hunghaimart.entity.SanPham;
import com.hunghaimart.service.SanPhamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/san-pham")
@RequiredArgsConstructor
public class SanPhamController {

    private final SanPhamService sanPhamService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SanPham>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách sản phẩm thành công", sanPhamService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SanPham>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy sản phẩm thành công", sanPhamService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SanPham>> create(@Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo sản phẩm thành công", sanPhamService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<SanPham>> update(@PathVariable Long id, @Valid @RequestBody SanPhamRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật sản phẩm thành công", sanPhamService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        sanPhamService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa sản phẩm thành công", null));
    }
}
