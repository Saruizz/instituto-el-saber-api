package com.institutosaber.api.modules.estudiantes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record AcudienteRequestDto(
        @Valid @NotNull PersonaRequestDto persona,
        @NotBlank @Size(max = 80) String parentesco,
        @Size(max = 100) String ocupacion,
        @Size(max = 200) String lugarTrabajo,
        @Size(max = 30) String telefonoTrabajo,
        Boolean esResponsableEconomico) {
}
