package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.TonKhoRequest;
import com.hunghaimart.entity.GiaoDichTonKho;
import com.hunghaimart.entity.SanPham;
import com.hunghaimart.security.CustomUserDetails;
import com.hunghaimart.service.TonKhoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ton-kho")
@RequiredArgsConstructor
public class TonKhoController {

    private final TonKhoService tonKhoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<SanPham>>> getTonKho(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) Long chiNhanhId) {
        return ResponseEntity.ok(ApiResponse.success("Lấy dữ liệu tồn kho thành công", tonKhoService.getTonKho(userDetails, chiNhanhId)));
    }

    @GetMapping("/sap-het")
    public ResponseEntity<ApiResponse<List<SanPham>>> getSapHet(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) Long chiNhanhId) {
        return ResponseEntity.ok(ApiResponse.success("Lấy sản phẩm sắp hết thành công", tonKhoService.getSapHet(userDetails, chiNhanhId)));
    }

    @GetMapping("/giao-dich")
    public ResponseEntity<ApiResponse<List<GiaoDichTonKho>>> getGiaoDich(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestParam(required = false) Long chiNhanhId) {
        return ResponseEntity.ok(ApiResponse.success("Lấy lịch sử giao dịch thành công", tonKhoService.getGiaoDich(userDetails, chiNhanhId)));
    }

    @PostMapping("/nhap")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<GiaoDichTonKho>> nhapKho(
            @Valid @RequestBody TonKhoRequest request, 
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Nhập kho thành công", tonKhoService.nhapKho(request, userDetails)));
    }

    @PostMapping("/dieu-chinh")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<GiaoDichTonKho>> dieuChinh(
            @Valid @RequestBody TonKhoRequest request, 
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(ApiResponse.success("Điều chỉnh tồn kho thành công", tonKhoService.dieuChinh(request, userDetails)));
    }
}
