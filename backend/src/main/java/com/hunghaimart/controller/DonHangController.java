package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.DonHangRequest;
import com.hunghaimart.entity.DonHang;
import com.hunghaimart.service.DonHangService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/don-hang")
@RequiredArgsConstructor
public class DonHangController {
    
    private final DonHangService donHangService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DonHang>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách đơn hàng thành công", donHangService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DonHang>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Lấy đơn hàng thành công", donHangService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DonHang>> create(
            @Valid @RequestBody DonHangRequest request, 
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Tạo đơn hàng thành công", donHangService.create(request, userDetails.getUsername())));
    }
}
