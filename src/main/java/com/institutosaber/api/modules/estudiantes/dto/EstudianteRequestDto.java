package com.institutosaber.api.modules.estudiantes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

public record EstudianteRequestDto(
        @Valid @NotNull PersonaRequestDto persona,
        @Size(max = 5) String grupoSanguineo,
        @Size(max = 200) String condicionEspecial,
        @Size(max = 200) String institucionProcedencia,
        @Size(max = 300) String observaciones) {
}
