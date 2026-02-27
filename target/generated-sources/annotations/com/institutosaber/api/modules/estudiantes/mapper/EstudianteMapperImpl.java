package com.institutosaber.api.modules.estudiantes.mapper;

import com.institutosaber.api.modules.estudiantes.dto.AcudienteRequestDto;
import com.institutosaber.api.modules.estudiantes.dto.AcudienteResponseDto;
import com.institutosaber.api.modules.estudiantes.dto.EstudianteRequestDto;
import com.institutosaber.api.modules.estudiantes.dto.EstudianteResponseDto;
import com.institutosaber.api.modules.estudiantes.dto.EstudianteResumenDto;
import com.institutosaber.api.modules.estudiantes.dto.PersonaRequestDto;
import com.institutosaber.api.modules.estudiantes.dto.PersonaResponseDto;
import com.institutosaber.api.modules.estudiantes.entity.Acudiente;
import com.institutosaber.api.modules.estudiantes.entity.Estudiante;
import com.institutosaber.api.modules.estudiantes.entity.Persona;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-02-27T16:11:21-0500",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.18 (Eclipse Adoptium)"
)
@Component
public class EstudianteMapperImpl implements EstudianteMapper {

    @Override
    public Persona toPersonaEntity(PersonaRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Persona.PersonaBuilder persona = Persona.builder();

        persona.tipoDocumento( dto.tipoDocumento() );
        persona.numeroDocumento( dto.numeroDocumento() );
        persona.primerNombre( dto.primerNombre() );
        persona.segundoNombre( dto.segundoNombre() );
        persona.primerApellido( dto.primerApellido() );
        persona.segundoApellido( dto.segundoApellido() );
        persona.fechaNacimiento( dto.fechaNacimiento() );
        persona.genero( dto.genero() );
        persona.municipioNacimiento( dto.municipioNacimiento() );
        persona.departamentoNacimiento( dto.departamentoNacimiento() );
        persona.direccion( dto.direccion() );
        persona.municipioResidencia( dto.municipioResidencia() );
        persona.telefono( dto.telefono() );
        persona.email( dto.email() );

        return persona.build();
    }

    @Override
    public PersonaResponseDto toPersonaDto(Persona p) {
        if ( p == null ) {
            return null;
        }

        Long id = null;
        String tipoDocumento = null;
        String numeroDocumento = null;
        String primerNombre = null;
        String segundoNombre = null;
        String primerApellido = null;
        String segundoApellido = null;
        LocalDate fechaNacimiento = null;
        String genero = null;
        String municipioNacimiento = null;
        String departamentoNacimiento = null;
        String direccion = null;
        String municipioResidencia = null;
        String telefono = null;
        String email = null;

        id = p.getId();
        tipoDocumento = p.getTipoDocumento();
        numeroDocumento = p.getNumeroDocumento();
        primerNombre = p.getPrimerNombre();
        segundoNombre = p.getSegundoNombre();
        primerApellido = p.getPrimerApellido();
        segundoApellido = p.getSegundoApellido();
        fechaNacimiento = p.getFechaNacimiento();
        genero = p.getGenero();
        municipioNacimiento = p.getMunicipioNacimiento();
        departamentoNacimiento = p.getDepartamentoNacimiento();
        direccion = p.getDireccion();
        municipioResidencia = p.getMunicipioResidencia();
        telefono = p.getTelefono();
        email = p.getEmail();

        String nombreCompleto = p.getNombreCompleto();

        PersonaResponseDto personaResponseDto = new PersonaResponseDto( id, tipoDocumento, numeroDocumento, primerNombre, segundoNombre, primerApellido, segundoApellido, nombreCompleto, fechaNacimiento, genero, municipioNacimiento, departamentoNacimiento, direccion, municipioResidencia, telefono, email );

        return personaResponseDto;
    }

    @Override
    public void updatePersonaFromDto(PersonaRequestDto dto, Persona persona) {
        if ( dto == null ) {
            return;
        }

        persona.setTipoDocumento( dto.tipoDocumento() );
        persona.setNumeroDocumento( dto.numeroDocumento() );
        persona.setPrimerNombre( dto.primerNombre() );
        persona.setSegundoNombre( dto.segundoNombre() );
        persona.setPrimerApellido( dto.primerApellido() );
        persona.setSegundoApellido( dto.segundoApellido() );
        persona.setFechaNacimiento( dto.fechaNacimiento() );
        persona.setGenero( dto.genero() );
        persona.setMunicipioNacimiento( dto.municipioNacimiento() );
        persona.setDepartamentoNacimiento( dto.departamentoNacimiento() );
        persona.setDireccion( dto.direccion() );
        persona.setMunicipioResidencia( dto.municipioResidencia() );
        persona.setTelefono( dto.telefono() );
        persona.setEmail( dto.email() );
    }

    @Override
    public Acudiente toAcudienteEntity(AcudienteRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Acudiente.AcudienteBuilder acudiente = Acudiente.builder();

        acudiente.parentesco( dto.parentesco() );
        acudiente.ocupacion( dto.ocupacion() );
        acudiente.lugarTrabajo( dto.lugarTrabajo() );
        acudiente.telefonoTrabajo( dto.telefonoTrabajo() );
        acudiente.esResponsableEconomico( dto.esResponsableEconomico() );

        return acudiente.build();
    }

