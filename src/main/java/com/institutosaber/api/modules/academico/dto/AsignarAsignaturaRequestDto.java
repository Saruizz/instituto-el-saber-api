package com.institutosaber.api.modules.academico.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record AsignarAsignaturaRequestDto(
        @NotNull(message = "La asignatura es obligatoria") Long asignaturaId,
        @NotNull(message = "La intensidad horaria es obligatoria") @Min(value = 1, message = "La intensidad mínima es 1 hora") @Max(value = 40, message = "La intensidad máxima es 40 horas") Integer intensidadHorariaSemanal) {
}
