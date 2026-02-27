package com.institutosaber.api.modules.estudiantes.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record PersonaRequestDto(
        @NotBlank @Size(max = 20) String tipoDocumento,
        @NotBlank @Size(max = 30) String numeroDocumento,
        @NotBlank @Size(max = 60) String primerNombre,
        @Size(max = 60) String segundoNombre,
        @NotBlank @Size(max = 60) String primerApellido,
        @Size(max = 60) String segundoApellido,
        @NotNull LocalDate fechaNacimiento,
        @NotBlank @Size(max = 20) String genero,
        @NotBlank @Size(max = 100) String municipioNacimiento,
        @NotBlank @Size(max = 100) String departamentoNacimiento,
        @Size(max = 200) String direccion,
        @Size(max = 100) String municipioResidencia,
        @Size(max = 30) String telefono,
        @Email @Size(max = 150) String email) {
}
