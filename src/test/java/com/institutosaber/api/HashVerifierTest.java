package com.institutosaber.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashVerifierTest {

    @Test
    void verificarHashAdmin() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        // Verificar hash existente en V10
        String hashV10 = "$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        System.out.println("==============================================");
        System.out.println("Hash V10 valido para 'admin123': " + encoder.matches("admin123", hashV10));

        // Generar nuevo hash valido
        String nuevoHash = encoder.encode("admin123");
        System.out.println("Nuevo hash valido para 'admin123': " + nuevoHash);
        System.out.println("Verificacion nuevo hash: " + encoder.matches("admin123", nuevoHash));
        System.out.println("==============================================");
    }
}
