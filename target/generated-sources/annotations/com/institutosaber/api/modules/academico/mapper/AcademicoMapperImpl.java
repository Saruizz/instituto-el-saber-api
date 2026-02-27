package com.institutosaber.api.modules.academico.mapper;

import com.institutosaber.api.modules.academico.dto.AnioLectivoRequestDto;
import com.institutosaber.api.modules.academico.dto.AnioLectivoResponseDto;
import com.institutosaber.api.modules.academico.dto.AreaRequestDto;
import com.institutosaber.api.modules.academico.dto.AreaResponseDto;
import com.institutosaber.api.modules.academico.dto.AsignaturaRequestDto;
import com.institutosaber.api.modules.academico.dto.AsignaturaResponseDto;
import com.institutosaber.api.modules.academico.dto.GradoAsignaturaResponseDto;
import com.institutosaber.api.modules.academico.dto.GradoRequestDto;
import com.institutosaber.api.modules.academico.dto.GradoResponseDto;
import com.institutosaber.api.modules.academico.dto.NivelEducativoResponseDto;
import com.institutosaber.api.modules.academico.dto.PeriodoRequestDto;
import com.institutosaber.api.modules.academico.dto.PeriodoResponseDto;
import com.institutosaber.api.modules.academico.entity.AnioLectivo;
import com.institutosaber.api.modules.academico.entity.Area;
import com.institutosaber.api.modules.academico.entity.Asignatura;
import com.institutosaber.api.modules.academico.entity.Grado;
import com.institutosaber.api.modules.academico.entity.GradoAsignatura;
import com.institutosaber.api.modules.academico.entity.NivelEducativo;
import com.institutosaber.api.modules.academico.entity.Periodo;
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
public class AcademicoMapperImpl implements AcademicoMapper {

    @Override
    public NivelEducativoResponseDto toNivelDto(NivelEducativo entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long id = null;
        String nombre = null;
        Integer orden = null;

        id = entidad.getId();
        nombre = entidad.getNombre();
        orden = entidad.getOrden();

        NivelEducativoResponseDto nivelEducativoResponseDto = new NivelEducativoResponseDto( id, nombre, orden );

        return nivelEducativoResponseDto;
    }

    @Override
    public List<NivelEducativoResponseDto> toNivelDtoList(List<NivelEducativo> lista) {
        if ( lista == null ) {
            return null;
        }

        List<NivelEducativoResponseDto> list = new ArrayList<NivelEducativoResponseDto>( lista.size() );
        for ( NivelEducativo nivelEducativo : lista ) {
            list.add( toNivelDto( nivelEducativo ) );
        }

        return list;
    }

    @Override
    public GradoResponseDto toGradoDto(Grado entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long nivelId = null;
        String nivelNombre = null;
        Long id = null;
        String nombre = null;
        String descripcion = null;
        Integer orden = null;

        nivelId = entidadNivelId( entidad );
        nivelNombre = entidadNivelNombre( entidad );
        id = entidad.getId();
        nombre = entidad.getNombre();
        descripcion = entidad.getDescripcion();
        orden = entidad.getOrden();

        GradoResponseDto gradoResponseDto = new GradoResponseDto( id, nivelId, nivelNombre, nombre, descripcion, orden );

        return gradoResponseDto;
    }

    @Override
    public List<GradoResponseDto> toGradoDtoList(List<Grado> lista) {
        if ( lista == null ) {
            return null;
        }

        List<GradoResponseDto> list = new ArrayList<GradoResponseDto>( lista.size() );
        for ( Grado grado : lista ) {
            list.add( toGradoDto( grado ) );
        }

        return list;
    }

    @Override
    public Grado toGradoEntity(GradoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Grado.GradoBuilder grado = Grado.builder();

        grado.nombre( dto.nombre() );
        grado.descripcion( dto.descripcion() );
        grado.orden( dto.orden() );

        return grado.build();
    }

