package com.institutosaber.api.modules.estudiantes.dto;

public record EstudianteResponseDto(
        Long id,
        String codigoEstudiante,
        PersonaResponseDto persona,
        Boolean activo,
        String grupoSanguineo,
        String condicionEspecial,
        String institucionProcedencia,
        String observaciones,
        Integer totalMatriculas) {
}
