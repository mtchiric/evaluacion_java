package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PrestamoDTO {

    private Long id;

    private LibrosDTO libro;

    private UsuarioDTO usuario;

    private LocalDate fechaPrestamo;

    private LocalDate fechaDevolucion;
}
