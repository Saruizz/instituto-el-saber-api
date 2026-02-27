package com.institutosaber.api.modules.estudiantes.dto;

public record AcudienteResponseDto(
        Long id,
        PersonaResponseDto persona,
        String parentesco,
        String ocupacion,
        String lugarTrabajo,
        String telefonoTrabajo,
        Boolean esResponsableEconomico) {
}
