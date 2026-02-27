package com.institutosaber.api.modules.estudiantes.service;

import com.institutosaber.api.modules.estudiantes.dto.*;
import com.institutosaber.api.shared.PageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstudiantesService {

    // ── Estudiantes ──────────────────────────────────────────────────────────
    PageResponse<EstudianteResumenDto> buscarEstudiantes(String termino, Pageable pageable);

    EstudianteResponseDto obtenerEstudiante(Long id);

    EstudianteResponseDto crearEstudiante(EstudianteRequestDto dto);

    EstudianteResponseDto actualizarEstudiante(Long id, EstudianteRequestDto dto);

    void desactivarEstudiante(Long id);

    // ── Acudientes ───────────────────────────────────────────────────────────
    AcudienteResponseDto obtenerAcudiente(Long id);

    AcudienteResponseDto crearAcudiente(AcudienteRequestDto dto);

    AcudienteResponseDto actualizarAcudiente(Long id, AcudienteRequestDto dto);

    List<AcudienteResponseDto> buscarAcudientes(String termino);

    // ── Matrículas ───────────────────────────────────────────────────────────
    PageResponse<MatriculaResponseDto> listarMatriculas(Long anioLectivoId, Long gradoId, String estado,
            Pageable pageable);

    MatriculaResponseDto obtenerMatricula(Long id);

    List<MatriculaResponseDto> historialEstudiante(Long estudianteId);

    MatriculaResponseDto matricularEstudiante(MatriculaRequestDto dto);

    MatriculaResponseDto actualizarMatricula(Long id, MatriculaRequestDto dto);

    void cambiarEstadoMatricula(Long id, String nuevoEstado);
}
