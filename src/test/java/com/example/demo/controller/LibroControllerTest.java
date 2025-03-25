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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    void obtenerLibroPorId() throws Exception {

        LocalDate fecha1 = LocalDate.now();

        LibrosDTO libro1 = new LibrosDTO(1L, "Libro1", "autor1", "isbn1", fecha1);

        when(libroService.obtenerLibroId(1L)).thenReturn(libro1);

        mockMvc.perform(MockMvcRequestBuilders.get("/libros/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.titulo", Matchers.is("Libro1")))
               .andExpect(jsonPath("$.autor", Matchers.is("autor1")));

        verify(libroService, times(1)).obtenerLibroId(1L);
    }

    @Test
    void crearLibro() throws Exception {
        LocalDate fecha1 = LocalDate.of(2021, 10, 10);

        LibrosDTO libro1 = new LibrosDTO(1L, "Libro1", "autor1", "isbn1", fecha1);

        when(libroService.crearLibro(any(LibrosDTO.class))).thenReturn(libro1);

        String json = "{\"id\":1,\"titulo\":\"Libro1\",\"autor\":\"autor1\",\"isbn\":\"isbn1\",\"fecha\":\"2021-10-10\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/libros")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", Matchers.is("Libro1")))
                .andExpect(jsonPath("$.autor", Matchers.is("autor1")));

        verify(libroService, times(1)).crearLibro(any(LibrosDTO.class));
    }

    @Test
    void putLibro() throws Exception {
        LocalDate fecha1 = LocalDate.of(2021, 10, 10);

        LibrosDTO libro1 = new LibrosDTO(1L, "Libro1", "autor1", "isbn1", fecha1);

        when(libroService.putLibro(any(Long.class), any(LibrosDTO.class))).thenReturn(libro1);

        String json = "{\"id\":1,\"titulo\":\"Libro1\",\"autor\":\"autor1\",\"isbn\":\"isbn1\",\"fecha\":\"2021-10-10\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/libros/1")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", Matchers.is("Libro1")))
                .andExpect(jsonPath("$.autor", Matchers.is("autor1")));

        verify(libroService, times(1)).putLibro(any(Long.class), any(LibrosDTO.class));
    }

    @Test
    void patchLibro() throws Exception {
        LocalDate fecha1 = LocalDate.of(2021, 10, 10);

        LibrosDTO libro1 = new LibrosDTO(1L, "Libro1", "autor1", "isbn1", fecha1);

        when(libroService.patchLibro(any(Long.class), any(LibrosDTO.class))).thenReturn(libro1);

        String json = "{\"id\":1,\"titulo\":\"Libro1\",\"autor\":\"autor1\",\"isbn\":\"isbn1\",\"fecha\":\"2021-10-10\"}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/libros/1")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo", Matchers.is("Libro1")))
                .andExpect(jsonPath("$.autor", Matchers.is("autor1")));

        verify(libroService, times(1)).patchLibro(any(Long.class), any(LibrosDTO.class));
    }

    @Test
    void deleteLibro() throws Exception {
            
            mockMvc.perform(MockMvcRequestBuilders.delete("/libros/1"))
                .andExpect(status().isNoContent());
    
            verify(libroService, times(1)).deleteLibro(1L);
    }
}