package com.institutosaber.api.modules.auth.dto;

import java.util.Set;

public record UsuarioResponseDto(
        Long id,
        String username,
        String email,
        Boolean activo,
        Set<String> roles) {
}
