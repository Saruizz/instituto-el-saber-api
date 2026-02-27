package com.institutosaber.api.modules.auth.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegistrarUsuarioRequestDto {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 4, max = 80, message = "El usuario debe tener entre 4 y 80 caracteres")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener mínimo 8 caracteres")
    private String password;

    @Email(message = "El email no es válido")
    private String email;

    @NotBlank(message = "El rol es obligatorio")
    private String rol; // Ej: ROLE_DOCENTE, ROLE_SECRETARIA
}
