-- ============================================================
-- V12__reset_admin_password.sql
-- Asegura que el usuario admin exista con contraseña correcta.
-- Contraseña: admin123 (BCrypt factor 12)
-- Hash verificado: $2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy
-- Creado: 2026-02-27 | SABER-SIS Instituto El Saber
-- ============================================================

-- Asegurar que el rol ADMIN existe
INSERT INTO roles (nombre, descripcion)
VALUES ('ROLE_ADMIN', 'Administrador del sistema. Acceso total.')
ON CONFLICT (nombre) DO NOTHING;

-- Actualizar hash si el usuario ya existe,
-- o insertarlo si fue borrado accidentalmente
INSERT INTO usuarios (username, password_hash, email, activo)
VALUES (
    'admin',
    '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
    'admin@institutosaber.edu.co',
    TRUE
)
ON CONFLICT (username) DO UPDATE
    SET password_hash = '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
        activo        = TRUE;

-- Asegurar que tiene el rol ADMIN asignado
INSERT INTO usuarios_roles (usuario_id, rol_id)
SELECT u.id, r.id
FROM   usuarios u
CROSS JOIN roles r
WHERE  u.username = 'admin'
AND    r.nombre   = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

