-- ============================================================
-- V4__crear_tablas_estudiantes.sql
-- Estudiantes, matrículas e historial de matrículas
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE estudiantes (
    id              BIGSERIAL   PRIMARY KEY,
    persona_id      BIGINT      NOT NULL UNIQUE REFERENCES personas(id),
    eps             VARCHAR(100),
    tiene_sisben    BOOLEAN     NOT NULL DEFAULT FALSE,
    nivel_sisben    VARCHAR(10),            -- A1-A5, B1-B8, C1-C18, D21
    estrato         INTEGER     CHECK (estrato BETWEEN 1 AND 6),
    activo          BOOLEAN     NOT NULL DEFAULT TRUE,
    usuario_id      BIGINT      REFERENCES usuarios(id),
    acudiente_id    BIGINT      REFERENCES acudientes(id),
    created_at      TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP   NOT NULL DEFAULT NOW()
);

CREATE TABLE matriculas (
    id                  BIGSERIAL       PRIMARY KEY,
    estudiante_id       BIGINT          NOT NULL REFERENCES estudiantes(id),
    anio_lectivo_id     BIGINT          NOT NULL REFERENCES anios_lectivos(id),
    grado_id            BIGINT          NOT NULL REFERENCES grados(id),
    tipo_matricula      VARCHAR(10)     NOT NULL CHECK (tipo_matricula IN ('NUEVO','ANTIGUO')),
    fecha_matricula     DATE            NOT NULL DEFAULT CURRENT_DATE,
    numero_matricula    VARCHAR(20)     NOT NULL UNIQUE,
    colegio_procedencia VARCHAR(200),
    activo              BOOLEAN         NOT NULL DEFAULT TRUE,
    observaciones       TEXT,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    UNIQUE (estudiante_id, anio_lectivo_id)
);

CREATE TABLE historial_matriculas (
    id              BIGSERIAL   PRIMARY KEY,
    estudiante_id   BIGINT      NOT NULL REFERENCES estudiantes(id),
    anio_lectivo_id BIGINT      NOT NULL REFERENCES anios_lectivos(id),
    grado_id        BIGINT      NOT NULL REFERENCES grados(id),
    tipo_matricula  VARCHAR(10) NOT NULL,
    estado          VARCHAR(20) NOT NULL DEFAULT 'ACTIVO'  -- ACTIVO | RETIRADO | PROMOVIDO | REPROBADO
);

-- Índices
CREATE INDEX idx_estudiantes_persona     ON estudiantes(persona_id);
CREATE INDEX idx_estudiantes_activo      ON estudiantes(activo);
CREATE INDEX idx_matriculas_estudiante   ON matriculas(estudiante_id);
CREATE INDEX idx_matriculas_anio_grado   ON matriculas(anio_lectivo_id, grado_id);
CREATE INDEX idx_matriculas_numero       ON matriculas(numero_matricula);
CREATE INDEX idx_historial_estudiante    ON historial_matriculas(estudiante_id);
