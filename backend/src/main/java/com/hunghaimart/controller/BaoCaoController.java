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
import java.text.NumberFormat;
import java.time.LocalDate;
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
        
        LocalDate now = LocalDate.now();
        BigDecimal doanhThuThang = BigDecimal.ZERO;
        BigDecimal loiNhuan = BigDecimal.ZERO;
        int donHoanTat = 0;
        int donHuy = 0;

        for (DonHang dh : allOrders) {
            if (dh.getNgayTao() != null && dh.getNgayTao().getMonth() == now.getMonth() && dh.getNgayTao().getYear() == now.getYear()) {
                if ("HOAN_THANH".equals(dh.getTrangThai())) {
                    doanhThuThang = doanhThuThang.add(dh.getTongTien());
                    donHoanTat++;
                    
                    // Tính lợi nhuận
                    for (ChiTietDonHang ct : dh.getChiTietDonHangs()) {
                        BigDecimal giaVon = ct.getSanPham().getGiaVon();
                        BigDecimal tienVon = giaVon.multiply(BigDecimal.valueOf(ct.getSoLuong()));
                        loiNhuan = loiNhuan.add(ct.getTamTinh().subtract(tienVon));
                    }
                } else if ("HUY".equals(dh.getTrangThai())) {
                    donHuy++;
                }
            }
        }

        double tyLeHuy = 0.0;
        if (donHoanTat + donHuy > 0) {
            tyLeHuy = (double) donHuy / (donHoanTat + donHuy) * 100;
        }

        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        
        Map<String, Object> data = new HashMap<>();
        data.put("doanhThuThang", format.format(doanhThuThang));
        data.put("loiNhuan", format.format(loiNhuan));
        data.put("donHoanTat", donHoanTat);
        data.put("tyLeHuy", String.format("%.2f%%", tyLeHuy));
        
        return ResponseEntity.ok(ApiResponse.success("Lấy báo cáo thành công", data));
    }
}
