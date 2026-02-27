-- ============================================================
-- V9__crear_tabla_configuracion.sql
-- Tabla de parámetros configurables del sistema
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE configuracion (
    id          BIGSERIAL       PRIMARY KEY,
    clave       VARCHAR(80)     NOT NULL UNIQUE,
    valor       VARCHAR(500)    NOT NULL,
    descripcion VARCHAR(300),
    updated_at  TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_configuracion_clave ON configuracion(clave);
