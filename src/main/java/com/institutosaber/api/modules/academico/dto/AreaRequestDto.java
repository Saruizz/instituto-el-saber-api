package com.institutosaber.api.modules.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AreaRequestDto(
        @NotBlank(message = "El nombre del área es obligatorio") @Size(max = 150) String nombre,
        @Size(max = 300) String descripcion) {
}
