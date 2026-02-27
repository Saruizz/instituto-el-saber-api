package com.institutosaber.api.config;

import com.institutosaber.api.modules.auth.entity.Rol;
import com.institutosaber.api.modules.auth.entity.Usuario;
import com.institutosaber.api.modules.auth.repository.RolRepository;
import com.institutosaber.api.modules.auth.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * Inicializa el usuario admin al arrancar la aplicación.
 * Solo actúa si el usuario no existe o si su contraseña no coincide con la
 * contraseña por defecto. Esto garantiza que el hash siempre sea correcto
 * independientemente de migraciones Flyway previas con hashes incorrectos.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String ADMIN_EMAIL = "admin@institutosaber.edu.co";
    private static final String ADMIN_ROL = "ROLE_ADMIN";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        // Asegurar que el rol existe
        Rol rol = rolRepository.findByNombre(ADMIN_ROL).orElseGet(() -> {
            log.info("Creando rol {}", ADMIN_ROL);
            Rol r = new Rol();
            r.setNombre(ADMIN_ROL);
            r.setDescripcion("Administrador del sistema. Acceso total.");
            return rolRepository.save(r);
        });

        usuarioRepository.findByUsername(ADMIN_USERNAME).ifPresentOrElse(
                usuario -> {
                    // Verificar si la contraseña es correcta; si no, actualizarla
                    if (!passwordEncoder.matches(ADMIN_PASSWORD, usuario.getPasswordHash())) {
                        log.warn("Hash de admin incorrecto. Regenerando contraseña...");
                        usuario.setPasswordHash(passwordEncoder.encode(ADMIN_PASSWORD));
                        usuario.setActivo(true);
                        if (usuario.getRoles().stream().noneMatch(r -> r.getNombre().equals(ADMIN_ROL))) {
                            usuario.getRoles().add(rol);
                        }
                        usuarioRepository.save(usuario);
                        log.info("Contraseña del admin actualizada correctamente.");
                    } else {
                        log.debug("Usuario admin OK, contraseña verificada.");
                    }
                },
                () -> {
                    log.info("Creando usuario admin por defecto...");
                    Usuario admin = Usuario.builder()
                            .username(ADMIN_USERNAME)
                            .passwordHash(passwordEncoder.encode(ADMIN_PASSWORD))
                            .email(ADMIN_EMAIL)
                            .activo(true)
                            .roles(Set.of(rol))
                            .build();
                    usuarioRepository.save(admin);
                    log.info("Usuario admin creado correctamente.");
                });
    }
}
