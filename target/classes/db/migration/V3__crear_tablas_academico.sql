-- ============================================================
-- V3__crear_tablas_academico.sql
-- Estructura académica: niveles, grados, áreas, asignaturas,
-- años lectivos y periodos
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE niveles_educativos (
    id      BIGSERIAL       PRIMARY KEY,
    nombre  VARCHAR(80)     NOT NULL UNIQUE,  -- PREESCOLAR | PRIMARIA | SECUNDARIA | MEDIA
    orden   INTEGER         NOT NULL
);

CREATE TABLE grados (
    id          BIGSERIAL       PRIMARY KEY,
    nivel_id    BIGINT          NOT NULL REFERENCES niveles_educativos(id),
    nombre      VARCHAR(80)     NOT NULL,
    descripcion VARCHAR(200),
    orden       INTEGER         NOT NULL
);

CREATE TABLE areas (
    id          BIGSERIAL       PRIMARY KEY,
    nombre      VARCHAR(150)    NOT NULL UNIQUE,
    descripcion VARCHAR(300),
    activo      BOOLEAN         NOT NULL DEFAULT TRUE
);

CREATE TABLE asignaturas (
    id          BIGSERIAL       PRIMARY KEY,
    area_id     BIGINT          NOT NULL REFERENCES areas(id),
    nombre      VARCHAR(150)    NOT NULL,
    descripcion VARCHAR(300),
    activo      BOOLEAN         NOT NULL DEFAULT TRUE
);

CREATE TABLE grados_asignaturas (
    id                          BIGSERIAL   PRIMARY KEY,
    grado_id                    BIGINT      NOT NULL REFERENCES grados(id),
    asignatura_id               BIGINT      NOT NULL REFERENCES asignaturas(id),
    intensidad_horaria_semanal  INTEGER     NOT NULL DEFAULT 1,
    UNIQUE (grado_id, asignatura_id)
);

CREATE TABLE anios_lectivos (
    id          BIGSERIAL       PRIMARY KEY,
    anio        INTEGER         NOT NULL UNIQUE,
    fecha_inicio DATE           NOT NULL,
    fecha_fin    DATE           NOT NULL,
    activo      BOOLEAN         NOT NULL DEFAULT FALSE,
    descripcion VARCHAR(200),
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE periodos (
    id              BIGSERIAL   PRIMARY KEY,
    anio_lectivo_id BIGINT      NOT NULL REFERENCES anios_lectivos(id),
    numero_periodo  INTEGER     NOT NULL,   -- 1, 2, 3, 4
    nombre          VARCHAR(80) NOT NULL,
    fecha_inicio    DATE        NOT NULL,
    fecha_fin       DATE        NOT NULL,
    activo          BOOLEAN     NOT NULL DEFAULT FALSE,
    UNIQUE (anio_lectivo_id, numero_periodo)
);

-- Índices
CREATE INDEX idx_grados_nivel          ON grados(nivel_id);
CREATE INDEX idx_asignaturas_area      ON asignaturas(area_id);
CREATE INDEX idx_periodos_anio_lectivo ON periodos(anio_lectivo_id);
CREATE INDEX idx_anios_lectivos_activo ON anios_lectivos(activo);
