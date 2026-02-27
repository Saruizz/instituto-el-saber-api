package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface PeriodoRepository extends JpaRepository<Periodo, Long> {
    List<Periodo> findByAnioLectivoIdOrderByNumeroPeriodoAsc(Long anioLectivoId);

    Optional<Periodo> findByAnioLectivoIdAndActivo(Long anioLectivoId, Boolean activo);

    boolean existsByAnioLectivoIdAndNumeroPeriodo(Long anioLectivoId, Integer numeroPeriodo);

    boolean existsByAnioLectivoIdAndNumeroPeriodoAndIdNot(Long anioLectivoId, Integer numero, Long id);

    @Query("""
            SELECT p FROM Periodo p JOIN FETCH p.anioLectivo
            WHERE p.anioLectivo.id = :anioId
            ORDER BY p.numeroPeriodo ASC
            """)
    List<Periodo> findByAnioLectivoIdWithDetails(@Param("anioId") Long anioId);
}