    @Override
    public AreaResponseDto toAreaDto(Area entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long id = null;
        String nombre = null;
        String descripcion = null;
        Boolean activo = null;

        id = entidad.getId();
        nombre = entidad.getNombre();
        descripcion = entidad.getDescripcion();
        activo = entidad.getActivo();

        AreaResponseDto areaResponseDto = new AreaResponseDto( id, nombre, descripcion, activo );

        return areaResponseDto;
    }

    @Override
    public List<AreaResponseDto> toAreaDtoList(List<Area> lista) {
        if ( lista == null ) {
            return null;
        }

        List<AreaResponseDto> list = new ArrayList<AreaResponseDto>( lista.size() );
        for ( Area area : lista ) {
            list.add( toAreaDto( area ) );
        }

        return list;
    }

    @Override
    public Area toAreaEntity(AreaRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Area.AreaBuilder area = Area.builder();

        area.nombre( dto.nombre() );
        area.descripcion( dto.descripcion() );

        return area.build();
    }

    @Override
    public void updateAreaFromDto(AreaRequestDto dto, Area entidad) {
        if ( dto == null ) {
            return;
        }

        entidad.setNombre( dto.nombre() );
        entidad.setDescripcion( dto.descripcion() );
    }

    @Override
    public AsignaturaResponseDto toAsignaturaDto(Asignatura entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long areaId = null;
        String areaNombre = null;
        Long id = null;
        String nombre = null;
        String descripcion = null;
        Boolean activo = null;

        areaId = entidadAreaId( entidad );
        areaNombre = entidadAreaNombre( entidad );
        id = entidad.getId();
        nombre = entidad.getNombre();
        descripcion = entidad.getDescripcion();
        activo = entidad.getActivo();

        AsignaturaResponseDto asignaturaResponseDto = new AsignaturaResponseDto( id, areaId, areaNombre, nombre, descripcion, activo );

        return asignaturaResponseDto;
    }

    @Override
    public List<AsignaturaResponseDto> toAsignaturaDtoList(List<Asignatura> lista) {
        if ( lista == null ) {
            return null;
        }

        List<AsignaturaResponseDto> list = new ArrayList<AsignaturaResponseDto>( lista.size() );
        for ( Asignatura asignatura : lista ) {
            list.add( toAsignaturaDto( asignatura ) );
        }

        return list;
    }

    @Override
    public Asignatura toAsignaturaEntity(AsignaturaRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Asignatura.AsignaturaBuilder asignatura = Asignatura.builder();

        asignatura.nombre( dto.nombre() );
        asignatura.descripcion( dto.descripcion() );

        return asignatura.build();
    }

    @Override
    public void updateAsignaturaFromDto(AsignaturaRequestDto dto, Asignatura entidad) {
        if ( dto == null ) {
            return;
        }

        entidad.setNombre( dto.nombre() );
        entidad.setDescripcion( dto.descripcion() );
    }

    @Override
    public GradoAsignaturaResponseDto toGradoAsignaturaDto(GradoAsignatura entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long gradoId = null;
        String gradoNombre = null;
        Long asignaturaId = null;
        String asignaturaNombre = null;
        String areaNombre = null;
        Long id = null;
        Integer intensidadHorariaSemanal = null;

        gradoId = entidadGradoId( entidad );
        gradoNombre = entidadGradoNombre( entidad );
        asignaturaId = entidadAsignaturaId( entidad );
        asignaturaNombre = entidadAsignaturaNombre( entidad );
        areaNombre = entidadAsignaturaAreaNombre( entidad );
        id = entidad.getId();
        intensidadHorariaSemanal = entidad.getIntensidadHorariaSemanal();

        GradoAsignaturaResponseDto gradoAsignaturaResponseDto = new GradoAsignaturaResponseDto( id, gradoId, gradoNombre, asignaturaId, asignaturaNombre, areaNombre, intensidadHorariaSemanal );

        return gradoAsignaturaResponseDto;
    }

