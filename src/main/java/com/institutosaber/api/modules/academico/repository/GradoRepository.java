package com.institutosaber.api.modules.academico.repository;

import com.institutosaber.api.modules.academico.entity.Grado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GradoRepository extends JpaRepository<Grado, Long> {
    List<Grado> findByNivelIdOrderByOrdenAsc(Long nivelId);

    List<Grado> findAllByOrderByNivelOrdenAscOrdenAsc();

    @Query("SELECT g FROM Grado g JOIN FETCH g.nivel ORDER BY g.nivel.orden ASC, g.orden ASC")
    List<Grado> findAllWithNivel();
}
