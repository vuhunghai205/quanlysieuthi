package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.entity.NhaCungCap;
import com.hunghaimart.repository.NhaCungCapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/nha-cung-cap")
@RequiredArgsConstructor
public class NhaCungCapController {
    private final NhaCungCapRepository nhaCungCapRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<NhaCungCap>>> getAll() {
        return ResponseEntity.ok(ApiResponse.success("Lấy danh sách nhà cung cấp thành công", nhaCungCapRepository.findAll()));
    }
}