    @Override
    public List<GradoAsignaturaResponseDto> toGradoAsignaturaDtoList(List<GradoAsignatura> lista) {
        if ( lista == null ) {
            return null;
        }

        List<GradoAsignaturaResponseDto> list = new ArrayList<GradoAsignaturaResponseDto>( lista.size() );
        for ( GradoAsignatura gradoAsignatura : lista ) {
            list.add( toGradoAsignaturaDto( gradoAsignatura ) );
        }

        return list;
    }

    @Override
    public AnioLectivoResponseDto toAnioLectivoDto(AnioLectivo entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long id = null;
        Integer anio = null;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        Boolean activo = null;
        String descripcion = null;

        id = entidad.getId();
        anio = entidad.getAnio();
        fechaInicio = entidad.getFechaInicio();
        fechaFin = entidad.getFechaFin();
        activo = entidad.getActivo();
        descripcion = entidad.getDescripcion();

        Integer totalPeriodos = null;

        AnioLectivoResponseDto anioLectivoResponseDto = new AnioLectivoResponseDto( id, anio, fechaInicio, fechaFin, activo, descripcion, totalPeriodos );

        return anioLectivoResponseDto;
    }

    @Override
    public List<AnioLectivoResponseDto> toAnioLectivoDtoList(List<AnioLectivo> lista) {
        if ( lista == null ) {
            return null;
        }

        List<AnioLectivoResponseDto> list = new ArrayList<AnioLectivoResponseDto>( lista.size() );
        for ( AnioLectivo anioLectivo : lista ) {
            list.add( toAnioLectivoDto( anioLectivo ) );
        }

        return list;
    }

    @Override
    public AnioLectivo toAnioLectivoEntity(AnioLectivoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        AnioLectivo.AnioLectivoBuilder anioLectivo = AnioLectivo.builder();

        anioLectivo.anio( dto.anio() );
        anioLectivo.fechaInicio( dto.fechaInicio() );
        anioLectivo.fechaFin( dto.fechaFin() );
        anioLectivo.descripcion( dto.descripcion() );

        return anioLectivo.build();
    }

    @Override
    public void updateAnioLectivoFromDto(AnioLectivoRequestDto dto, AnioLectivo entidad) {
        if ( dto == null ) {
            return;
        }

        entidad.setAnio( dto.anio() );
        entidad.setFechaInicio( dto.fechaInicio() );
        entidad.setFechaFin( dto.fechaFin() );
        entidad.setDescripcion( dto.descripcion() );
    }

    @Override
    public PeriodoResponseDto toPeriodoDto(Periodo entidad) {
        if ( entidad == null ) {
            return null;
        }

        Long anioLectivoId = null;
        Integer anio = null;
        Long id = null;
        Integer numeroPeriodo = null;
        String nombre = null;
        LocalDate fechaInicio = null;
        LocalDate fechaFin = null;
        Boolean activo = null;

        anioLectivoId = entidadAnioLectivoId( entidad );
        anio = entidadAnioLectivoAnio( entidad );
        id = entidad.getId();
        numeroPeriodo = entidad.getNumeroPeriodo();
        nombre = entidad.getNombre();
        fechaInicio = entidad.getFechaInicio();
        fechaFin = entidad.getFechaFin();
        activo = entidad.getActivo();

        PeriodoResponseDto periodoResponseDto = new PeriodoResponseDto( id, anioLectivoId, anio, numeroPeriodo, nombre, fechaInicio, fechaFin, activo );

        return periodoResponseDto;
    }

    @Override
    public List<PeriodoResponseDto> toPeriodoDtoList(List<Periodo> lista) {
        if ( lista == null ) {
            return null;
        }

        List<PeriodoResponseDto> list = new ArrayList<PeriodoResponseDto>( lista.size() );
        for ( Periodo periodo : lista ) {
            list.add( toPeriodoDto( periodo ) );
        }

        return list;
    }

