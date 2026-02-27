package com.institutosaber.api.modules.academico.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "niveles_educativos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NivelEducativo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(nullable = false)
    private Integer orden;
}
