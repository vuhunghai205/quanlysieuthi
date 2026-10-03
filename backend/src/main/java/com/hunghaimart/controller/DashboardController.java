package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.entity.DonHang;
import com.hunghaimart.entity.KhachHang;
import com.hunghaimart.repository.DonHangRepository;
import com.hunghaimart.repository.KhachHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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
        List<KhachHang> allCustomers = khachHangRepository.findAll();
        
        LocalDate today = LocalDate.now();
        int currentWeek = today.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        BigDecimal doanhThuNgay = BigDecimal.ZERO;
        BigDecimal doanhThuTuan = BigDecimal.ZERO;
        BigDecimal doanhThuThang = BigDecimal.ZERO;
        BigDecimal doanhThuNam = BigDecimal.ZERO;

        int donHangNgay = 0, donHangTuan = 0, donHangThang = 0, donHangNam = 0;

        for (DonHang dh : allOrders) {
            if (dh.getNgayTao() == null) continue;
            LocalDate dhDate = dh.getNgayTao().toLocalDate();
            BigDecimal tien = dh.getTongTien();

            // Tính năm
            if (dhDate.getYear() == currentYear) {
                doanhThuNam = doanhThuNam.add(tien);
                donHangNam++;
                
                // Tính tháng
                if (dhDate.getMonthValue() == currentMonth) {
                    doanhThuThang = doanhThuThang.add(tien);
                    donHangThang++;
                }
                
                // Tính tuần
                int dhWeek = dhDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
                if (dhWeek == currentWeek) {
                    doanhThuTuan = doanhThuTuan.add(tien);
                    donHangTuan++;
                }
            }

            // Tính ngày
            if (dhDate.isEqual(today)) {
                doanhThuNgay = doanhThuNgay.add(tien);
                donHangNgay++;
            }
        }

        int khachNgay = 0, khachTuan = 0, khachThang = 0, khachNam = 0;
        for (KhachHang kh : allCustomers) {
            if (kh.getNgayTao() == null) continue;
            LocalDate khDate = kh.getNgayTao().toLocalDate();
            
            if (khDate.getYear() == currentYear) {
                khachNam++;
                if (khDate.getMonthValue() == currentMonth) khachThang++;
                if (khDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) == currentWeek) khachTuan++;
            }
            if (khDate.isEqual(today)) khachNgay++;
        }

        Map<String, Object> doanhThu = new HashMap<>();
        doanhThu.put("ngay", doanhThuNgay);
        doanhThu.put("tuan", doanhThuTuan);
        doanhThu.put("thang", doanhThuThang);
        doanhThu.put("nam", doanhThuNam);

        Map<String, Object> donHang = new HashMap<>();
        donHang.put("ngay", donHangNgay);
        donHang.put("tuan", donHangTuan);
        donHang.put("thang", donHangThang);
        donHang.put("nam", donHangNam);

        Map<String, Object> khachHang = new HashMap<>();
        khachHang.put("ngay", khachNgay);
        khachHang.put("tuan", khachTuan);
        khachHang.put("thang", khachThang);
        khachHang.put("nam", khachNam);

        Map<String, Object> data = new HashMap<>();
        data.put("doanhThu", doanhThu);
        data.put("donHang", donHang);
        data.put("khachHang", khachHang);
        
        return ResponseEntity.ok(ApiResponse.success("Lấy tổng quan thành công", data));
    }
}
