-- ============================================================
-- V11__adaptar_tablas_estudiantes_m06.sql
-- Adapta las tablas personas, acudientes, estudiantes y
-- matriculas para soportar el modelo M06 del Módulo Estudiantes.
-- Creado: 2026-02-27 | SABER-SIS Instituto El Saber
-- ============================================================

-- ─────────────────────────────────────────────────────────────
-- 1. Ampliar tabla PERSONAS con los campos del nuevo modelo
-- ─────────────────────────────────────────────────────────────
ALTER TABLE personas
    ADD COLUMN IF NOT EXISTS primer_nombre           VARCHAR(60),
    ADD COLUMN IF NOT EXISTS segundo_nombre          VARCHAR(60),
    ADD COLUMN IF NOT EXISTS primer_apellido         VARCHAR(60),
    ADD COLUMN IF NOT EXISTS segundo_apellido        VARCHAR(60),
    ADD COLUMN IF NOT EXISTS municipio_nacimiento    VARCHAR(100),
    ADD COLUMN IF NOT EXISTS departamento_nacimiento VARCHAR(100),
    ADD COLUMN IF NOT EXISTS municipio_residencia    VARCHAR(100);

-- Copiar datos legacy si existen (split de nombres y apellidos)
UPDATE personas
SET
    primer_nombre   = SPLIT_PART(nombres,   ' ', 1),
    segundo_nombre  = CASE WHEN ARRAY_LENGTH(STRING_TO_ARRAY(nombres, ' '), 1) > 1
                           THEN SPLIT_PART(nombres, ' ', 2) ELSE NULL END,
    primer_apellido = SPLIT_PART(apellidos, ' ', 1),
    segundo_apellido= CASE WHEN ARRAY_LENGTH(STRING_TO_ARRAY(apellidos, ' '), 1) > 1
                           THEN SPLIT_PART(apellidos, ' ', 2) ELSE NULL END,
    municipio_nacimiento    = COALESCE(lugar_nacimiento, 'Santa Marta'),
    departamento_nacimiento = 'Magdalena',
    municipio_residencia    = 'Santa Marta',
    genero = CASE genero WHEN 'M' THEN 'MASCULINO' WHEN 'F' THEN 'FEMENINO' ELSE COALESCE(genero, 'MASCULINO') END
WHERE primer_nombre IS NULL;

-- Aseguramos NOT NULL con defaults para filas vacías
UPDATE personas SET primer_nombre   = 'Sin nombre'  WHERE primer_nombre   IS NULL OR primer_nombre   = '';
UPDATE personas SET primer_apellido = 'Sin apellido' WHERE primer_apellido IS NULL OR primer_apellido = '';
UPDATE personas SET municipio_nacimiento    = 'Santa Marta' WHERE municipio_nacimiento    IS NULL;
UPDATE personas SET departamento_nacimiento = 'Magdalena'   WHERE departamento_nacimiento IS NULL;
UPDATE personas SET genero = 'MASCULINO' WHERE genero IS NULL OR (genero NOT IN ('MASCULINO', 'FEMENINO', 'OTRO'));

-- ─────────────────────────────────────────────────────────────
-- 2. Adaptar tabla ACUDIENTES al nuevo modelo
-- ─────────────────────────────────────────────────────────────
ALTER TABLE acudientes
    ADD COLUMN IF NOT EXISTS ocupacion               VARCHAR(100),
    ADD COLUMN IF NOT EXISTS lugar_trabajo           VARCHAR(200),
    ADD COLUMN IF NOT EXISTS telefono_trabajo        VARCHAR(30),
    ADD COLUMN IF NOT EXISTS es_responsable_economico BOOLEAN NOT NULL DEFAULT TRUE;

-- Copiar datos legacy
UPDATE acudientes
SET
    ocupacion   = COALESCE(ocupacion_padre, ocupacion_madre),
    parentesco  = COALESCE(parentesco, 'OTRO')
WHERE ocupacion IS NULL;

-- ─────────────────────────────────────────────────────────────
-- 3. Adaptar tabla ESTUDIANTES al nuevo modelo
-- ─────────────────────────────────────────────────────────────
ALTER TABLE estudiantes
    ADD COLUMN IF NOT EXISTS codigo_estudiante       VARCHAR(20),
    ADD COLUMN IF NOT EXISTS grupo_sanguineo         VARCHAR(5),
    ADD COLUMN IF NOT EXISTS condicion_especial      VARCHAR(200),
    ADD COLUMN IF NOT EXISTS institucion_procedencia VARCHAR(200),
    ADD COLUMN IF NOT EXISTS observaciones           VARCHAR(300);

-- Generar códigos automáticos para filas existentes
DO $$
DECLARE
    rec RECORD;
    seq INT := 1;
    anio INT := EXTRACT(YEAR FROM NOW())::INT;
BEGIN
    FOR rec IN SELECT id FROM estudiantes WHERE codigo_estudiante IS NULL ORDER BY id LOOP
        UPDATE estudiantes SET codigo_estudiante = anio || '-' || LPAD(seq::TEXT, 4, '0')
        WHERE id = rec.id;
        seq := seq + 1;
    END LOOP;
END
$$;

-- Unique constraint en codigo_estudiante
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_constraint WHERE conname = 'uk_estudiante_codigo') THEN
        ALTER TABLE estudiantes ADD CONSTRAINT uk_estudiante_codigo UNIQUE (codigo_estudiante);
    END IF;
END
$$;

-- ─────────────────────────────────────────────────────────────
-- 4. Adaptar tabla MATRICULAS al nuevo modelo
-- ─────────────────────────────────────────────────────────────
ALTER TABLE matriculas
    ADD COLUMN IF NOT EXISTS acudiente_id        BIGINT REFERENCES acudientes(id),
    ADD COLUMN IF NOT EXISTS estado              VARCHAR(20) NOT NULL DEFAULT 'ACTIVA',
    ADD COLUMN IF NOT EXISTS valor_matricula     NUMERIC(12,2),
    ADD COLUMN IF NOT EXISTS descuento_porcentaje INTEGER NOT NULL DEFAULT 0;

-- Migrar campo 'activo' a 'estado'
UPDATE matriculas SET estado = CASE WHEN activo THEN 'ACTIVA' ELSE 'RETIRADA' END
WHERE estado = 'ACTIVA' AND activo = FALSE;

-- ─────────────────────────────────────────────────────────────
-- 5. Índices adicionales
-- ─────────────────────────────────────────────────────────────
CREATE INDEX IF NOT EXISTS idx_personas_primer_apellido  ON personas(primer_apellido);
CREATE INDEX IF NOT EXISTS idx_personas_primer_nombre    ON personas(primer_nombre);
CREATE INDEX IF NOT EXISTS idx_estudiantes_codigo        ON estudiantes(codigo_estudiante);
CREATE INDEX IF NOT EXISTS idx_matriculas_estado         ON matriculas(estado);
CREATE INDEX IF NOT EXISTS idx_matriculas_acudiente      ON matriculas(acudiente_id);
