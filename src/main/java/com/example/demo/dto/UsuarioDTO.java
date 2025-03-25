package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class UsuarioDTO {

    private Long id;

    private String nombre;

    private String email;

    private String telefono;

    private LocalDate fechaRegistro;
}
