package com.institutosaber.api.modules.academico.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AsignaturaRequestDto(
        @NotNull(message = "El área es obligatoria") Long areaId,
        @NotBlank(message = "El nombre de la asignatura es obligatorio") @Size(max = 150) String nombre,
        @Size(max = 300) String descripcion) {
}
