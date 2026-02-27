package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.Area;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findByActivoTrueOrderByNombreAsc();

    Page<Area> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    boolean existsByNombre(String nombre);

    boolean existsByNombreAndIdNot(String nombre, Long id);
}
