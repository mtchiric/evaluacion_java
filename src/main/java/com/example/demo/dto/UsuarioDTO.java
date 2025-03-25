package com.example.demo.dto;

import java.time.LocalDate;

public record UsuarioDTO(
    Long id,
    String nombre,
    String email,
    String telefono,
    LocalDate fechaRegistro
){}
