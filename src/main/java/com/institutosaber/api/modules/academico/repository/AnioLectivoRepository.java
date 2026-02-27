package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.AnioLectivo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AnioLectivoRepository extends JpaRepository<AnioLectivo, Long> {
    Optional<AnioLectivo> findByActivo(Boolean activo);

    Optional<AnioLectivo> findByAnio(Integer anio);

    List<AnioLectivo> findAllByOrderByAnioDesc();

    boolean existsByAnio(Integer anio);

    boolean existsByAnioAndIdNot(Integer anio, Long id);
}
