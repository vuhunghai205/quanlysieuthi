package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.KhachHangRequest;
import com.hunghaimart.entity.KhachHang;
import com.hunghaimart.service.KhachHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/khach-hang")
@RequiredArgsConstructor
public class KhachHangController {
    
    private final KhachHangService khachHangService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<KhachHang>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách khách hàng thành công", khachHangService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<KhachHang>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy khách hàng thành công", khachHangService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<KhachHang>> create(@Valid @RequestBody KhachHangRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo khách hàng thành công", khachHangService.create(request)));
    }
}
