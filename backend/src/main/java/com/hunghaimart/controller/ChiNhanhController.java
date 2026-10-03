package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.ChiNhanhRequest;
import com.hunghaimart.entity.ChiNhanh;
import com.hunghaimart.service.ChiNhanhService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chi-nhanh")
@RequiredArgsConstructor
public class ChiNhanhController {
    
    private final ChiNhanhService chiNhanhService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ChiNhanh>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách chi nhánh thành công", chiNhanhService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ChiNhanh>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy chi nhánh thành công", chiNhanhService.getById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChiNhanh>> create(@Valid @RequestBody ChiNhanhRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo chi nhánh thành công", chiNhanhService.create(request)));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChiNhanh>> update(@PathVariable Long id, @Valid @RequestBody ChiNhanhRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật chi nhánh thành công", chiNhanhService.update(id, request)));
    }

    @PatchMapping("/{id}/hoat-dong")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<ChiNhanh>> updateStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái thành công", chiNhanhService.updateStatus(id, body.get("hoatDong"))));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        chiNhanhService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa chi nhánh thành công", null));
    }
}
