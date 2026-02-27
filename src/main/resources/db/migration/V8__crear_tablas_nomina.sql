-- ============================================================
-- V8__crear_tablas_nomina.sql
-- Nómina mensual y detalle por empleado
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE nominas (
    id                  BIGSERIAL       PRIMARY KEY,
    anio_lectivo_id     BIGINT          NOT NULL REFERENCES anios_lectivos(id),
    mes                 INTEGER         NOT NULL CHECK (mes BETWEEN 1 AND 12),
    anio                INTEGER         NOT NULL,
    estado              VARCHAR(15)     NOT NULL DEFAULT 'BORRADOR'
                            CHECK (estado IN ('BORRADOR','APROBADA','PAGADA')),
    fecha_pago          DATE,
    total_devengado     NUMERIC(15,2)   NOT NULL DEFAULT 0,
    total_deducciones   NUMERIC(15,2)   NOT NULL DEFAULT 0,
    total_neto          NUMERIC(15,2)   NOT NULL DEFAULT 0,
    creado_por          BIGINT          REFERENCES usuarios(id),
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    UNIQUE (mes, anio)
);

CREATE TABLE nomina_detalle (
    id                  BIGSERIAL       PRIMARY KEY,
    nomina_id           BIGINT          NOT NULL REFERENCES nominas(id) ON DELETE CASCADE,
    personal_id         BIGINT          NOT NULL REFERENCES personal(id),
    salario_base        NUMERIC(15,2)   NOT NULL,
    horas_extras        NUMERIC(15,2)   NOT NULL DEFAULT 0,
    bonificaciones      NUMERIC(15,2)   NOT NULL DEFAULT 0,
    salud_empleado      NUMERIC(15,2)   NOT NULL DEFAULT 0,
    pension_empleado    NUMERIC(15,2)   NOT NULL DEFAULT 0,
    retencion_fuente    NUMERIC(15,2)   NOT NULL DEFAULT 0,
    otros_descuentos    NUMERIC(15,2)   NOT NULL DEFAULT 0,
    total_devengado     NUMERIC(15,2)   NOT NULL,
    total_deducciones   NUMERIC(15,2)   NOT NULL,
    neto_pagar          NUMERIC(15,2)   NOT NULL,
    observacion         TEXT,
    UNIQUE (nomina_id, personal_id)
);

-- Índices
CREATE INDEX idx_nominas_estado         ON nominas(estado);
CREATE INDEX idx_nominas_mes_anio       ON nominas(mes, anio);
CREATE INDEX idx_nomina_detalle_nomina  ON nomina_detalle(nomina_id);
CREATE INDEX idx_nomina_detalle_perso   ON nomina_detalle(personal_id);
