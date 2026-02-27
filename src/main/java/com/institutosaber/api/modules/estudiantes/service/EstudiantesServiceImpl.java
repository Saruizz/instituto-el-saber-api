package com.institutosaber.api.modules.estudiantes.service;

import com.institutosaber.api.exception.BusinessException;
import com.institutosaber.api.exception.EntityNotFoundException;
import com.institutosaber.api.modules.academico.entity.AnioLectivo;
import com.institutosaber.api.modules.academico.entity.Grado;
import com.institutosaber.api.modules.academico.repository.AnioLectivoRepository;
import com.institutosaber.api.modules.academico.repository.GradoRepository;
import com.institutosaber.api.modules.estudiantes.dto.*;
import com.institutosaber.api.modules.estudiantes.entity.*;
import com.institutosaber.api.modules.estudiantes.mapper.EstudianteMapper;
import com.institutosaber.api.modules.estudiantes.repository.*;
import com.institutosaber.api.shared.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EstudiantesServiceImpl implements EstudiantesService {

    private final EstudianteRepository estudianteRepo;
    private final AcudienteRepository acudienteRepo;
    private final PersonaRepository personaRepo;
    private final MatriculaRepository matriculaRepo;
    private final AnioLectivoRepository anioLectivoRepo;
    private final GradoRepository gradoRepo;
    private final EstudianteMapper mapper;

    // ─────────────────────────────────────────────────────────────
    // ESTUDIANTES
    // ─────────────────────────────────────────────────────────────
    @Override
    public PageResponse<EstudianteResumenDto> buscarEstudiantes(String termino, Pageable pageable) {
        var page = estudianteRepo.buscar(termino, pageable);
        return PageResponse.of(page.map(mapper::toEstudianteResumen));
    }

    @Override
    public EstudianteResponseDto obtenerEstudiante(Long id) {
        Estudiante e = estudianteRepo.findByIdWithPersona(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante", "id", id));
        int total = matriculaRepo.findByEstudianteIdOrderByAnioLectivoAnioDesc(id).size();
        var dto = mapper.toEstudianteDto(e);
        return new EstudianteResponseDto(dto.id(), dto.codigoEstudiante(), dto.persona(),
                dto.activo(), dto.grupoSanguineo(), dto.condicionEspecial(),
                dto.institucionProcedencia(), dto.observaciones(), total);
    }

    @Override
    @Transactional
    public EstudianteResponseDto crearEstudiante(EstudianteRequestDto dto) {
        validarDocumento(dto.persona().numeroDocumento(), null);

        Persona persona = mapper.toPersonaEntity(dto.persona());
        Estudiante estudiante = Estudiante.builder()
                .persona(persona)
                .codigoEstudiante(generarCodigo())
                .activo(true)
                .grupoSanguineo(dto.grupoSanguineo())
                .condicionEspecial(dto.condicionEspecial())
                .institucionProcedencia(dto.institucionProcedencia())
                .observaciones(dto.observaciones())
                .build();

        estudiante = estudianteRepo.save(estudiante);
        log.info("Estudiante creado: {} | código: {}", persona.getNombreCompleto(), estudiante.getCodigoEstudiante());

        var respDto = mapper.toEstudianteDto(estudiante);
        return new EstudianteResponseDto(respDto.id(), respDto.codigoEstudiante(), respDto.persona(),
                respDto.activo(), respDto.grupoSanguineo(), respDto.condicionEspecial(),
                respDto.institucionProcedencia(), respDto.observaciones(), 0);
    }

    @Override
    @Transactional
    public EstudianteResponseDto actualizarEstudiante(Long id, EstudianteRequestDto dto) {
        Estudiante est = estudianteRepo.findByIdWithPersona(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante", "id", id));
        validarDocumento(dto.persona().numeroDocumento(), est.getPersona().getId());

        mapper.updatePersonaFromDto(dto.persona(), est.getPersona());
        est.setGrupoSanguineo(dto.grupoSanguineo());
        est.setCondicionEspecial(dto.condicionEspecial());
        est.setInstitucionProcedencia(dto.institucionProcedencia());
        est.setObservaciones(dto.observaciones());

        est = estudianteRepo.save(est);
        int total = matriculaRepo.findByEstudianteIdOrderByAnioLectivoAnioDesc(id).size();
        var respDto = mapper.toEstudianteDto(est);
        return new EstudianteResponseDto(respDto.id(), respDto.codigoEstudiante(), respDto.persona(),
                respDto.activo(), respDto.grupoSanguineo(), respDto.condicionEspecial(),
                respDto.institucionProcedencia(), respDto.observaciones(), total);
    }

    @Override
    @Transactional
    public void desactivarEstudiante(Long id) {
        Estudiante est = estudianteRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante", "id", id));
        est.setActivo(false);
        estudianteRepo.save(est);
        log.info("Estudiante desactivado: id={}", id);
    }

    private void validarDocumento(String documento, Long personaIdIgnorar) {
        if (personaIdIgnorar == null) {
            if (personaRepo.existsByNumeroDocumento(documento))
                throw new BusinessException("Ya existe una persona con el documento: " + documento);
        } else {
            if (personaRepo.existsByNumeroDocumentoAndIdNot(documento, personaIdIgnorar))
                throw new BusinessException("Ya existe otra persona con el documento: " + documento);
        }
    }

    private String generarCodigo() {
        int anio = Year.now().getValue();
        long count = estudianteRepo.count() + 1;
        return String.format("%d-%04d", anio, count);
    }

    // ─────────────────────────────────────────────────────────────
    // ACUDIENTES
    // ─────────────────────────────────────────────────────────────
    @Override
    public AcudienteResponseDto obtenerAcudiente(Long id) {
        return mapper.toAcudienteDto(acudienteRepo.findByIdWithPersona(id)
                .orElseThrow(() -> new EntityNotFoundException("Acudiente", "id", id)));
    }

    @Override
    @Transactional
    public AcudienteResponseDto crearAcudiente(AcudienteRequestDto dto) {
        if (personaRepo.existsByNumeroDocumento(dto.persona().numeroDocumento()))
            throw new BusinessException("Ya existe una persona con el documento: " + dto.persona().numeroDocumento());

        Persona persona = mapper.toPersonaEntity(dto.persona());
        Acudiente acudiente = mapper.toAcudienteEntity(dto);
        acudiente.setPersona(persona);
        acudiente.setEsResponsableEconomico(dto.esResponsableEconomico() != null ? dto.esResponsableEconomico() : true);
        return mapper.toAcudienteDto(acudienteRepo.save(acudiente));
    }

    @Override
    @Transactional
    public AcudienteResponseDto actualizarAcudiente(Long id, AcudienteRequestDto dto) {
        Acudiente acudiente = acudienteRepo.findByIdWithPersona(id)
                .orElseThrow(() -> new EntityNotFoundException("Acudiente", "id", id));
        if (personaRepo.existsByNumeroDocumentoAndIdNot(dto.persona().numeroDocumento(),
                acudiente.getPersona().getId()))
            throw new BusinessException("Ya existe otra persona con el documento: " + dto.persona().numeroDocumento());

        mapper.updatePersonaFromDto(dto.persona(), acudiente.getPersona());
        mapper.updateAcudienteFromDto(dto, acudiente);
        return mapper.toAcudienteDto(acudienteRepo.save(acudiente));
    }

    @Override
    public List<AcudienteResponseDto> buscarAcudientes(String termino) {
        return acudienteRepo.buscar(termino == null ? "" : termino)
                .stream().map(mapper::toAcudienteDto).toList();
    }

    // ─────────────────────────────────────────────────────────────
    // MATRÍCULAS
    // ─────────────────────────────────────────────────────────────
    @Override
    public PageResponse<MatriculaResponseDto> listarMatriculas(
            Long anioLectivoId, Long gradoId, String estado, Pageable pageable) {
        var page = matriculaRepo.buscarPorAnio(anioLectivoId, gradoId, estado, pageable);
        return PageResponse.of(page.map(mapper::toMatriculaDto));
    }

    @Override
    public MatriculaResponseDto obtenerMatricula(Long id) {
        Matricula m = matriculaRepo.findByIdWithDetails(id)
                .orElseThrow(() -> new EntityNotFoundException("Matricula", "id", id));
        return mapper.toMatriculaDto(m);
    }

    @Override
    public List<MatriculaResponseDto> historialEstudiante(Long estudianteId) {
        estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante", "id", estudianteId));
        return mapper.toMatriculaDtoList(
                matriculaRepo.findByEstudianteIdOrderByAnioLectivoAnioDesc(estudianteId));
    }

    @Override
    @Transactional
    public MatriculaResponseDto matricularEstudiante(MatriculaRequestDto dto) {
        if (matriculaRepo.existsByEstudianteIdAndAnioLectivoId(dto.estudianteId(), dto.anioLectivoId()))
            throw new BusinessException("El estudiante ya está matriculado en ese año lectivo.");

        Estudiante est = estudianteRepo.findById(dto.estudianteId())
                .orElseThrow(() -> new EntityNotFoundException("Estudiante", "id", dto.estudianteId()));
        AnioLectivo anio = anioLectivoRepo.findById(dto.anioLectivoId())
                .orElseThrow(() -> new EntityNotFoundException("AnioLectivo", "id", dto.anioLectivoId()));
        Grado grado = gradoRepo.findById(dto.gradoId())
                .orElseThrow(() -> new EntityNotFoundException("Grado", "id", dto.gradoId()));

        Acudiente acudiente = null;
        if (dto.acudienteId() != null)
            acudiente = acudienteRepo.findById(dto.acudienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Acudiente", "id", dto.acudienteId()));

        Matricula matricula = Matricula.builder()
                .estudiante(est)
                .anioLectivo(anio)
                .grado(grado)
                .acudiente(acudiente)
                .numeroMatricula(generarNumeroMatricula(anio.getAnio()))
                .fechaMatricula(dto.fechaMatricula())
                .estado("ACTIVA")
                .valorMatricula(dto.valorMatricula())
                .descuentoPorcentaje(dto.descuentoPorcentaje() != null ? dto.descuentoPorcentaje() : 0)
                .observaciones(dto.observaciones())
                .createdAt(LocalDateTime.now())
                .build();

        matricula = matriculaRepo.save(matricula);
        log.info("Estudiante matriculado: {} | anio: {} | grado: {}",
                est.getPersona().getNombreCompleto(), anio.getAnio(), grado.getNombre());

        return mapper.toMatriculaDto(matricula);
    }

    @Override
    @Transactional
    public MatriculaResponseDto actualizarMatricula(Long id, MatriculaRequestDto dto) {
        Matricula m = matriculaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matricula", "id", id));

        Grado grado = gradoRepo.findById(dto.gradoId())
                .orElseThrow(() -> new EntityNotFoundException("Grado", "id", dto.gradoId()));

        Acudiente acudiente = null;
        if (dto.acudienteId() != null)
            acudiente = acudienteRepo.findById(dto.acudienteId())
                    .orElseThrow(() -> new EntityNotFoundException("Acudiente", "id", dto.acudienteId()));

        m.setGrado(grado);
        m.setAcudiente(acudiente);
        m.setFechaMatricula(dto.fechaMatricula());
        m.setValorMatricula(dto.valorMatricula());
        m.setDescuentoPorcentaje(dto.descuentoPorcentaje() != null ? dto.descuentoPorcentaje() : 0);
        m.setObservaciones(dto.observaciones());

        return mapper.toMatriculaDto(matriculaRepo.save(m));
    }

    @Override
    @Transactional
    public void cambiarEstadoMatricula(Long id, String nuevoEstado) {
        var estados = List.of("ACTIVA", "RETIRADA", "TRASLADADA", "PROMOCIONADA");
        if (!estados.contains(nuevoEstado))
            throw new BusinessException("Estado inválido. Valores permitidos: " + estados);

        Matricula m = matriculaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Matricula", "id", id));
        m.setEstado(nuevoEstado);
        matriculaRepo.save(m);
        log.info("Estado de matrícula {} cambiado a: {}", id, nuevoEstado);
    }

    private String generarNumeroMatricula(Integer anio) {
        String ultimo = matriculaRepo.findMaxNumeroMatriculaByAnio(anio);
        int secuencia = 1;
        if (ultimo != null) {
            try {
                secuencia = Integer.parseInt(ultimo.substring(ultimo.lastIndexOf('-') + 1)) + 1;
            } catch (NumberFormatException ignored) {
            }
        }
        return String.format("%d-M-%04d", anio, secuencia);
    }
}
