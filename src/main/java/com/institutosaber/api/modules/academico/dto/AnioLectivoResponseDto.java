package com.institutosaber.api.modules.academico.dto;

import java.time.LocalDate;

public record AnioLectivoResponseDto(
        Long id,
        Integer anio,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Boolean activo,
        String descripcion,
        Integer totalPeriodos) {
}
