package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.NivelEducativo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NivelEducativoRepository extends JpaRepository<NivelEducativo, Long> {
    List<NivelEducativo> findAllByOrderByOrdenAsc();

    boolean existsByNombre(String nombre);
}
