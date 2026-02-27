package com.institutosaber.api.modules.academico.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "grados_asignaturas", uniqueConstraints = @UniqueConstraint(columnNames = { "grado_id",
        "asignatura_id" }))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GradoAsignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asignatura_id", nullable = false)
    private Asignatura asignatura;

    @Column(name = "intensidad_horaria_semanal", nullable = false)
    @Builder.Default
    private Integer intensidadHorariaSemanal = 1;
}
