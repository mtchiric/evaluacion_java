package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class LibrosDTO {

    private Long id;

    private String titulo;

    private String autor;

    private String isbn;

    private LocalDate fechaPublicacion;
}
