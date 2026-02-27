package com.institutosaber.api.modules.estudiantes.dto;

import java.time.LocalDate;

public record PersonaResponseDto(
        Long id,
        String tipoDocumento,
        String numeroDocumento,
        String primerNombre,
        String segundoNombre,
        String primerApellido,
        String segundoApellido,
        String nombreCompleto,
        LocalDate fechaNacimiento,
        String genero,
        String municipioNacimiento,
        String departamentoNacimiento,
        String direccion,
        String municipioResidencia,
        String telefono,
        String email) {
}
