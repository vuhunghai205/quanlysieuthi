package com.hunghaimart.controller;

import com.hunghaimart.dto.ApiResponse;
import com.hunghaimart.dto.ChangePasswordRequest;
import com.hunghaimart.dto.LoginRequest;
import com.hunghaimart.dto.LoginResponse;
import com.hunghaimart.entity.NguoiDung;
import com.hunghaimart.exception.BusinessException;
import com.hunghaimart.repository.NguoiDungRepository;
import com.hunghaimart.security.CustomUserDetails;
import com.hunghaimart.security.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final NguoiDungRepository nguoiDungRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String jwt = tokenProvider.generateToken(authentication);

        NguoiDung user = userDetails.getNguoiDung();
        LoginResponse.UserDto userDto = LoginResponse.UserDto.builder()
                .id(user.getId())
                .fullName(user.getHoTen())
                .email(user.getEmail())
                .role(user.getVaiTro())
                .branch(user.getChiNhanh() != null ? user.getChiNhanh().getId() : null)
                .build();

        LoginResponse response = LoginResponse.builder()
                .accessToken(jwt)
                .tokenType("Bearer")
                .expiresIn(3600000L) // 1 hour based on env
                .user(userDto)
                .build();

        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", response));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<LoginResponse.UserDto>> getCurrentUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        NguoiDung user = userDetails.getNguoiDung();
        LoginResponse.UserDto userDto = LoginResponse.UserDto.builder()
                .id(user.getId())
                .fullName(user.getHoTen())
                .email(user.getEmail())
                .role(user.getVaiTro())
                .branch(user.getChiNhanh() != null ? user.getChiNhanh().getId() : null)
                .build();
        return ResponseEntity.ok(ApiResponse.success("Lấy thông tin thành công", userDto));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BusinessException("Mật khẩu xác nhận không khớp");
        }
        
        NguoiDung user = userDetails.getNguoiDung();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getMatKhau())) {
            throw new BusinessException("Mật khẩu hiện tại không đúng");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getMatKhau())) {
            throw new BusinessException("Mật khẩu mới không được trùng với mật khẩu cũ");
        }

        user.setMatKhau(passwordEncoder.encode(request.getNewPassword()));
        nguoiDungRepository.save(user);

        return ResponseEntity.ok(ApiResponse.success("Đổi mật khẩu thành công", null));
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {
        // JWT stateless so logout is handled on client by removing token
        return ResponseEntity.ok(ApiResponse.success("Đăng xuất thành công", null));
    }
}
