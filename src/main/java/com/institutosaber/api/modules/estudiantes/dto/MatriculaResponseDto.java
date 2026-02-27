package com.institutosaber.api.modules.estudiantes.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record MatriculaResponseDto(
        Long id,
        String numeroMatricula,
        Long estudianteId,
        String estudianteNombre,
        String estudianteDocumento,
        String codigoEstudiante,
        Long anioLectivoId,
        Integer anio,
        Long gradoId,
        String gradoNombre,
        String nivelNombre,
        Long acudienteId,
        String acudienteNombre,
        LocalDate fechaMatricula,
        String estado,
        BigDecimal valorMatricula,
        Integer descuentoPorcentaje,
        BigDecimal valorConDescuento,
        String observaciones,
        LocalDateTime createdAt) {
}
