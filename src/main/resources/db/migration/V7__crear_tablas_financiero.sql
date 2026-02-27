-- ============================================================
-- V7__crear_tablas_financiero.sql
-- Conceptos de cobro, cobros (cuentas por cobrar) y pagos
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- ============================================================

CREATE TABLE conceptos_cobro (
    id              BIGSERIAL       PRIMARY KEY,
    nombre          VARCHAR(150)    NOT NULL UNIQUE,
    descripcion     VARCHAR(300),
    valor_base      NUMERIC(15,2)   NOT NULL DEFAULT 0,
    tipo            VARCHAR(20)     NOT NULL CHECK (tipo IN ('PENSION','MATRICULA','OTRO_COBRO')),
    periodicidad    VARCHAR(15)     NOT NULL CHECK (periodicidad IN ('MENSUAL','UNICO','ANUAL')),
    activo          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE cobros (
    id                  BIGSERIAL       PRIMARY KEY,
    matricula_id        BIGINT          NOT NULL REFERENCES matriculas(id),
    concepto_cobro_id   BIGINT          NOT NULL REFERENCES conceptos_cobro(id),
    anio_lectivo_id     BIGINT          NOT NULL REFERENCES anios_lectivos(id),
    periodo_id          BIGINT          REFERENCES periodos(id),
    mes                 INTEGER         CHECK (mes BETWEEN 1 AND 12),
    valor_total         NUMERIC(15,2)   NOT NULL,
    descuento           NUMERIC(15,2)   NOT NULL DEFAULT 0,
    valor_a_pagar       NUMERIC(15,2)   NOT NULL,
    estado              VARCHAR(15)     NOT NULL DEFAULT 'PENDIENTE'
                            CHECK (estado IN ('PENDIENTE','PAGADO','PARCIAL','ANULADO')),
    fecha_vencimiento   DATE,
    observacion         TEXT,
    created_at          TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP       NOT NULL DEFAULT NOW()
);

CREATE TABLE pagos (
    id              BIGSERIAL       PRIMARY KEY,
    cobro_id        BIGINT          NOT NULL REFERENCES cobros(id),
    valor_pagado    NUMERIC(15,2)   NOT NULL,
    fecha_pago      TIMESTAMP       NOT NULL DEFAULT NOW(),
    metodo_pago     VARCHAR(30)     NOT NULL CHECK (metodo_pago IN ('EFECTIVO','TRANSFERENCIA','CHEQUE','OTRO')),
    numero_recibo   VARCHAR(30)     NOT NULL UNIQUE,
    cajero_id       BIGINT          REFERENCES usuarios(id),
    observacion     TEXT,
    anulado         BOOLEAN         NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

-- Índices
CREATE INDEX idx_cobros_matricula     ON cobros(matricula_id);
CREATE INDEX idx_cobros_estado        ON cobros(estado);
CREATE INDEX idx_cobros_anio          ON cobros(anio_lectivo_id);
CREATE INDEX idx_cobros_vencimiento   ON cobros(fecha_vencimiento);
CREATE INDEX idx_pagos_cobro          ON pagos(cobro_id);
CREATE INDEX idx_pagos_recibo         ON pagos(numero_recibo);
