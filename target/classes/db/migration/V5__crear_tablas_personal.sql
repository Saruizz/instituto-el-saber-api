-- ============================================================
-- V5__crear_tablas_personal.sql
-- Personal docente y administrativo, asignación de clases
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE personal (
    id              BIGSERIAL       PRIMARY KEY,
    persona_id      BIGINT          NOT NULL UNIQUE REFERENCES personas(id),
    cargo           VARCHAR(100)    NOT NULL,
    tipo_contrato   VARCHAR(30)     NOT NULL,   -- PLANTA | PROVISIONAL | HORA_CATEDRA | CONTRATO
    fecha_ingreso   DATE            NOT NULL,
    fecha_retiro    DATE,
    salario_base    NUMERIC(15,2)   NOT NULL DEFAULT 0,
    activo          BOOLEAN         NOT NULL DEFAULT TRUE,
    usuario_id      BIGINT          REFERENCES usuarios(id),
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE docentes_asignaturas_grados (
    id              BIGSERIAL   PRIMARY KEY,
    personal_id     BIGINT      NOT NULL REFERENCES personal(id),
    asignatura_id   BIGINT      NOT NULL REFERENCES asignaturas(id),
    grado_id        BIGINT      NOT NULL REFERENCES grados(id),
    anio_lectivo_id BIGINT      NOT NULL REFERENCES anios_lectivos(id),
    activo          BOOLEAN     NOT NULL DEFAULT TRUE,
    UNIQUE (personal_id, asignatura_id, grado_id, anio_lectivo_id)
);

-- Índices
CREATE INDEX idx_personal_persona          ON personal(persona_id);
CREATE INDEX idx_personal_activo           ON personal(activo);
CREATE INDEX idx_docentes_asig_personal    ON docentes_asignaturas_grados(personal_id);
CREATE INDEX idx_docentes_asig_grado       ON docentes_asignaturas_grados(grado_id, anio_lectivo_id);
