package com.example.demo.dto;

import java.time.LocalDate;

public record LibrosDTO(
    Long id,
    String titulo,
    String autor,
    String isbn,
    LocalDate fechaPublicacion
){}
