package com.institutosaber.api.modules.estudiantes.entity;

import com.institutosaber.api.modules.academico.entity.AnioLectivo;
import com.institutosaber.api.modules.academico.entity.Grado;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "matriculas", uniqueConstraints = @UniqueConstraint(columnNames = { "estudiante_id",
        "anio_lectivo_id" }, name = "uk_matricula_estudiante_anio"))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anio_lectivo_id", nullable = false)
    private AnioLectivo anioLectivo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grado_id", nullable = false)
    private Grado grado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "acudiente_id")
    private Acudiente acudiente;

    @Column(name = "numero_matricula", unique = true, length = 20)
    private String numeroMatricula;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    /** Estado: ACTIVA, RETIRADA, TRASLADADA, PROMOCIONADA */
    @Column(name = "estado", nullable = false, length = 20)
    @Builder.Default
    private String estado = "ACTIVA";

    @Column(name = "valor_matricula", precision = 12, scale = 2)
    private BigDecimal valorMatricula;

    @Column(name = "descuento_porcentaje")
    @Builder.Default
    private Integer descuentoPorcentaje = 0;

    @Column(name = "observaciones", length = 200)
    private String observaciones;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    // ── Campos legacy (read-only) ────────────────────────────────────────────
    @Column(name = "tipo_matricula", length = 10, insertable = false, updatable = false)
    private String tipoMatricula;

    @Column(name = "activo", insertable = false, updatable = false)
    private Boolean activo;
}
