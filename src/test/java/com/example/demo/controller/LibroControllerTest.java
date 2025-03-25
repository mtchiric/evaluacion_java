package com.example.demo.controller;

import com.example.demo.dto.LibrosDTO;
import com.example.demo.service.LibroService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibroController.class)
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibroService libroService;

    @Test
    void obtenerLibros() throws Exception {

        List<LibrosDTO> librosMock = new ArrayList<>();

        LocalDate fecha1 = LocalDate.now();
        LocalDate fecha2 = LocalDate.now();

        LibrosDTO libro1 = new LibrosDTO(1L, "Libro1", "autor1", "isbn1", fecha1);
        LibrosDTO libro2 = new LibrosDTO(1L, "Libro2", "autor2", "isbn2", fecha2);

        librosMock.add(libro1);
        librosMock.add(libro2);

        when(libroService.obtenerLibros()).thenReturn(librosMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/libros"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()", Matchers.is(librosMock.size())))
               .andExpect(jsonPath("$[0].titulo", Matchers.is("Libro1")))
               .andExpect(jsonPath("$[1].autor", Matchers.is("autor2")));

        verify(libroService, times(1)).obtenerLibros();
    }
}