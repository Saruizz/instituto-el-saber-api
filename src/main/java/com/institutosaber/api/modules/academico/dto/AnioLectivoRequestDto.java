package com.institutosaber.api.modules.academico.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record AnioLectivoRequestDto(
        @NotNull(message = "El año es obligatorio") @Min(value = 2000) Integer anio,
        @NotNull(message = "La fecha de inicio es obligatoria") LocalDate fechaInicio,
        @NotNull(message = "La fecha de fin es obligatoria") LocalDate fechaFin,
        @Size(max = 200) String descripcion) {
}
