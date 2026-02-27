package com.institutosaber.api.modules.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tipo;
    private long expiraEn;
    private Long userId;
    private String username;
    private String email;
    private Set<String> roles;
}