    @Override
    public AcudienteResponseDto toAcudienteDto(Acudiente a) {
        if ( a == null ) {
            return null;
        }

        Long id = null;
        PersonaResponseDto persona = null;
        String parentesco = null;
        String ocupacion = null;
        String lugarTrabajo = null;
        String telefonoTrabajo = null;
        Boolean esResponsableEconomico = null;

        id = a.getId();
        persona = toPersonaDto( a.getPersona() );
        parentesco = a.getParentesco();
        ocupacion = a.getOcupacion();
        lugarTrabajo = a.getLugarTrabajo();
        telefonoTrabajo = a.getTelefonoTrabajo();
        esResponsableEconomico = a.getEsResponsableEconomico();

        AcudienteResponseDto acudienteResponseDto = new AcudienteResponseDto( id, persona, parentesco, ocupacion, lugarTrabajo, telefonoTrabajo, esResponsableEconomico );

        return acudienteResponseDto;
    }

    @Override
    public void updateAcudienteFromDto(AcudienteRequestDto dto, Acudiente acudiente) {
        if ( dto == null ) {
            return;
        }

        acudiente.setParentesco( dto.parentesco() );
        acudiente.setOcupacion( dto.ocupacion() );
        acudiente.setLugarTrabajo( dto.lugarTrabajo() );
        acudiente.setTelefonoTrabajo( dto.telefonoTrabajo() );
        acudiente.setEsResponsableEconomico( dto.esResponsableEconomico() );
    }

    @Override
    public Estudiante toEstudianteEntity(EstudianteRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Estudiante.EstudianteBuilder estudiante = Estudiante.builder();

        estudiante.grupoSanguineo( dto.grupoSanguineo() );
        estudiante.condicionEspecial( dto.condicionEspecial() );
        estudiante.institucionProcedencia( dto.institucionProcedencia() );
        estudiante.observaciones( dto.observaciones() );

        return estudiante.build();
    }

    @Override
    public EstudianteResponseDto toEstudianteDto(Estudiante e) {
        if ( e == null ) {
            return null;
        }

        Long id = null;
        String codigoEstudiante = null;
        PersonaResponseDto persona = null;
        Boolean activo = null;
        String grupoSanguineo = null;
        String condicionEspecial = null;
        String institucionProcedencia = null;
        String observaciones = null;

        id = e.getId();
        codigoEstudiante = e.getCodigoEstudiante();
        persona = toPersonaDto( e.getPersona() );
        activo = e.getActivo();
        grupoSanguineo = e.getGrupoSanguineo();
        condicionEspecial = e.getCondicionEspecial();
        institucionProcedencia = e.getInstitucionProcedencia();
        observaciones = e.getObservaciones();

        Integer totalMatriculas = null;

        EstudianteResponseDto estudianteResponseDto = new EstudianteResponseDto( id, codigoEstudiante, persona, activo, grupoSanguineo, condicionEspecial, institucionProcedencia, observaciones, totalMatriculas );

        return estudianteResponseDto;
    }

    @Override
    public EstudianteResumenDto toEstudianteResumen(Estudiante e) {
        if ( e == null ) {
            return null;
        }

        String codigoEstudiante = null;
        String numeroDocumento = null;
        String tipoDocumento = null;
        Long id = null;
        Boolean activo = null;

        codigoEstudiante = e.getCodigoEstudiante();
        numeroDocumento = ePersonaNumeroDocumento( e );
        tipoDocumento = ePersonaTipoDocumento( e );
        id = e.getId();
        activo = e.getActivo();

        String nombreCompleto = e.getPersona().getNombreCompleto();

        EstudianteResumenDto estudianteResumenDto = new EstudianteResumenDto( id, codigoEstudiante, nombreCompleto, numeroDocumento, tipoDocumento, activo );

        return estudianteResumenDto;
    }

    @Override
    public List<EstudianteResumenDto> toEstudianteResumenList(List<Estudiante> lista) {
        if ( lista == null ) {
            return null;
        }

        List<EstudianteResumenDto> list = new ArrayList<EstudianteResumenDto>( lista.size() );
        for ( Estudiante estudiante : lista ) {
            list.add( toEstudianteResumen( estudiante ) );
        }

        return list;
    }

    private String ePersonaNumeroDocumento(Estudiante estudiante) {
        if ( estudiante == null ) {
            return null;
        }
        Persona persona = estudiante.getPersona();
        if ( persona == null ) {
            return null;
        }
        String numeroDocumento = persona.getNumeroDocumento();
        if ( numeroDocumento == null ) {
            return null;
        }
        return numeroDocumento;
    }

    private String ePersonaTipoDocumento(Estudiante estudiante) {
        if ( estudiante == null ) {
            return null;
        }
        Persona persona = estudiante.getPersona();
        if ( persona == null ) {
            return null;
        }
        String tipoDocumento = persona.getTipoDocumento();
        if ( tipoDocumento == null ) {
            return null;
        }
        return tipoDocumento;
    }
}
