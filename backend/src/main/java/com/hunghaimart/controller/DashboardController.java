package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.entity.DonHang;
import com.hunghaimart.repository.DonHangRepository;
import com.hunghaimart.repository.KhachHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DonHangRepository donHangRepository;
    private final KhachHangRepository khachHangRepository;

    @GetMapping("/tong-quan")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getTongQuan() {
        List<DonHang> allOrders = donHangRepository.findAll();
        
        LocalDate today = LocalDate.now();
        BigDecimal doanhThuHomNay = BigDecimal.ZERO;
        int donHangMoi = 0;
        BigDecimal tongDoanhThu = BigDecimal.ZERO;

        for (DonHang dh : allOrders) {
            tongDoanhThu = tongDoanhThu.add(dh.getTongTien());
            
            if (dh.getNgayTao() != null && dh.getNgayTao().toLocalDate().isEqual(today)) {
                doanhThuHomNay = doanhThuHomNay.add(dh.getTongTien());
                donHangMoi++;
            }
        }

        BigDecimal giaTriTrungBinh = BigDecimal.ZERO;
        if (!allOrders.isEmpty()) {
            giaTriTrungBinh = tongDoanhThu.divide(BigDecimal.valueOf(allOrders.size()), 0, RoundingMode.HALF_UP);
        }

        Map<String, Object> data = new HashMap<>();
        data.put("doanhThuHomNay", doanhThuHomNay);
        data.put("donHangMoi", donHangMoi);
        data.put("khachHangMoi", khachHangRepository.count());
        data.put("giaTriDonTrungBinh", giaTriTrungBinh);
        
        return ResponseEntity.ok(ApiResponse.success("Lấy tổng quan thành công", data));
    }
}
