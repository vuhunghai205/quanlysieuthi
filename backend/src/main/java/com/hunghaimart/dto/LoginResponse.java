package com.hunghaimart.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String accessToken;
    private String tokenType;
    private long expiresIn;
    private UserDto user;

    @Data
    @Builder
    public static class UserDto {
        private Long id;
        private String fullName;
        private String email;
        private String role;
        private Long branch;
    }
}
