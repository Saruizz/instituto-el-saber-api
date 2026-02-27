package com.institutosaber.api.modules.estudiantes.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record MatriculaRequestDto(
        @NotNull(message = "El estudiante es obligatorio") Long estudianteId,
        @NotNull(message = "El año lectivo es obligatorio") Long anioLectivoId,
        @NotNull(message = "El grado es obligatorio") Long gradoId,
        Long acudienteId,
        @NotNull(message = "La fecha de matrícula es obligatoria") LocalDate fechaMatricula,
        @DecimalMin("0") @Digits(integer = 10, fraction = 2) BigDecimal valorMatricula,
        @Min(0) @Max(100) Integer descuentoPorcentaje,
        @Size(max = 200) String observaciones) {
}
