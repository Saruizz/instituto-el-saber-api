package com.institutosaber.api.modules.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record GradoRequestDto(
        @NotNull(message = "El nivel es obligatorio") Long nivelId,
        @NotBlank(message = "El nombre es obligatorio") @Size(max = 80) String nombre,
        @Size(max = 200) String descripcion,
        @NotNull(message = "El orden es obligatorio") Integer orden) {
}
