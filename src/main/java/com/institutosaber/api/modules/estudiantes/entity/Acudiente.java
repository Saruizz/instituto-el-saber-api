package com.institutosaber.api.modules.estudiantes.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "acudientes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Acudiente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "persona_id", nullable = false, unique = true)
    private Persona persona;

    @Column(name = "parentesco", nullable = false, length = 80)
    private String parentesco;

    @Column(name = "ocupacion", length = 100)
    private String ocupacion;

    @Column(name = "lugar_trabajo", length = 200)
    private String lugarTrabajo;

    @Column(name = "telefono_trabajo", length = 30)
    private String telefonoTrabajo;

    @Column(name = "es_responsable_economico", nullable = false)
    @Builder.Default
    private Boolean esResponsableEconomico = true;

    // ── Columnas legacy (read-only para retrocompatibilidad) ───────────────
    @Column(name = "nombre_acudiente", length = 200, insertable = false, updatable = false)
    private String nombreAcudiente;

    @Column(name = "telefono_acudiente", length = 20, insertable = false, updatable = false)
    private String telefonoAcudiente;
}
