-- ============================================================
-- V2__crear_tablas_personas.sql
-- Tablas base de personas, acudientes y relación con estudiantes
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE personas (
    id                  BIGSERIAL       PRIMARY KEY,
    tipo_documento      VARCHAR(10)     NOT NULL,   -- RC | TI | CC | CE
    numero_documento    VARCHAR(30)     NOT NULL UNIQUE,
    fecha_expedicion    DATE,
    lugar_expedicion    VARCHAR(100),
    apellidos           VARCHAR(150)    NOT NULL,
    nombres             VARCHAR(150)    NOT NULL,
    fecha_nacimiento    DATE            NOT NULL,
    lugar_nacimiento    VARCHAR(100),
    genero              VARCHAR(10),                -- M | F | OTRO
    direccion           VARCHAR(200),
    barrio              VARCHAR(100),
    telefono            VARCHAR(20),
    celular             VARCHAR(20),
    email               VARCHAR(150),
    foto_url            VARCHAR(500),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE acudientes (
    id                  BIGSERIAL       PRIMARY KEY,
    persona_id          BIGINT          REFERENCES personas(id),
    nombre_padre        VARCHAR(200),
    ocupacion_padre     VARCHAR(150),
    telefono_padre      VARCHAR(20),
    nombre_madre        VARCHAR(200),
    ocupacion_madre     VARCHAR(150),
    telefono_madre      VARCHAR(20),
    nombre_acudiente    VARCHAR(200),
    parentesco          VARCHAR(50),
    telefono_acudiente  VARCHAR(20),
    usuario_id          BIGINT          REFERENCES usuarios(id),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Índices
CREATE INDEX idx_personas_documento     ON personas(numero_documento);
CREATE INDEX idx_personas_apellidos     ON personas(apellidos);
CREATE INDEX idx_acudientes_persona     ON acudientes(persona_id);
