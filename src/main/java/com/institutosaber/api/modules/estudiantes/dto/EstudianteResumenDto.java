package com.institutosaber.api.modules.estudiantes.dto;

/** Resumen compacto para listas y autocomplete */
public record EstudianteResumenDto(
        Long id,
        String codigoEstudiante,
        String nombreCompleto,
        String numeroDocumento,
        String tipoDocumento,
        Boolean activo) {
}
