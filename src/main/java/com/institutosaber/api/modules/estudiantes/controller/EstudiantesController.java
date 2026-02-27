package com.institutosaber.api.modules.estudiantes.controller;

import com.institutosaber.api.modules.estudiantes.dto.*;
import com.institutosaber.api.modules.estudiantes.service.EstudiantesService;
import com.institutosaber.api.shared.ApiResponse;
import com.institutosaber.api.shared.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
@Tag(name = "3. Estudiantes y Matrículas", description = "Gestión de estudiantes, acudientes y matrículas")
public class EstudiantesController {

    private final EstudiantesService service;

    // ─── ESTUDIANTES ──────────────────────────────────────────────────────────
    @GetMapping
    @Operation(summary = "Buscar estudiantes activos (paginado)")
    public ResponseEntity<ApiResponse<PageResponse<EstudianteResumenDto>>> buscar(
            @RequestParam(required = false) String termino,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size, Sort.by("persona.primerApellido").ascending());
        return ResponseEntity.ok(ApiResponse.ok(service.buscarEstudiantes(termino, pageable)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener datos completos de un estudiante")
    public ResponseEntity<ApiResponse<EstudianteResponseDto>> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerEstudiante(id)));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR','ROLE_SECRETARIA')")
    @Operation(summary = "Crear nuevo estudiante")
    public ResponseEntity<ApiResponse<EstudianteResponseDto>> crear(
            @Valid @RequestBody EstudianteRequestDto dto) {
        EstudianteResponseDto resp = service.crearEstudiante(dto);
        return ResponseEntity.created(URI.create("/api/v1/estudiantes/" + resp.id()))
                .body(ApiResponse.ok(resp, "Estudiante creado exitosamente"));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR','ROLE_SECRETARIA')")
    @Operation(summary = "Actualizar datos de un estudiante")
    public ResponseEntity<ApiResponse<EstudianteResponseDto>> actualizar(
            @PathVariable Long id, @Valid @RequestBody EstudianteRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarEstudiante(id, dto), "Estudiante actualizado"));
    }

    @PatchMapping("/{id}/desactivar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR')")
    @Operation(summary = "Desactivar un estudiante")
    public ResponseEntity<ApiResponse<Void>> desactivar(@PathVariable Long id) {
        service.desactivarEstudiante(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Estudiante desactivado"));
    }

    @GetMapping("/{id}/historial-matriculas")
    @Operation(summary = "Ver historial de matrículas de un estudiante")
    public ResponseEntity<ApiResponse<List<MatriculaResponseDto>>> historial(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.historialEstudiante(id)));
    }

    // ─── ACUDIENTES ───────────────────────────────────────────────────────────
    @GetMapping("/acudientes")
    @Operation(summary = "Buscar acudientes por nombre o documento")
    public ResponseEntity<ApiResponse<List<AcudienteResponseDto>>> buscarAcudientes(
            @RequestParam(required = false) String termino) {
        return ResponseEntity.ok(ApiResponse.ok(service.buscarAcudientes(termino)));
    }

    @GetMapping("/acudientes/{id}")
    @Operation(summary = "Obtener acudiente por ID")
    public ResponseEntity<ApiResponse<AcudienteResponseDto>> obtenerAcudiente(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerAcudiente(id)));
    }

    @PostMapping("/acudientes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR','ROLE_SECRETARIA')")
    @Operation(summary = "Crear acudiente")
    public ResponseEntity<ApiResponse<AcudienteResponseDto>> crearAcudiente(
            @Valid @RequestBody AcudienteRequestDto dto) {
        AcudienteResponseDto resp = service.crearAcudiente(dto);
        return ResponseEntity.created(URI.create("/api/v1/estudiantes/acudientes/" + resp.id()))
                .body(ApiResponse.ok(resp, "Acudiente creado exitosamente"));
    }

    @PutMapping("/acudientes/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR','ROLE_SECRETARIA')")
    @Operation(summary = "Actualizar acudiente")
    public ResponseEntity<ApiResponse<AcudienteResponseDto>> actualizarAcudiente(
            @PathVariable Long id, @Valid @RequestBody AcudienteRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarAcudiente(id, dto), "Acudiente actualizado"));
    }

    // ─── MATRÍCULAS ───────────────────────────────────────────────────────────
    @GetMapping("/matriculas")
    @Operation(summary = "Listar matrículas por año lectivo (con filtros y paginación)")
    public ResponseEntity<ApiResponse<PageResponse<MatriculaResponseDto>>> listarMatriculas(
            @RequestParam Long anioLectivoId,
            @RequestParam(required = false) Long gradoId,
            @RequestParam(required = false) String estado,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.ok(service.listarMatriculas(anioLectivoId, gradoId, estado, pageable)));
    }

    @GetMapping("/matriculas/{id}")
    @Operation(summary = "Obtener matrícula por ID")
    public ResponseEntity<ApiResponse<MatriculaResponseDto>> obtenerMatricula(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerMatricula(id)));
    }

    @PostMapping("/matriculas")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR','ROLE_SECRETARIA')")
    @Operation(summary = "Matricular un estudiante")
    public ResponseEntity<ApiResponse<MatriculaResponseDto>> matricular(
            @Valid @RequestBody MatriculaRequestDto dto) {
        MatriculaResponseDto resp = service.matricularEstudiante(dto);
        return ResponseEntity.created(URI.create("/api/v1/estudiantes/matriculas/" + resp.id()))
                .body(ApiResponse.ok(resp, "Estudiante matriculado exitosamente"));
    }

    @PutMapping("/matriculas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR','ROLE_SECRETARIA')")
    @Operation(summary = "Actualizar datos de una matrícula")
    public ResponseEntity<ApiResponse<MatriculaResponseDto>> actualizar(
            @PathVariable Long id, @Valid @RequestBody MatriculaRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarMatricula(id, dto), "Matrícula actualizada"));
    }

    @PatchMapping("/matriculas/{id}/estado")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_RECTOR')")
    @Operation(summary = "Cambiar estado de una matrícula (ACTIVA, RETIRADA, TRASLADADA, PROMOCIONADA)")
    public ResponseEntity<ApiResponse<Void>> cambiarEstado(
            @PathVariable Long id, @RequestParam String estado) {
        service.cambiarEstadoMatricula(id, estado);
        return ResponseEntity.ok(ApiResponse.ok(null, "Estado actualizado a: " + estado));
    }
}
