package com.institutosaber.api.modules.academico.dto;

import java.time.LocalDate;

public record PeriodoResponseDto(
        Long id,
        Long anioLectivoId,
        Integer anio,
        Integer numeroPeriodo,
        String nombre,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Boolean activo) {
}
