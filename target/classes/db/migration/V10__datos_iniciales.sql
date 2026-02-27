-- ============================================================
-- V10__datos_iniciales.sql
-- Datos semilla: roles, niveles educativos, áreas MEN,
-- usuario administrador por defecto
-- Creado: 2026-02-25 | SABER-SIS Instituto El Saber
-- NOTA: Contraseña admin = "admin123" (BCrypt factor 12)
-- ============================================================

-- ROLES
INSERT INTO roles (nombre, descripcion) VALUES
  ('ROLE_ADMIN',       'Administrador del sistema. Acceso total.'),
  ('ROLE_RECTOR',      'Rector del colegio. Acceso a todos los módulos.'),
  ('ROLE_SECRETARIA',  'Secretaria académica. Matrículas, estudiantes, certificados.'),
  ('ROLE_DOCENTE',     'Docente. Registro de notas y asistencia de sus asignaturas.'),
  ('ROLE_ACUDIENTE',   'Padre/Acudiente. Portal de consulta: notas, asistencia, cobros.');

-- USUARIO ADMIN POR DEFECTO
INSERT INTO usuarios (username, password_hash, email, activo)
VALUES ('admin', '$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'admin@institutosaber.edu.co', TRUE);

-- Asignar rol ADMIN
INSERT INTO usuarios_roles (usuario_id, rol_id)
SELECT u.id, r.id FROM usuarios u, roles r
WHERE u.username = 'admin' AND r.nombre = 'ROLE_ADMIN';

-- NIVELES EDUCATIVOS
INSERT INTO niveles_educativos (nombre, orden) VALUES
  ('PREESCOLAR', 1),
  ('PRIMARIA',   2),
  ('SECUNDARIA', 3),
  ('MEDIA',      4);

-- GRADOS — PREESCOLAR
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Prejardín',  'Preescolar - Prejardín',  1 FROM niveles_educativos WHERE nombre = 'PREESCOLAR';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Jardín',     'Preescolar - Jardín',     2 FROM niveles_educativos WHERE nombre = 'PREESCOLAR';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Transición', 'Preescolar - Transición', 3 FROM niveles_educativos WHERE nombre = 'PREESCOLAR';

-- GRADOS — PRIMARIA
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 1°', 'Primaria - Primero',  1 FROM niveles_educativos WHERE nombre = 'PRIMARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 2°', 'Primaria - Segundo',  2 FROM niveles_educativos WHERE nombre = 'PRIMARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 3°', 'Primaria - Tercero',  3 FROM niveles_educativos WHERE nombre = 'PRIMARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 4°', 'Primaria - Cuarto',   4 FROM niveles_educativos WHERE nombre = 'PRIMARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 5°', 'Primaria - Quinto',   5 FROM niveles_educativos WHERE nombre = 'PRIMARIA';

-- GRADOS — SECUNDARIA
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 6°', 'Secundaria - Sexto',   1 FROM niveles_educativos WHERE nombre = 'SECUNDARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 7°', 'Secundaria - Séptimo', 2 FROM niveles_educativos WHERE nombre = 'SECUNDARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 8°', 'Secundaria - Octavo',  3 FROM niveles_educativos WHERE nombre = 'SECUNDARIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 9°', 'Secundaria - Noveno',  4 FROM niveles_educativos WHERE nombre = 'SECUNDARIA';

-- GRADOS — MEDIA
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 10°', 'Media - Décimo',     1 FROM niveles_educativos WHERE nombre = 'MEDIA';
INSERT INTO grados (nivel_id, nombre, descripcion, orden)
SELECT id, 'Grado 11°', 'Media - Undécimo',   2 FROM niveles_educativos WHERE nombre = 'MEDIA';

-- ÁREAS ACADÉMICAS (Decreto 1290 MEN Colombia)
INSERT INTO areas (nombre, descripcion) VALUES
  ('Matemáticas',                                     'Área de Matemáticas'),
  ('Lengua Castellana',                               'Humanidades - Lengua Castellana'),
  ('Ciencias Naturales y Educación Ambiental',        'Biología, Química, Física y Educación Ambiental'),
  ('Ciencias Sociales, Historia y Geografía',         'Historia, Geografía, Democracia y Constitución'),
  ('Educación Física, Recreación y Deportes',         'Educación Física'),
  ('Educación Artística y Cultural',                  'Artes visuales, música, danza, teatro'),
  ('Educación Religiosa',                             'Educación Religiosa y Moral'),
  ('Tecnología e Informática',                        'Tecnología e Informática'),
  ('Inglés',                                          'Humanidades - Lengua Extranjera'),
  ('Ética y Valores Humanos',                         'Formación en valores y ciudadanía');

-- CONFIGURACIÓN INICIAL
INSERT INTO configuracion (clave, valor, descripcion) VALUES
  ('nota_minima_aprobatoria',   '6.0',                    'Nota mínima para aprobar una asignatura'),
  ('nombre_institucion',        'Instituto El Saber',      'Nombre oficial del colegio'),
  ('nit_institucion',           '900000000-0',             'NIT del instituto'),
  ('ciudad_institucion',        'Santa Marta, Magdalena',  'Ciudad sede del instituto'),
  ('rector_nombre',             'Por Definir',             'Nombre del rector para documentos PDF'),
  ('secretaria_nombre',         'Por Definir',             'Nombre de la secretaria para documentos PDF'),
  ('telefono_institucion',      '(+57) 300 0000000',       'Teléfono del instituto'),
  ('email_institucion',         'info@institutosaber.edu.co', 'Correo oficial del instituto'),
  ('direccion_institucion',     'Santa Marta, Magdalena, Colombia', 'Dirección del instituto'),
  ('logo_url',                  '/assets/img/logo.png',   'Ruta del logo para PDFs');
