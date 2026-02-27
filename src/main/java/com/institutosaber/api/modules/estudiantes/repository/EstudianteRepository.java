package com.institutosaber.api.modules.estudiantes.repository;

import com.institutosaber.api.modules.estudiantes.entity.Estudiante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByPersonaNumeroDocumento(String numeroDocumento);

    boolean existsByPersonaNumeroDocumento(String numeroDocumento);

    boolean existsByCodigoEstudiante(String codigo);

    @Query("""
            SELECT e FROM Estudiante e JOIN FETCH e.persona p
            WHERE e.activo = true
            AND (:termino IS NULL OR :termino = ''
                 OR LOWER(p.primerNombre)   LIKE LOWER(CONCAT('%', :termino, '%'))
                 OR LOWER(p.primerApellido) LIKE LOWER(CONCAT('%', :termino, '%'))
                 OR LOWER(p.segundoApellido) LIKE LOWER(CONCAT('%', :termino, '%'))
                 OR p.numeroDocumento       LIKE CONCAT('%', :termino, '%')
                 OR e.codigoEstudiante      LIKE CONCAT('%', :termino, '%'))
            ORDER BY p.primerApellido ASC, p.primerNombre ASC
            """)
    Page<Estudiante> buscar(@Param("termino") String termino, Pageable pageable);

    @Query("SELECT e FROM Estudiante e JOIN FETCH e.persona WHERE e.id = :id")
    Optional<Estudiante> findByIdWithPersona(@Param("id") Long id);

    /** Siguiente secuencia para número de código de estudiante */
    @Query("SELECT COUNT(e) + 1 FROM Estudiante e WHERE YEAR(e.persona.fechaNacimiento) > 0")
    Long nextSecuencia();
}
