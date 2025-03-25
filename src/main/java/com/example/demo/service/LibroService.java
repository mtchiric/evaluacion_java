package com.example.demo.service;

import com.example.demo.dto.LibrosDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LibroService {
    List<LibrosDTO> obtenerLibros();

    LibrosDTO obtenerLibroId(Long id);

    LibrosDTO crearLibro(LibrosDTO libroDto);

    LibrosDTO putLibro(Long id, LibrosDTO libroDto);

    LibrosDTO patchLibro(Long id, LibrosDTO libroDto);

    void deleteLibro(Long id);

}
