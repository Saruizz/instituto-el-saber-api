package com.institutosaber.api.modules.estudiantes.repository;

import com.institutosaber.api.modules.estudiantes.entity.Acudiente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AcudienteRepository extends JpaRepository<Acudiente, Long> {

    Optional<Acudiente> findByPersonaNumeroDocumento(String numeroDocumento);

    boolean existsByPersonaNumeroDocumento(String numeroDocumento);

    @Query("SELECT a FROM Acudiente a JOIN FETCH a.persona WHERE a.id = :id")
    Optional<Acudiente> findByIdWithPersona(@Param("id") Long id);

    @Query("""
            SELECT a FROM Acudiente a JOIN FETCH a.persona p
            WHERE LOWER(p.primerApellido) LIKE LOWER(CONCAT('%', :termino, '%'))
               OR p.numeroDocumento LIKE CONCAT('%', :termino, '%')
            ORDER BY p.primerApellido ASC, p.primerNombre ASC
            """)
    List<Acudiente> buscar(@Param("termino") String termino);
}
