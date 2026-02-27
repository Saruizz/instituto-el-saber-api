package com.institutosaber.api.modules.academico.dto;

public record GradoAsignaturaResponseDto(
        Long id,
        Long gradoId,
        String gradoNombre,
        Long asignaturaId,
        String asignaturaNombre,
        String areaNombre,
        Integer intensidadHorariaSemanal) {
}
