-- ============================================================
-- V6__crear_tablas_notas_asistencia.sql
-- Notas por periodo y control de asistencia diaria
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE notas (
    id                      BIGSERIAL       PRIMARY KEY,
    matricula_id            BIGINT          NOT NULL REFERENCES matriculas(id),
    asignatura_id           BIGINT          NOT NULL REFERENCES asignaturas(id),
    periodo_id              BIGINT          NOT NULL REFERENCES periodos(id),
    nota_ser                NUMERIC(4,1)    CHECK (nota_ser BETWEEN 1.0 AND 10.0),
    nota_saber              NUMERIC(4,1)    CHECK (nota_saber BETWEEN 1.0 AND 10.0),
    nota_hacer              NUMERIC(4,1)    CHECK (nota_hacer BETWEEN 1.0 AND 10.0),
    nota_convivir           NUMERIC(4,1)    CHECK (nota_convivir BETWEEN 1.0 AND 10.0),
    nota_definitiva         NUMERIC(4,1)    CHECK (nota_definitiva BETWEEN 1.0 AND 10.0),
    concepto_cualitativo    VARCHAR(20)     CHECK (concepto_cualitativo IN ('SUPERIOR','ALTO','BASICO','BAJO')),
    observacion             TEXT,
    registrado_por          BIGINT          REFERENCES usuarios(id),
    created_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP       NOT NULL DEFAULT NOW(),
    UNIQUE (matricula_id, asignatura_id, periodo_id)
);

CREATE TABLE asistencias (
    id              BIGSERIAL   PRIMARY KEY,
    matricula_id    BIGINT      NOT NULL REFERENCES matriculas(id),
    asignatura_id   BIGINT      NOT NULL REFERENCES asignaturas(id),
    fecha           DATE        NOT NULL,
    estado          VARCHAR(15) NOT NULL CHECK (estado IN ('PRESENTE','AUSENTE','TARDANZA','EXCUSADO')),
    observacion     VARCHAR(300),
    registrado_por  BIGINT      REFERENCES usuarios(id),
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    UNIQUE (matricula_id, asignatura_id, fecha)
);

-- Índices
CREATE INDEX idx_notas_matricula         ON notas(matricula_id);
CREATE INDEX idx_notas_periodo           ON notas(periodo_id);
CREATE INDEX idx_notas_asignatura        ON notas(asignatura_id);
CREATE INDEX idx_asistencias_matricula   ON asistencias(matricula_id);
CREATE INDEX idx_asistencias_fecha       ON asistencias(fecha);
CREATE INDEX idx_asistencias_asignatura  ON asistencias(asignatura_id);
