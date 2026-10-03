package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.NguoiDungRequest;
import com.hunghaimart.entity.NguoiDung;
import com.hunghaimart.service.NguoiDungService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/nguoi-dung")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class NguoiDungController {

    private final NguoiDungService nguoiDungService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NguoiDung>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách người dùng thành công", nguoiDungService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NguoiDung>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy người dùng thành công", nguoiDungService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<NguoiDung>> create(@Valid @RequestBody NguoiDungRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Tạo người dùng thành công", nguoiDungService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NguoiDung>> update(@PathVariable Long id, @Valid @RequestBody NguoiDungRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật người dùng thành công", nguoiDungService.update(id, request)));
    }

    @PatchMapping("/{id}/kich-hoat")
    public ResponseEntity<ApiResponse<NguoiDung>> updateStatus(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái thành công", nguoiDungService.updateStatus(id, body.get("kichHoat"))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        nguoiDungService.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Xóa người dùng thành công", null));
    }
}
