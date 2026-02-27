package com.institutosaber.api.modules.academico.service;

import com.institutosaber.api.exception.BusinessException;
import com.institutosaber.api.exception.EntityNotFoundException;
import com.institutosaber.api.modules.academico.dto.*;
import com.institutosaber.api.modules.academico.entity.*;
import com.institutosaber.api.modules.academico.mapper.AcademicoMapper;
import com.institutosaber.api.modules.academico.repository.*;
import com.institutosaber.api.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AcademicoServiceImpl implements AcademicoService {

    private final NivelEducativoRepository nivelRepo;
    private final GradoRepository gradoRepo;
    private final AreaRepository areaRepo;
    private final AsignaturaRepository asignaturaRepo;
    private final GradoAsignaturaRepository gradoAsignaturaRepo;
    private final AnioLectivoRepository anioLectivoRepo;
    private final PeriodoRepository periodoRepo;
    private final AcademicoMapper mapper;

    // ─────────────────────────────────────────
    // NIVELES EDUCATIVOS
    // ─────────────────────────────────────────
    @Override
    public List<NivelEducativoResponseDto> listarNiveles() {
        return mapper.toNivelDtoList(nivelRepo.findAllByOrderByOrdenAsc());
    }

    // ─────────────────────────────────────────
    // GRADOS
    // ─────────────────────────────────────────
    @Override
    public List<GradoResponseDto> listarGrados() {
        return mapper.toGradoDtoList(gradoRepo.findAllWithNivel());
    }

    @Override
    public List<GradoResponseDto> listarGradosPorNivel(Long nivelId) {
        nivelRepo.findById(nivelId)
                .orElseThrow(() -> new EntityNotFoundException("NivelEducativo", "id", nivelId));
        return mapper.toGradoDtoList(gradoRepo.findByNivelIdOrderByOrdenAsc(nivelId));
    }

    @Override
    @Transactional
    public GradoResponseDto crearGrado(GradoRequestDto dto) {
        NivelEducativo nivel = nivelRepo.findById(dto.nivelId())
                .orElseThrow(() -> new EntityNotFoundException("NivelEducativo", "id", dto.nivelId()));

        Grado grado = mapper.toGradoEntity(dto);
        grado.setNivel(nivel);
        return mapper.toGradoDto(gradoRepo.save(grado));
    }

    @Override
    @Transactional
    public GradoResponseDto actualizarGrado(Long id, GradoRequestDto dto) {
        Grado grado = gradoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grado", "id", id));
        NivelEducativo nivel = nivelRepo.findById(dto.nivelId())
                .orElseThrow(() -> new EntityNotFoundException("NivelEducativo", "id", dto.nivelId()));

        grado.setNivel(nivel);
        grado.setNombre(dto.nombre());
        grado.setDescripcion(dto.descripcion());
        grado.setOrden(dto.orden());
        return mapper.toGradoDto(gradoRepo.save(grado));
    }

    @Override
    @Transactional
    public void eliminarGrado(Long id) {
        if (!gradoRepo.existsById(id))
            throw new EntityNotFoundException("Grado", "id", id);
        gradoRepo.deleteById(id);
    }

    // ─────────────────────────────────────────
    // ÁREAS
    // ─────────────────────────────────────────
    @Override
    public List<AreaResponseDto> listarAreasActivas() {
        return mapper.toAreaDtoList(areaRepo.findByActivoTrueOrderByNombreAsc());
    }

    @Override
    public PageResponse<AreaResponseDto> buscarAreas(String nombre, Pageable pageable) {
        var page = areaRepo.findByNombreContainingIgnoreCase(nombre == null ? "" : nombre, pageable);
        return PageResponse.of(page.map(mapper::toAreaDto));
    }

    @Override
    public AreaResponseDto obtenerArea(Long id) {
        return mapper.toAreaDto(areaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Area", "id", id)));
    }

    @Override
    @Transactional
    public AreaResponseDto crearArea(AreaRequestDto dto) {
        if (areaRepo.existsByNombre(dto.nombre()))
            throw new BusinessException("Ya existe un área con el nombre: " + dto.nombre());

        Area area = mapper.toAreaEntity(dto);
        area.setActivo(true);
        return mapper.toAreaDto(areaRepo.save(area));
    }

    @Override
    @Transactional
    public AreaResponseDto actualizarArea(Long id, AreaRequestDto dto) {
        Area area = areaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Area", "id", id));

        if (areaRepo.existsByNombreAndIdNot(dto.nombre(), id))
            throw new BusinessException("Ya existe otra área con el nombre: " + dto.nombre());

        mapper.updateAreaFromDto(dto, area);
        return mapper.toAreaDto(areaRepo.save(area));
    }

    @Override
    @Transactional
    public void desactivarArea(Long id) {
        Area area = areaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Area", "id", id));
        area.setActivo(false);
        areaRepo.save(area);
        log.info("Área desactivada: id={}", id);
    }

    // ─────────────────────────────────────────
    // ASIGNATURAS
    // ─────────────────────────────────────────
    @Override
    public List<AsignaturaResponseDto> listarAsignaturasActivas() {
        return mapper.toAsignaturaDtoList(asignaturaRepo.findByActivoTrueOrderByAreaNombreAscNombreAsc());
    }

    @Override
    public List<AsignaturaResponseDto> listarAsignaturasPorArea(Long areaId) {
        areaRepo.findById(areaId)
                .orElseThrow(() -> new EntityNotFoundException("Area", "id", areaId));
        return mapper.toAsignaturaDtoList(
                asignaturaRepo.findByAreaIdAndActivoTrueOrderByNombreAsc(areaId));
    }

    @Override
    public PageResponse<AsignaturaResponseDto> buscarAsignaturas(String nombre, Pageable pageable) {
        var page = asignaturaRepo.buscar(nombre, pageable);
        return PageResponse.of(page.map(mapper::toAsignaturaDto));
    }

    @Override
    public AsignaturaResponseDto obtenerAsignatura(Long id) {
        return mapper.toAsignaturaDto(asignaturaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignatura", "id", id)));
    }

    @Override
    @Transactional
    public AsignaturaResponseDto crearAsignatura(AsignaturaRequestDto dto) {
        Area area = areaRepo.findById(dto.areaId())
                .orElseThrow(() -> new EntityNotFoundException("Area", "id", dto.areaId()));

        if (asignaturaRepo.existsByNombreAndAreaId(dto.nombre(), dto.areaId()))
            throw new BusinessException("Ya existe la asignatura '" + dto.nombre() + "' en esa área.");

        Asignatura asignatura = mapper.toAsignaturaEntity(dto);
        asignatura.setArea(area);
        asignatura.setActivo(true);
        return mapper.toAsignaturaDto(asignaturaRepo.save(asignatura));
    }

    @Override
    @Transactional
    public AsignaturaResponseDto actualizarAsignatura(Long id, AsignaturaRequestDto dto) {
        Asignatura asignatura = asignaturaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignatura", "id", id));
        Area area = areaRepo.findById(dto.areaId())
                .orElseThrow(() -> new EntityNotFoundException("Area", "id", dto.areaId()));

        if (asignaturaRepo.existsByNombreAndAreaIdAndIdNot(dto.nombre(), dto.areaId(), id))
            throw new BusinessException("Ya existe otra asignatura '" + dto.nombre() + "' en esa área.");

        mapper.updateAsignaturaFromDto(dto, asignatura);
        asignatura.setArea(area);
        return mapper.toAsignaturaDto(asignaturaRepo.save(asignatura));
    }

    @Override
    @Transactional
    public void desactivarAsignatura(Long id) {
        Asignatura asignatura = asignaturaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Asignatura", "id", id));
        asignatura.setActivo(false);
        asignaturaRepo.save(asignatura);
    }

    // ─────────────────────────────────────────
    // GRADO-ASIGNATURAS
    // ─────────────────────────────────────────
    @Override
    public List<GradoAsignaturaResponseDto> listarAsignaturasPorGrado(Long gradoId) {
        gradoRepo.findById(gradoId)
                .orElseThrow(() -> new EntityNotFoundException("Grado", "id", gradoId));
        return mapper.toGradoAsignaturaDtoList(
                gradoAsignaturaRepo.findByGradoIdWithDetails(gradoId));
    }

    @Override
    @Transactional
    public GradoAsignaturaResponseDto asignarAsignaturaAGrado(Long gradoId, AsignarAsignaturaRequestDto dto) {
        Grado grado = gradoRepo.findById(gradoId)
                .orElseThrow(() -> new EntityNotFoundException("Grado", "id", gradoId));
        Asignatura asignatura = asignaturaRepo.findById(dto.asignaturaId())
                .orElseThrow(() -> new EntityNotFoundException("Asignatura", "id", dto.asignaturaId()));

        if (gradoAsignaturaRepo.existsByGradoIdAndAsignaturaId(gradoId, dto.asignaturaId()))
            throw new BusinessException("La asignatura ya está asignada a ese grado.");

        GradoAsignatura ga = GradoAsignatura.builder()
                .grado(grado)
                .asignatura(asignatura)
                .intensidadHorariaSemanal(dto.intensidadHorariaSemanal())
                .build();

        return mapper.toGradoAsignaturaDto(gradoAsignaturaRepo.save(ga));
    }

    @Override
    @Transactional
    public void quitarAsignaturaDeGrado(Long gradoId, Long asignaturaId) {
        if (!gradoAsignaturaRepo.existsByGradoIdAndAsignaturaId(gradoId, asignaturaId))
            throw new EntityNotFoundException("GradoAsignatura", "gradoId+asignaturaId",
                    gradoId + "+" + asignaturaId);
        gradoAsignaturaRepo.deleteByGradoIdAndAsignaturaId(gradoId, asignaturaId);
    }

    @Override
    @Transactional
    public GradoAsignaturaResponseDto actualizarIntensidadHoraria(Long id, Integer intensidad) {
        GradoAsignatura ga = gradoAsignaturaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("GradoAsignatura", "id", id));
        ga.setIntensidadHorariaSemanal(intensidad);
        return mapper.toGradoAsignaturaDto(gradoAsignaturaRepo.save(ga));
    }

    // ─────────────────────────────────────────
    // AÑOS LECTIVOS
    // ─────────────────────────────────────────
    @Override
    public List<AnioLectivoResponseDto> listarAniosLectivos() {
        return anioLectivoRepo.findAllByOrderByAnioDesc().stream()
                .map(al -> {
                    var dto = mapper.toAnioLectivoDto(al);
                    return new AnioLectivoResponseDto(
                            dto.id(), dto.anio(), dto.fechaInicio(), dto.fechaFin(),
                            dto.activo(), dto.descripcion(),
                            periodoRepo.findByAnioLectivoIdOrderByNumeroPeriodoAsc(al.getId()).size());
                }).toList();
    }

    @Override
    public AnioLectivoResponseDto obtenerAnioLectivo(Long id) {
        AnioLectivo al = anioLectivoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", id));
        var dto = mapper.toAnioLectivoDto(al);
        int totalPeriodos = periodoRepo.findByAnioLectivoIdOrderByNumeroPeriodoAsc(id).size();
        return new AnioLectivoResponseDto(dto.id(), dto.anio(), dto.fechaInicio(), dto.fechaFin(),
                dto.activo(), dto.descripcion(), totalPeriodos);
    }

    @Override
    public AnioLectivoResponseDto obtenerAnioLectivoActivo() {
        AnioLectivo al = anioLectivoRepo.findByActivo(true)
                .orElseThrow(() -> new BusinessException("No hay un año lectivo activo actualmente."));
        var dto = mapper.toAnioLectivoDto(al);
        int totalPeriodos = periodoRepo.findByAnioLectivoIdOrderByNumeroPeriodoAsc(al.getId()).size();
        return new AnioLectivoResponseDto(dto.id(), dto.anio(), dto.fechaInicio(), dto.fechaFin(),
                dto.activo(), dto.descripcion(), totalPeriodos);
    }

    @Override
    @Transactional
    public AnioLectivoResponseDto crearAnioLectivo(AnioLectivoRequestDto dto) {
        if (anioLectivoRepo.existsByAnio(dto.anio()))
            throw new BusinessException("Ya existe un año lectivo para el año: " + dto.anio());
        if (dto.fechaFin().isBefore(dto.fechaInicio()))
            throw new BusinessException("La fecha de fin no puede ser anterior a la fecha de inicio.");

        AnioLectivo al = mapper.toAnioLectivoEntity(dto);
        al.setActivo(false);
        al.setCreatedAt(LocalDateTime.now());
        al = anioLectivoRepo.save(al);
        log.info("Año lectivo creado: {}", al.getAnio());

        return new AnioLectivoResponseDto(
                al.getId(), al.getAnio(), al.getFechaInicio(), al.getFechaFin(),
                al.getActivo(), al.getDescripcion(), 0);
    }

    @Override
    @Transactional
    public AnioLectivoResponseDto actualizarAnioLectivo(Long id, AnioLectivoRequestDto dto) {
        AnioLectivo al = anioLectivoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", id));

        if (anioLectivoRepo.existsByAnioAndIdNot(dto.anio(), id))
            throw new BusinessException("Ya existe otro año lectivo para el año: " + dto.anio());
        if (dto.fechaFin().isBefore(dto.fechaInicio()))
            throw new BusinessException("La fecha de fin no puede ser anterior a la fecha de inicio.");

        mapper.updateAnioLectivoFromDto(dto, al);
        al = anioLectivoRepo.save(al);
        int totalPeriodos = periodoRepo.findByAnioLectivoIdOrderByNumeroPeriodoAsc(id).size();
        return new AnioLectivoResponseDto(al.getId(), al.getAnio(), al.getFechaInicio(), al.getFechaFin(),
                al.getActivo(), al.getDescripcion(), totalPeriodos);
    }

    @Override
    @Transactional
    public void activarAnioLectivo(Long id) {
        // Desactivar el año lectivo activo actual
        anioLectivoRepo.findByActivo(true).ifPresent(actual -> {
            actual.setActivo(false);
            anioLectivoRepo.save(actual);
        });

        AnioLectivo al = anioLectivoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", id));
        al.setActivo(true);
        anioLectivoRepo.save(al);
        log.info("Año lectivo activado: {}", al.getAnio());
    }

    // ─────────────────────────────────────────
    // PERIODOS
    // ─────────────────────────────────────────
    @Override
    public List<PeriodoResponseDto> listarPeriodos(Long anioLectivoId) {
        return mapper.toPeriodoDtoList(
                periodoRepo.findByAnioLectivoIdWithDetails(anioLectivoId));
    }

    @Override
    public PeriodoResponseDto obtenerPeriodo(Long id) {
        return mapper.toPeriodoDto(periodoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Periodo", "id", id)));
    }

    @Override
    @Transactional
    public PeriodoResponseDto crearPeriodo(PeriodoRequestDto dto) {
        AnioLectivo anioLectivo = anioLectivoRepo.findById(dto.anioLectivoId())
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", dto.anioLectivoId()));

        if (periodoRepo.existsByAnioLectivoIdAndNumeroPeriodo(dto.anioLectivoId(), dto.numeroPeriodo()))
            throw new BusinessException("El periodo " + dto.numeroPeriodo() + " ya existe para ese año lectivo.");

        if (dto.fechaFin().isBefore(dto.fechaInicio()))
            throw new BusinessException("La fecha de fin no puede ser anterior a la fecha de inicio.");

        Periodo periodo = mapper.toPeriodoEntity(dto);
        periodo.setAnioLectivo(anioLectivo);
        periodo.setActivo(false);
        return mapper.toPeriodoDto(periodoRepo.save(periodo));
    }

    @Override
    @Transactional
    public PeriodoResponseDto actualizarPeriodo(Long id, PeriodoRequestDto dto) {
        Periodo periodo = periodoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Periodo", "id", id));

        if (periodoRepo.existsByAnioLectivoIdAndNumeroPeriodoAndIdNot(
                dto.anioLectivoId(), dto.numeroPeriodo(), id))
            throw new BusinessException("Ya existe otro periodo " + dto.numeroPeriodo() + " en ese año lectivo.");

        AnioLectivo anioLectivo = anioLectivoRepo.findById(dto.anioLectivoId())
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", dto.anioLectivoId()));

        mapper.updatePeriodoFromDto(dto, periodo);
        periodo.setAnioLectivo(anioLectivo);
        return mapper.toPeriodoDto(periodoRepo.save(periodo));
    }

    @Override
    @Transactional
    public void activarPeriodo(Long id) {
        Periodo periodo = periodoRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Periodo", "id", id));

        // Desactivar periodo activo del mismo año
        periodoRepo.findByAnioLectivoIdAndActivo(periodo.getAnioLectivo().getId(), true)
                .ifPresent(p -> {
                    p.setActivo(false);
                    periodoRepo.save(p);
                });

        periodo.setActivo(true);
        periodoRepo.save(periodo);
        log.info("Periodo activado: {} año {}", periodo.getNumeroPeriodo(), periodo.getAnioLectivo().getAnio());
    }

    @Override
    @Transactional
    public void crearPeriodosDefecto(Long anioLectivoId) {
        AnioLectivo al = anioLectivoRepo.findById(anioLectivoId)
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", anioLectivoId));

        if (periodoRepo.findByAnioLectivoIdOrderByNumeroPeriodoAsc(anioLectivoId).size() == 4)
            throw new BusinessException("El año lectivo ya tiene los 4 periodos creados.");

        // Dividir el año en 4 trimestres aproximados
        LocalDate ini = al.getFechaInicio();
        LocalDate fin = al.getFechaFin();
        long dias = ini.until(fin, java.time.temporal.ChronoUnit.DAYS);
        long diasPer = dias / 4;

        for (int i = 1; i <= 4; i++) {
            if (!periodoRepo.existsByAnioLectivoIdAndNumeroPeriodo(anioLectivoId, i)) {
                LocalDate iniPer = ini.plusDays((long) (i - 1) * diasPer);
                LocalDate finPer = (i == 4) ? fin : ini.plusDays((long) i * diasPer - 1);
                String nombre = "Período " + i + " - " + al.getAnio();

                periodoRepo.save(Periodo.builder()
                        .anioLectivo(al).numeroPeriodo(i).nombre(nombre)
                        .fechaInicio(iniPer).fechaFin(finPer).activo(false)
                        .build());
            }
        }
        log.info("Periodos por defecto creados para el año lectivo {}", al.getAnio());
    }
}
