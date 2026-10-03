package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.entity.ChiTietDonHang;
import com.hunghaimart.entity.DonHang;
import com.hunghaimart.repository.DonHangRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/api/bao-cao")
@RequiredArgsConstructor
public class BaoCaoController {
    
    private final DonHangRepository donHangRepository;

    @GetMapping("/tong-quan")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getBaoCaoTongQuan() {
        List<DonHang> allOrders = donHangRepository.findAll();
        
        LocalDate today = LocalDate.now();
        int currentWeek = today.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear());
        int currentMonth = today.getMonthValue();
        int currentYear = today.getYear();

        BigDecimal dtNgay = BigDecimal.ZERO, dtTuan = BigDecimal.ZERO, dtThang = BigDecimal.ZERO, dtNam = BigDecimal.ZERO;
        BigDecimal lnNgay = BigDecimal.ZERO, lnTuan = BigDecimal.ZERO, lnThang = BigDecimal.ZERO, lnNam = BigDecimal.ZERO;
        int donHoanTatNgay = 0, donHoanTatTuan = 0, donHoanTatThang = 0, donHoanTatNam = 0;
        int donHuyNgay = 0, donHuyTuan = 0, donHuyThang = 0, donHuyNam = 0;

        for (DonHang dh : allOrders) {
            if (dh.getNgayTao() == null) continue;
            LocalDate dhDate = dh.getNgayTao().toLocalDate();
            
            boolean isYear = dhDate.getYear() == currentYear;
            boolean isMonth = isYear && dhDate.getMonthValue() == currentMonth;
            boolean isWeek = isYear && dhDate.get(WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear()) == currentWeek;
            boolean isToday = dhDate.isEqual(today);

            if ("HOAN_THANH".equals(dh.getTrangThai())) {
                BigDecimal tongTien = dh.getTongTien();
                
                // Lợi nhuận = Tổng tiền (đã trừ điểm giảm giá) - (Giá vốn * số lượng)
                BigDecimal giaVonTong = BigDecimal.ZERO;
                for (ChiTietDonHang ct : dh.getChiTietDonHangs()) {
                    if (ct.getSanPham() != null && ct.getSanPham().getGiaVon() != null) {
                        giaVonTong = giaVonTong.add(ct.getSanPham().getGiaVon().multiply(BigDecimal.valueOf(ct.getSoLuong())));
                    }
                }
                BigDecimal loiNhuan = tongTien.subtract(giaVonTong);

                if (isYear) { dtNam = dtNam.add(tongTien); lnNam = lnNam.add(loiNhuan); donHoanTatNam++; }
                if (isMonth) { dtThang = dtThang.add(tongTien); lnThang = lnThang.add(loiNhuan); donHoanTatThang++; }
                if (isWeek) { dtTuan = dtTuan.add(tongTien); lnTuan = lnTuan.add(loiNhuan); donHoanTatTuan++; }
                if (isToday) { dtNgay = dtNgay.add(tongTien); lnNgay = lnNgay.add(loiNhuan); donHoanTatNgay++; }
            } else if ("HUY".equals(dh.getTrangThai())) {
                if (isYear) donHuyNam++;
                if (isMonth) donHuyThang++;
                if (isWeek) donHuyTuan++;
                if (isToday) donHuyNgay++;
            }
        }

        Map<String, Object> doanhThu = new HashMap<>();
        doanhThu.put("ngay", dtNgay); doanhThu.put("tuan", dtTuan); doanhThu.put("thang", dtThang); doanhThu.put("nam", dtNam);

        Map<String, Object> loiNhuan = new HashMap<>();
        loiNhuan.put("ngay", lnNgay); loiNhuan.put("tuan", lnTuan); loiNhuan.put("thang", lnThang); loiNhuan.put("nam", lnNam);

        Map<String, Object> donHoanTat = new HashMap<>();
        donHoanTat.put("ngay", donHoanTatNgay); donHoanTat.put("tuan", donHoanTatTuan); donHoanTat.put("thang", donHoanTatThang); donHoanTat.put("nam", donHoanTatNam);

        Map<String, Object> tyLeHuy = new HashMap<>();
        tyLeHuy.put("ngay", donHoanTatNgay + donHuyNgay > 0 ? String.format("%.2f%%", (double)donHuyNgay/(donHoanTatNgay+donHuyNgay)*100) : "0.00%");
        tyLeHuy.put("tuan", donHoanTatTuan + donHuyTuan > 0 ? String.format("%.2f%%", (double)donHuyTuan/(donHoanTatTuan+donHuyTuan)*100) : "0.00%");
        tyLeHuy.put("thang", donHoanTatThang + donHuyThang > 0 ? String.format("%.2f%%", (double)donHuyThang/(donHoanTatThang+donHuyThang)*100) : "0.00%");
        tyLeHuy.put("nam", donHoanTatNam + donHuyNam > 0 ? String.format("%.2f%%", (double)donHuyNam/(donHoanTatNam+donHuyNam)*100) : "0.00%");

        Map<String, Object> data = new HashMap<>();
        data.put("doanhThu", doanhThu);
        data.put("loiNhuan", loiNhuan);
        data.put("donHoanTat", donHoanTat);
        data.put("tyLeHuy", tyLeHuy);
        
        return ResponseEntity.ok(ApiResponse.success("Lấy báo cáo thành công", data));
    }
}
