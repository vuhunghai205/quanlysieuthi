package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    @GetMapping("/tong-quan")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTongQuan() {
        // Mock data for dashboard
        Map<String, Object> data = new HashMap<>();
        data.put("doanhThuHomNay", new BigDecimal("24680000"));
        data.put("donHangMoi", 186);
        data.put("khachHangMoi", 2840);
        data.put("giaTriDonTrungBinh", new BigDecimal("428000"));
        return ResponseEntity.ok(ApiResponse.success("Lấy tổng quan thành công", data));
    }
}
