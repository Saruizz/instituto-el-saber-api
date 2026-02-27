package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.GradoAsignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface GradoAsignaturaRepository extends JpaRepository<GradoAsignatura, Long> {
    List<GradoAsignatura> findByGradoId(Long gradoId);

    List<GradoAsignatura> findByAsignaturaId(Long asignaturaId);

    boolean existsByGradoIdAndAsignaturaId(Long gradoId, Long asignaturaId);

    void deleteByGradoIdAndAsignaturaId(Long gradoId, Long asignaturaId);

    @Query("""
            SELECT ga FROM GradoAsignatura ga
            JOIN FETCH ga.grado g JOIN FETCH g.nivel
            JOIN FETCH ga.asignatura a JOIN FETCH a.area
            WHERE g.id = :gradoId
            ORDER BY a.area.nombre ASC, a.nombre ASC
            """)
    List<GradoAsignatura> findByGradoIdWithDetails(@Param("gradoId") Long gradoId);
}
