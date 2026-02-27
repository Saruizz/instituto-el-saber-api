package com.institutosaber.api.modules.academico.mapper;

import com.institutosaber.api.modules.academico.dto.*;
import com.institutosaber.api.modules.academico.entity.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AcademicoMapper {

    // ── NivelEducativo ──
    NivelEducativoResponseDto toNivelDto(NivelEducativo entidad);

    List<NivelEducativoResponseDto> toNivelDtoList(List<NivelEducativo> lista);

    // ── Grado ──
    @Mapping(source = "nivel.id", target = "nivelId")
    @Mapping(source = "nivel.nombre", target = "nivelNombre")
    GradoResponseDto toGradoDto(Grado entidad);

    List<GradoResponseDto> toGradoDtoList(List<Grado> lista);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "nivel", ignore = true)
    Grado toGradoEntity(GradoRequestDto dto);

    // ── Area ──
    AreaResponseDto toAreaDto(Area entidad);

    List<AreaResponseDto> toAreaDtoList(List<Area> lista);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Area toAreaEntity(AreaRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateAreaFromDto(AreaRequestDto dto, @MappingTarget Area entidad);

    // ── Asignatura ──
    @Mapping(source = "area.id", target = "areaId")
    @Mapping(source = "area.nombre", target = "areaNombre")
    AsignaturaResponseDto toAsignaturaDto(Asignatura entidad);

    List<AsignaturaResponseDto> toAsignaturaDtoList(List<Asignatura> lista);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Asignatura toAsignaturaEntity(AsignaturaRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "area", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updateAsignaturaFromDto(AsignaturaRequestDto dto, @MappingTarget Asignatura entidad);

    // ── GradoAsignatura ──
    @Mapping(source = "grado.id", target = "gradoId")
    @Mapping(source = "grado.nombre", target = "gradoNombre")
    @Mapping(source = "asignatura.id", target = "asignaturaId")
    @Mapping(source = "asignatura.nombre", target = "asignaturaNombre")
    @Mapping(source = "asignatura.area.nombre", target = "areaNombre")
    GradoAsignaturaResponseDto toGradoAsignaturaDto(GradoAsignatura entidad);

    List<GradoAsignaturaResponseDto> toGradoAsignaturaDtoList(List<GradoAsignatura> lista);

    // ── AnioLectivo ──
    @Mapping(target = "totalPeriodos", ignore = true)
    AnioLectivoResponseDto toAnioLectivoDto(AnioLectivo entidad);

    List<AnioLectivoResponseDto> toAnioLectivoDtoList(List<AnioLectivo> lista);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    AnioLectivo toAnioLectivoEntity(AnioLectivoRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "activo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateAnioLectivoFromDto(AnioLectivoRequestDto dto, @MappingTarget AnioLectivo entidad);

    // ── Periodo ──
    @Mapping(source = "anioLectivo.id", target = "anioLectivoId")
    @Mapping(source = "anioLectivo.anio", target = "anio")
    PeriodoResponseDto toPeriodoDto(Periodo entidad);

    List<PeriodoResponseDto> toPeriodoDtoList(List<Periodo> lista);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anioLectivo", ignore = true)
    @Mapping(target = "activo", ignore = true)
    Periodo toPeriodoEntity(PeriodoRequestDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "anioLectivo", ignore = true)
    @Mapping(target = "activo", ignore = true)
    void updatePeriodoFromDto(PeriodoRequestDto dto, @MappingTarget Periodo entidad);
}
