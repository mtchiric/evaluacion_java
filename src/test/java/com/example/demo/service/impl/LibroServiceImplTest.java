package com.example.demo.service.impl;

import com.example.demo.dao.LibrosDAO;
import com.example.demo.dto.LibrosDTO;
import com.example.demo.repository.LibroRepository;
import jakarta.annotation.security.RunAs;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroServiceImpl libroServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void obtenerLibros() {

        List<LibrosDAO> libros = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            String titulo =  "titulo" + i;
            String autor =  "autor" + i;
            String isbn =  "isbn" + i;
            LocalDate fechaPublicacion = LocalDate.now();

            LibrosDAO libro = new LibrosDAO(
                    titulo,
                    autor,
                    isbn,
                    fechaPublicacion
            );

            libros.add(libro);
        }

        when(libroRepository.findAll()).thenReturn(libros);

        List<LibrosDTO> resultado = libroServiceImpl.obtenerLibros();

        assertEquals(2, resultado.size());
    }
}