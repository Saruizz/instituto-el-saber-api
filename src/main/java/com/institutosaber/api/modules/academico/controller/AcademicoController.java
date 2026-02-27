package com.institutosaber.api.modules.academico.controller;

import com.institutosaber.api.modules.academico.dto.*;
import com.institutosaber.api.modules.academico.service.AcademicoService;
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
@RequestMapping("/academico")
@RequiredArgsConstructor
@Tag(name = "2. Configuración Académica", description = "Niveles, Grados, Áreas, Asignaturas, Años Lectivos y Periodos")
public class AcademicoController {

    private final AcademicoService service;

    // ─── NIVELES ───────────────────────────────────────────────────────────────
    @GetMapping("/niveles")
    @Operation(summary = "Listar todos los niveles educativos")
    public ResponseEntity<ApiResponse<List<NivelEducativoResponseDto>>> listarNiveles() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarNiveles()));
    }

    // ─── GRADOS ────────────────────────────────────────────────────────────────
    @GetMapping("/grados")
    @Operation(summary = "Listar todos los grados ordenados por nivel y orden")
    public ResponseEntity<ApiResponse<List<GradoResponseDto>>> listarGrados() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarGrados()));
    }

    @GetMapping("/grados/nivel/{nivelId}")
    @Operation(summary = "Listar grados de un nivel específico")
    public ResponseEntity<ApiResponse<List<GradoResponseDto>>> listarGradosPorNivel(
            @PathVariable Long nivelId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarGradosPorNivel(nivelId)));
    }

    @PostMapping("/grados")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Crear un nuevo grado")
    public ResponseEntity<ApiResponse<GradoResponseDto>> crearGrado(
            @Valid @RequestBody GradoRequestDto dto) {
        GradoResponseDto resp = service.crearGrado(dto);
        return ResponseEntity.created(URI.create("/api/v1/academico/grados/" + resp.id()))
                .body(ApiResponse.ok(resp, "Grado creado exitosamente"));
    }

    @PutMapping("/grados/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Actualizar un grado")
    public ResponseEntity<ApiResponse<GradoResponseDto>> actualizarGrado(
            @PathVariable Long id, @Valid @RequestBody GradoRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarGrado(id, dto), "Grado actualizado"));
    }

    @DeleteMapping("/grados/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Eliminar un grado")
    public ResponseEntity<ApiResponse<Void>> eliminarGrado(@PathVariable Long id) {
        service.eliminarGrado(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Grado eliminado"));
    }

    // ─── ÁREAS ─────────────────────────────────────────────────────────────────
    @GetMapping("/areas")
    @Operation(summary = "Listar áreas activas o buscar por nombre (paginado)")
    public ResponseEntity<ApiResponse<PageResponse<AreaResponseDto>>> listarAreas(
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        return ResponseEntity.ok(ApiResponse.ok(service.buscarAreas(nombre, pageable)));
    }

    @GetMapping("/areas/activas")
    @Operation(summary = "Listar todas las áreas activas (sin paginación, para selects)")
    public ResponseEntity<ApiResponse<List<AreaResponseDto>>> listarAreasActivas() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarAreasActivas()));
    }

    @GetMapping("/areas/{id}")
    @Operation(summary = "Obtener un área por ID")
    public ResponseEntity<ApiResponse<AreaResponseDto>> obtenerArea(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerArea(id)));
    }

    @PostMapping("/areas")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Crear un área académica")
    public ResponseEntity<ApiResponse<AreaResponseDto>> crearArea(
            @Valid @RequestBody AreaRequestDto dto) {
        AreaResponseDto resp = service.crearArea(dto);
        return ResponseEntity.created(URI.create("/api/v1/academico/areas/" + resp.id()))
                .body(ApiResponse.ok(resp, "Área creada exitosamente"));
    }

    @PutMapping("/areas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Actualizar un área")
    public ResponseEntity<ApiResponse<AreaResponseDto>> actualizarArea(
            @PathVariable Long id, @Valid @RequestBody AreaRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarArea(id, dto), "Área actualizada"));
    }

    @DeleteMapping("/areas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Desactivar un área")
    public ResponseEntity<ApiResponse<Void>> desactivarArea(@PathVariable Long id) {
        service.desactivarArea(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Área desactivada"));
    }

    // ─── ASIGNATURAS ───────────────────────────────────────────────────────────
    @GetMapping("/asignaturas")
    @Operation(summary = "Buscar asignaturas con paginación")
    public ResponseEntity<ApiResponse<PageResponse<AsignaturaResponseDto>>> listarAsignaturas(
            @RequestParam(required = false) String nombre,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(ApiResponse.ok(service.buscarAsignaturas(nombre, pageable)));
    }

    @GetMapping("/asignaturas/activas")
    @Operation(summary = "Listar asignaturas activas (para selects)")
    public ResponseEntity<ApiResponse<List<AsignaturaResponseDto>>> listarAsignaturasActivas() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarAsignaturasActivas()));
    }

    @GetMapping("/asignaturas/area/{areaId}")
    @Operation(summary = "Listar asignaturas por área")
    public ResponseEntity<ApiResponse<List<AsignaturaResponseDto>>> listarPorArea(
            @PathVariable Long areaId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarAsignaturasPorArea(areaId)));
    }

    @GetMapping("/asignaturas/{id}")
    @Operation(summary = "Obtener asignatura por ID")
    public ResponseEntity<ApiResponse<AsignaturaResponseDto>> obtenerAsignatura(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerAsignatura(id)));
    }

    @PostMapping("/asignaturas")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Crear asignatura")
    public ResponseEntity<ApiResponse<AsignaturaResponseDto>> crearAsignatura(
            @Valid @RequestBody AsignaturaRequestDto dto) {
        AsignaturaResponseDto resp = service.crearAsignatura(dto);
        return ResponseEntity.created(URI.create("/api/v1/academico/asignaturas/" + resp.id()))
                .body(ApiResponse.ok(resp, "Asignatura creada exitosamente"));
    }

    @PutMapping("/asignaturas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Actualizar asignatura")
    public ResponseEntity<ApiResponse<AsignaturaResponseDto>> actualizarAsignatura(
            @PathVariable Long id, @Valid @RequestBody AsignaturaRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarAsignatura(id, dto), "Asignatura actualizada"));
    }

    @DeleteMapping("/asignaturas/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Desactivar asignatura")
    public ResponseEntity<ApiResponse<Void>> desactivarAsignatura(@PathVariable Long id) {
        service.desactivarAsignatura(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Asignatura desactivada"));
    }

    // ─── GRADO-ASIGNATURAS ─────────────────────────────────────────────────────
    @GetMapping("/grados/{gradoId}/asignaturas")
    @Operation(summary = "Listar asignaturas asignadas a un grado")
    public ResponseEntity<ApiResponse<List<GradoAsignaturaResponseDto>>> listarAsignaturasPorGrado(
            @PathVariable Long gradoId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarAsignaturasPorGrado(gradoId)));
    }

    @PostMapping("/grados/{gradoId}/asignaturas")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Asignar una asignatura a un grado con intensidad horaria")
    public ResponseEntity<ApiResponse<GradoAsignaturaResponseDto>> asignarAsignatura(
            @PathVariable Long gradoId,
            @Valid @RequestBody AsignarAsignaturaRequestDto dto) {
        GradoAsignaturaResponseDto resp = service.asignarAsignaturaAGrado(gradoId, dto);
        return ResponseEntity.created(URI.create("/api/v1/academico/grados/" + gradoId + "/asignaturas"))
                .body(ApiResponse.ok(resp, "Asignatura asignada al grado exitosamente"));
    }

    @DeleteMapping("/grados/{gradoId}/asignaturas/{asignaturaId}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Quitar una asignatura de un grado")
    public ResponseEntity<ApiResponse<Void>> quitarAsignatura(
            @PathVariable Long gradoId, @PathVariable Long asignaturaId) {
        service.quitarAsignaturaDeGrado(gradoId, asignaturaId);
        return ResponseEntity.ok(ApiResponse.ok(null, "Asignatura removida del grado"));
    }

    @PatchMapping("/grado-asignaturas/{id}/intensidad")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Actualizar intensidad horaria semanal")
    public ResponseEntity<ApiResponse<GradoAsignaturaResponseDto>> actualizarIntensidad(
            @PathVariable Long id,
            @RequestParam Integer intensidad) {
        return ResponseEntity.ok(ApiResponse.ok(
                service.actualizarIntensidadHoraria(id, intensidad), "Intensidad actualizada"));
    }

    // ─── AÑOS LECTIVOS ─────────────────────────────────────────────────────────
    @GetMapping("/anios-lectivos")
    @Operation(summary = "Listar todos los años lectivos")
    public ResponseEntity<ApiResponse<List<AnioLectivoResponseDto>>> listarAniosLectivos() {
        return ResponseEntity.ok(ApiResponse.ok(service.listarAniosLectivos()));
    }

    @GetMapping("/anios-lectivos/activo")
    @Operation(summary = "Obtener el año lectivo activo")
    public ResponseEntity<ApiResponse<AnioLectivoResponseDto>> obtenerAnioActivo() {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerAnioLectivoActivo()));
    }

    @GetMapping("/anios-lectivos/{id}")
    @Operation(summary = "Obtener año lectivo por ID")
    public ResponseEntity<ApiResponse<AnioLectivoResponseDto>> obtenerAnioLectivo(
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerAnioLectivo(id)));
    }

    @PostMapping("/anios-lectivos")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Crear año lectivo")
    public ResponseEntity<ApiResponse<AnioLectivoResponseDto>> crearAnioLectivo(
            @Valid @RequestBody AnioLectivoRequestDto dto) {
        AnioLectivoResponseDto resp = service.crearAnioLectivo(dto);
        return ResponseEntity.created(URI.create("/api/v1/academico/anios-lectivos/" + resp.id()))
                .body(ApiResponse.ok(resp, "Año lectivo creado exitosamente"));
    }

    @PutMapping("/anios-lectivos/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Actualizar año lectivo")
    public ResponseEntity<ApiResponse<AnioLectivoResponseDto>> actualizarAnioLectivo(
            @PathVariable Long id, @Valid @RequestBody AnioLectivoRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarAnioLectivo(id, dto), "Año lectivo actualizado"));
    }

    @PatchMapping("/anios-lectivos/{id}/activar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Activar un año lectivo (desactiva el anterior)")
    public ResponseEntity<ApiResponse<Void>> activarAnioLectivo(@PathVariable Long id) {
        service.activarAnioLectivo(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Año lectivo activado"));
    }

    // ─── PERIODOS ──────────────────────────────────────────────────────────────
    @GetMapping("/anios-lectivos/{anioLectivoId}/periodos")
    @Operation(summary = "Listar periodos de un año lectivo")
    public ResponseEntity<ApiResponse<List<PeriodoResponseDto>>> listarPeriodos(
            @PathVariable Long anioLectivoId) {
        return ResponseEntity.ok(ApiResponse.ok(service.listarPeriodos(anioLectivoId)));
    }

    @GetMapping("/periodos/{id}")
    @Operation(summary = "Obtener periodo por ID")
    public ResponseEntity<ApiResponse<PeriodoResponseDto>> obtenerPeriodo(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.ok(service.obtenerPeriodo(id)));
    }

    @PostMapping("/anios-lectivos/{anioLectivoId}/periodos")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Crear periodo en un año lectivo")
    public ResponseEntity<ApiResponse<PeriodoResponseDto>> crearPeriodo(
            @PathVariable Long anioLectivoId,
            @Valid @RequestBody PeriodoRequestDto dto) {
        // Enriquecer el DTO con el anioLectivoId de la URL
        var enriched = new PeriodoRequestDto(anioLectivoId, dto.numeroPeriodo(),
                dto.nombre(), dto.fechaInicio(), dto.fechaFin());
        PeriodoResponseDto resp = service.crearPeriodo(enriched);
        return ResponseEntity.created(URI.create("/api/v1/academico/periodos/" + resp.id()))
                .body(ApiResponse.ok(resp, "Período creado exitosamente"));
    }

    @PutMapping("/periodos/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Actualizar periodo")
    public ResponseEntity<ApiResponse<PeriodoResponseDto>> actualizarPeriodo(
            @PathVariable Long id, @Valid @RequestBody PeriodoRequestDto dto) {
        return ResponseEntity.ok(ApiResponse.ok(service.actualizarPeriodo(id, dto), "Período actualizado"));
    }

    @PatchMapping("/periodos/{id}/activar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Activar un período (desactiva el anterior del mismo año)")
    public ResponseEntity<ApiResponse<Void>> activarPeriodo(@PathVariable Long id) {
        service.activarPeriodo(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Período activado"));
    }

    @PostMapping("/anios-lectivos/{anioLectivoId}/periodos/crear-defecto")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_RECTOR')")
    @Operation(summary = "Crear los 4 períodos por defecto dividiendo el año uniformemente")
    public ResponseEntity<ApiResponse<Void>> crearPeriodosDefecto(
            @PathVariable Long anioLectivoId) {
        service.crearPeriodosDefecto(anioLectivoId);
        return ResponseEntity.ok(ApiResponse.ok(null, "Períodos por defecto creados exitosamente"));
    }
}
