package com.institutosaber.api.modules.estudiantes.repository;

import com.institutosaber.api.modules.estudiantes.entity.Matricula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    boolean existsByEstudianteIdAndAnioLectivoId(Long estudianteId, Long anioLectivoId);

    Optional<Matricula> findByEstudianteIdAndAnioLectivoId(Long estudianteId, Long anioLectivoId);

    List<Matricula> findByEstudianteIdOrderByAnioLectivoAnioDesc(Long estudianteId);

    boolean existsByNumeroMatricula(String numero);

    @Query("""
            SELECT m FROM Matricula m
            JOIN FETCH m.estudiante e JOIN FETCH e.persona
            JOIN FETCH m.grado g JOIN FETCH g.nivel
            JOIN FETCH m.anioLectivo
            WHERE m.anioLectivo.id = :anioId
            AND (:gradoId IS NULL OR m.grado.id = :gradoId)
            AND (:estado IS NULL OR m.estado = :estado)
            ORDER BY e.persona.primerApellido ASC, e.persona.primerNombre ASC
            """)
    Page<Matricula> buscarPorAnio(
            @Param("anioId") Long anioId,
            @Param("gradoId") Long gradoId,
            @Param("estado") String estado,
            Pageable pageable);

    @Query("""
            SELECT m FROM Matricula m
            JOIN FETCH m.estudiante e JOIN FETCH e.persona p
            JOIN FETCH m.grado g JOIN FETCH g.nivel
            JOIN FETCH m.anioLectivo
            WHERE m.id = :id
            """)
    Optional<Matricula> findByIdWithDetails(@Param("id") Long id);

    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.anioLectivo.id = :anioId AND m.grado.id = :gradoId AND m.estado = 'ACTIVA'")
    long countActivasPorGrado(@Param("anioId") Long anioId, @Param("gradoId") Long gradoId);

    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.anioLectivo.id = :anioId AND m.estado = 'ACTIVA'")
    long countActivasPorAnio(@Param("anioId") Long anioId);

    @Query("SELECT MAX(m.numeroMatricula) FROM Matricula m WHERE m.anioLectivo.anio = :anio")
    String findMaxNumeroMatriculaByAnio(@Param("anio") Integer anio);
}
