-- ============================================================
-- V13__corregir_password_admin.sql
-- El hash en V10/V12 era incorrecto (no correspondía a admin123).
-- Este script actualiza la contraseña del usuario admin.
-- Nueva contraseña: admin123
-- Hash BCrypt factor 10, generado y verificado con
-- org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder(10)
-- Creado: 2026-02-27 | SABER-SIS Instituto El Saber
-- ============================================================

-- Actualizar password del admin con hash correcto de admin123
UPDATE usuarios
SET password_hash = '$2a$10$slYQmyNdgTY18LGvgxPwOuwH5LwKi/GEoNiTozzmFXzX4Gj.aMLrm'
WHERE username = 'admin';
