package com.institutosaber.api.modules.estudiantes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "estudiantes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona_id", nullable = false, unique = true)
    private Persona persona;

    @Column(name = "codigo_estudiante", unique = true, length = 20)
    private String codigoEstudiante;

    @Column(name = "activo", nullable = false)
    @Builder.Default
    private Boolean activo = true;

    @Column(name = "grupo_sanguineo", length = 5)
    private String grupoSanguineo;

    @Column(name = "condicion_especial", length = 200)
    private String condicionEspecial;

    @Column(name = "institucion_procedencia", length = 200)
    private String institucionProcedencia;

    @Column(name = "observaciones", length = 300)
    private String observaciones;

    // ── Columnas legacy (read-only) ──────────────────────────────────────────
    @Column(name = "eps", length = 100, insertable = false, updatable = false)
    private String eps;

    @Column(name = "estrato", insertable = false, updatable = false)
    private Integer estrato;
}
