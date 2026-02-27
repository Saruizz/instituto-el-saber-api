package com.institutosaber.api.modules.academico.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PeriodoRequestDto(
        @NotNull(message = "El año lectivo es obligatorio") Long anioLectivoId,
        @NotNull @Min(1) @Max(4) Integer numeroPeriodo,
        @NotBlank @Size(max = 80) String nombre,
        @NotNull LocalDate fechaInicio,
        @NotNull LocalDate fechaFin) {
}