    @Override
    public Periodo toPeriodoEntity(PeriodoRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Periodo.PeriodoBuilder periodo = Periodo.builder();

        periodo.numeroPeriodo( dto.numeroPeriodo() );
        periodo.nombre( dto.nombre() );
        periodo.fechaInicio( dto.fechaInicio() );
        periodo.fechaFin( dto.fechaFin() );

        return periodo.build();
    }

    @Override
    public void updatePeriodoFromDto(PeriodoRequestDto dto, Periodo entidad) {
        if ( dto == null ) {
            return;
        }

        entidad.setNumeroPeriodo( dto.numeroPeriodo() );
        entidad.setNombre( dto.nombre() );
        entidad.setFechaInicio( dto.fechaInicio() );
        entidad.setFechaFin( dto.fechaFin() );
    }

    private Long entidadNivelId(Grado grado) {
        if ( grado == null ) {
            return null;
        }
        NivelEducativo nivel = grado.getNivel();
        if ( nivel == null ) {
            return null;
        }
        Long id = nivel.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entidadNivelNombre(Grado grado) {
        if ( grado == null ) {
            return null;
        }
        NivelEducativo nivel = grado.getNivel();
        if ( nivel == null ) {
            return null;
        }
        String nombre = nivel.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private Long entidadAreaId(Asignatura asignatura) {
        if ( asignatura == null ) {
            return null;
        }
        Area area = asignatura.getArea();
        if ( area == null ) {
            return null;
        }
        Long id = area.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entidadAreaNombre(Asignatura asignatura) {
        if ( asignatura == null ) {
            return null;
        }
        Area area = asignatura.getArea();
        if ( area == null ) {
            return null;
        }
        String nombre = area.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private Long entidadGradoId(GradoAsignatura gradoAsignatura) {
        if ( gradoAsignatura == null ) {
            return null;
        }
        Grado grado = gradoAsignatura.getGrado();
        if ( grado == null ) {
            return null;
        }
        Long id = grado.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entidadGradoNombre(GradoAsignatura gradoAsignatura) {
        if ( gradoAsignatura == null ) {
            return null;
        }
        Grado grado = gradoAsignatura.getGrado();
        if ( grado == null ) {
            return null;
        }
        String nombre = grado.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private Long entidadAsignaturaId(GradoAsignatura gradoAsignatura) {
        if ( gradoAsignatura == null ) {
            return null;
        }
        Asignatura asignatura = gradoAsignatura.getAsignatura();
        if ( asignatura == null ) {
            return null;
        }
        Long id = asignatura.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String entidadAsignaturaNombre(GradoAsignatura gradoAsignatura) {
        if ( gradoAsignatura == null ) {
            return null;
        }
        Asignatura asignatura = gradoAsignatura.getAsignatura();
        if ( asignatura == null ) {
            return null;
        }
        String nombre = asignatura.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private String entidadAsignaturaAreaNombre(GradoAsignatura gradoAsignatura) {
        if ( gradoAsignatura == null ) {
            return null;
        }
        Asignatura asignatura = gradoAsignatura.getAsignatura();
        if ( asignatura == null ) {
            return null;
        }
        Area area = asignatura.getArea();
        if ( area == null ) {
            return null;
        }
        String nombre = area.getNombre();
        if ( nombre == null ) {
            return null;
        }
        return nombre;
    }

    private Long entidadAnioLectivoId(Periodo periodo) {
        if ( periodo == null ) {
            return null;
        }
        AnioLectivo anioLectivo = periodo.getAnioLectivo();
        if ( anioLectivo == null ) {
            return null;
        }
        Long id = anioLectivo.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Integer entidadAnioLectivoAnio(Periodo periodo) {
        if ( periodo == null ) {
            return null;
        }
        AnioLectivo anioLectivo = periodo.getAnioLectivo();
        if ( anioLectivo == null ) {
            return null;
        }
        Integer anio = anioLectivo.getAnio();
        if ( anio == null ) {
            return null;
        }
        return anio;
    }
}
