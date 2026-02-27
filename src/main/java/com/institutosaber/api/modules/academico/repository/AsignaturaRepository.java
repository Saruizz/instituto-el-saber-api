package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.Asignatura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    List<Asignatura> findByAreaIdAndActivoTrueOrderByNombreAsc(Long areaId);

    List<Asignatura> findByActivoTrueOrderByAreaNombreAscNombreAsc();

    @Query("""
            SELECT a FROM Asignatura a JOIN FETCH a.area
            WHERE a.activo = true
            AND (:nombre IS NULL OR LOWER(a.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
            ORDER BY a.area.nombre ASC, a.nombre ASC
            """)
    Page<Asignatura> buscar(@Param("nombre") String nombre, Pageable pageable);

    boolean existsByNombreAndAreaId(String nombre, Long areaId);

    boolean existsByNombreAndAreaIdAndIdNot(String nombre, Long areaId, Long id);
}
