package com.institutosaber.api;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateHashTest {

    @Test
    void generarHash() throws IOException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        String hash = encoder.encode("admin123");

        // Verificar el hash del V10/V12 actual
        String hashViejo = "$2a$12$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy";
        boolean viejoOk = encoder.matches("admin123", hashViejo);
        boolean nuevoOk = encoder.matches("admin123", hash);

        try (FileWriter fw = new FileWriter("target/admin_hash.txt")) {
            fw.write("NUEVO_HASH=" + hash + "\n");
            fw.write("NUEVO_OK=" + nuevoOk + "\n");
            fw.write("VIEJO_OK=" + viejoOk + "\n");
        }
    }
}
