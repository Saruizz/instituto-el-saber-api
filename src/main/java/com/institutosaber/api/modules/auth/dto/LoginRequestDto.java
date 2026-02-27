package com.institutosaber.api.modules.auth.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
        @NotBlank(message = "El nombre de usuario es obligatorio") @Size(max = 80, message = "El usuario no puede superar 80 caracteres") String username,

        @NotBlank(message = "La contraseña es obligatoria") String password) {
}
