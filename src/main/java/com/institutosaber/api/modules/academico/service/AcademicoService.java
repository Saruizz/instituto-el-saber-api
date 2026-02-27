package com.institutosaber.api.modules.academico.service;

import com.institutosaber.api.modules.academico.dto.*;
import com.institutosaber.api.shared.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AcademicoService {

    // ── Niveles ──
    List<NivelEducativoResponseDto> listarNiveles();

    // ── Grados ──
    List<GradoResponseDto> listarGrados();

    List<GradoResponseDto> listarGradosPorNivel(Long nivelId);

    GradoResponseDto crearGrado(GradoRequestDto dto);

    GradoResponseDto actualizarGrado(Long id, GradoRequestDto dto);

    void eliminarGrado(Long id);

    // ── Áreas ──
    List<AreaResponseDto> listarAreasActivas();

    PageResponse<AreaResponseDto> buscarAreas(String nombre, Pageable pageable);

    AreaResponseDto obtenerArea(Long id);

    AreaResponseDto crearArea(AreaRequestDto dto);

    AreaResponseDto actualizarArea(Long id, AreaRequestDto dto);

    void desactivarArea(Long id);

    // ── Asignaturas ──
    List<AsignaturaResponseDto> listarAsignaturasActivas();

    List<AsignaturaResponseDto> listarAsignaturasPorArea(Long areaId);

    PageResponse<AsignaturaResponseDto> buscarAsignaturas(String nombre, Pageable pageable);

    AsignaturaResponseDto obtenerAsignatura(Long id);

    AsignaturaResponseDto crearAsignatura(AsignaturaRequestDto dto);

    AsignaturaResponseDto actualizarAsignatura(Long id, AsignaturaRequestDto dto);

    void desactivarAsignatura(Long id);

    // ── Grado-Asignaturas ──
    List<GradoAsignaturaResponseDto> listarAsignaturasPorGrado(Long gradoId);

    GradoAsignaturaResponseDto asignarAsignaturaAGrado(Long gradoId, AsignarAsignaturaRequestDto dto);

    void quitarAsignaturaDeGrado(Long gradoId, Long asignaturaId);

    GradoAsignaturaResponseDto actualizarIntensidadHoraria(Long id, Integer intensidad);

    // ── Años Lectivos ──
    List<AnioLectivoResponseDto> listarAniosLectivos();

    AnioLectivoResponseDto obtenerAnioLectivo(Long id);

    AnioLectivoResponseDto obtenerAnioLectivoActivo();

    AnioLectivoResponseDto crearAnioLectivo(AnioLectivoRequestDto dto);

    AnioLectivoResponseDto actualizarAnioLectivo(Long id, AnioLectivoRequestDto dto);

    void activarAnioLectivo(Long id);

    // ── Periodos ──
    List<PeriodoResponseDto> listarPeriodos(Long anioLectivoId);

    PeriodoResponseDto obtenerPeriodo(Long id);

    PeriodoResponseDto crearPeriodo(PeriodoRequestDto dto);

    PeriodoResponseDto actualizarPeriodo(Long id, PeriodoRequestDto dto);

    void activarPeriodo(Long id);

    void crearPeriodosDefecto(Long anioLectivoId);
}
