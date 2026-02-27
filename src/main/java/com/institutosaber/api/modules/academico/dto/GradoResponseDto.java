package com.institutosaber.api.modules.academico.dto;

public record GradoResponseDto(
        Long id,
        Long nivelId,
        String nivelNombre,
        String nombre,
        String descripcion,
        Integer orden) {
}
