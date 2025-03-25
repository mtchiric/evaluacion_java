package com.example.demo.dto;

import java.time.LocalDate;

public record PrestamoDTO(
    Long id,
    LibrosDTO libro,
    UsuarioDTO usuario,
    LocalDate fechaPrestamo,
    LocalDate fechaDevolucion
){}
