-- ============================================================
-- V1__crear_tablas_auth.sql
-- Tablas de autenticación: roles, usuarios, usuarios_roles
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE roles (
    id          BIGSERIAL       PRIMARY KEY,
    nombre      VARCHAR(50)     NOT NULL UNIQUE,
    descripcion VARCHAR(200),
    created_at  TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE usuarios (
    id              BIGSERIAL       PRIMARY KEY,
    username        VARCHAR(80)     NOT NULL UNIQUE,
    password_hash   VARCHAR(255)    NOT NULL,
    email           VARCHAR(150)    UNIQUE,
    activo          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE usuarios_roles (
    usuario_id  BIGINT  NOT NULL REFERENCES usuarios(id) ON DELETE CASCADE,
    rol_id      BIGINT  NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    PRIMARY KEY (usuario_id, rol_id)
);

-- Índices
CREATE INDEX idx_usuarios_username ON usuarios(username);
CREATE INDEX idx_usuarios_email    ON usuarios(email);
CREATE INDEX idx_usuarios_activo   ON usuarios(activo);
