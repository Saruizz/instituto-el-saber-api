package com.institutosaber.api.modules.academico.dto;

public record AsignaturaResponseDto(
        Long id,
        Long areaId,
        String areaNombre,
        String nombre,
        String descripcion,
        Boolean activo) {
}
