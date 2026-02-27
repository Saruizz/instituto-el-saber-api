package com.institutosaber.api.modules.estudiantes.mapper;

import com.institutosaber.api.modules.estudiantes.dto.*;
import com.institutosaber.api.modules.estudiantes.entity.*;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EstudianteMapper {

    // ── Persona ──────────────────────────────────────────────────────────────
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombres", ignore = true) // columna legacy insertable=false
    @Mapping(target = "apellidos", ignore = true) // columna legacy insertable=false
    Persona toPersonaEntity(PersonaRequestDto dto);

    @Mapping(target = "nombreCompleto", expression = "java(p.getNombreCompleto())")
    PersonaResponseDto toPersonaDto(Persona p);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nombres", ignore = true)
    @Mapping(target = "apellidos", ignore = true)
    void updatePersonaFromDto(PersonaRequestDto dto, @MappingTarget Persona persona);

    // ── Acudiente ─────────────────────────────────────────────────────────────
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "nombreAcudiente", ignore = true) // columna legacy
    @Mapping(target = "telefonoAcudiente", ignore = true) // columna legacy
    Acudiente toAcudienteEntity(AcudienteRequestDto dto);

    AcudienteResponseDto toAcudienteDto(Acudiente a);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "nombreAcudiente", ignore = true)
    @Mapping(target = "telefonoAcudiente", ignore = true)
    void updateAcudienteFromDto(AcudienteRequestDto dto, @MappingTarget Acudiente acudiente);

    // ── Estudiante ────────────────────────────────────────────────────────────
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "codigoEstudiante", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "persona", ignore = true)
    @Mapping(target = "eps", ignore = true) // columna legacy
    @Mapping(target = "estrato", ignore = true) // columna legacy
    Estudiante toEstudianteEntity(EstudianteRequestDto dto);

    @Mapping(target = "totalMatriculas", ignore = true)
    EstudianteResponseDto toEstudianteDto(Estudiante e);

    @Mapping(target = "codigoEstudiante", source = "codigoEstudiante")
    @Mapping(target = "nombreCompleto", expression = "java(e.getPersona().getNombreCompleto())")
    @Mapping(target = "numeroDocumento", source = "persona.numeroDocumento")
    @Mapping(target = "tipoDocumento", source = "persona.tipoDocumento")
    EstudianteResumenDto toEstudianteResumen(Estudiante e);

    List<EstudianteResumenDto> toEstudianteResumenList(List<Estudiante> lista);

    // ── Matrícula ─────────────────────────────────────────────────────────────
    default MatriculaResponseDto toMatriculaDto(Matricula m) {
        if (m == null)
            return null;
        BigDecimal valor = m.getValorMatricula() != null ? m.getValorMatricula() : BigDecimal.ZERO;
        int desc = m.getDescuentoPorcentaje() != null ? m.getDescuentoPorcentaje() : 0;
        BigDecimal valorConDesc = valor
                .multiply(BigDecimal.ONE.subtract(
                        BigDecimal.valueOf(desc).divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP)))
                .setScale(2, RoundingMode.HALF_UP);

        return new MatriculaResponseDto(
                m.getId(),
                m.getNumeroMatricula(),
                m.getEstudiante().getId(),
                m.getEstudiante().getPersona().getNombreCompleto(),
                m.getEstudiante().getPersona().getNumeroDocumento(),
                m.getEstudiante().getCodigoEstudiante(),
                m.getAnioLectivo().getId(),
                m.getAnioLectivo().getAnio(),
                m.getGrado().getId(),
                m.getGrado().getNombre(),
                m.getGrado().getNivel().getNombre(),
                m.getAcudiente() != null ? m.getAcudiente().getId() : null,
                m.getAcudiente() != null ? m.getAcudiente().getPersona().getNombreCompleto() : null,
                m.getFechaMatricula(),
                m.getEstado(),
                m.getValorMatricula(),
                m.getDescuentoPorcentaje(),
                valorConDesc,
                m.getObservaciones(),
                m.getCreatedAt());
    }

    default List<MatriculaResponseDto> toMatriculaDtoList(List<Matricula> lista) {
        return lista.stream().map(this::toMatriculaDto).toList();
    }
}
