package com.institutosaber.api.modules.estudiantes.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * Tabla 'personas' — compartida por estudiantes y acudientes.
 * Compatible con el esquema original (V2) extendido en V11.
 */
@Entity
@Table(name = "personas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_documento", nullable = false, length = 20)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, unique = true, length = 30)
    private String numeroDocumento;

    @Column(name = "primer_nombre", nullable = false, length = 60)
    private String primerNombre;

    @Column(name = "segundo_nombre", length = 60)
    private String segundoNombre;

    @Column(name = "primer_apellido", nullable = false, length = 60)
    private String primerApellido;

    @Column(name = "segundo_apellido", length = 60)
    private String segundoApellido;

    /** Columna legacy — mantenida para retrocompatibilidad */
    @Column(name = "nombres", length = 150, insertable = false, updatable = false)
    private String nombres;

    /** Columna legacy — mantenida para retrocompatibilidad */
    @Column(name = "apellidos", length = 150, insertable = false, updatable = false)
    private String apellidos;

    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @Column(name = "genero", nullable = false, length = 20)
    private String genero; // MASCULINO, FEMENINO, OTRO

    @Column(name = "municipio_nacimiento", nullable = false, length = 100)
    private String municipioNacimiento;

    @Column(name = "departamento_nacimiento", nullable = false, length = 100)
    private String departamentoNacimiento;

    @Column(name = "direccion", length = 200)
    private String direccion;

    @Column(name = "municipio_residencia", length = 100)
    private String municipioResidencia;

    @Column(name = "telefono", length = 30)
    private String telefono;

    @Column(name = "email", length = 150)
    private String email;

    /** Nombre completo calculado */
    public String getNombreCompleto() {
        StringBuilder sb = new StringBuilder(primerNombre);
        if (segundoNombre != null && !segundoNombre.isBlank())
            sb.append(" ").append(segundoNombre);
        sb.append(" ").append(primerApellido);
        if (segundoApellido != null && !segundoApellido.isBlank())
            sb.append(" ").append(segundoApellido);
        return sb.toString();
    }
}
