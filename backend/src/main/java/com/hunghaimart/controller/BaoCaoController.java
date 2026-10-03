package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bao-cao")
@RequiredArgsConstructor
public class BaoCaoController {

    @GetMapping("/tong-quan")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBaoCaoTongQuan() {
        // Mock data for reports
        Map<String, Object> data = new HashMap<>();
        data.put("doanhThuThang", "1,200,000,000 VND");
        data.put("loiNhuan", "300,000,000 VND");
        data.put("donHoanTat", 4500);
        data.put("tyLeHuy", "2.5%");
        return ResponseEntity.ok(ApiResponse.success("Lấy báo cáo thành công", data));
    }
}
