package com.example.demo.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.example.demo.dto.LibrosDTO;
import com.example.demo.dto.PrestamoDTO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.service.PrestamoService;

@WebMvcTest(PrestamoController.class)
public class PrestamoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    private PrestamoDTO crearPrestamoMock(){
        LibrosDTO libro = new LibrosDTO(1L, "titulo1", "autor1", "isbn1", LocalDate.now());
        UsuarioDTO usuario = new UsuarioDTO(1L, "Usuario1", "email1@gmail.com", "000000001", LocalDate.now());
        return new PrestamoDTO(1L, libro, usuario, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 14));
    }

    @Test
    void getPrestamos() throws Exception {
        List<PrestamoDTO> prestamosMock = new ArrayList<>();

        PrestamoDTO prestamo1 = crearPrestamoMock();
        PrestamoDTO prestamo2 = crearPrestamoMock();

        prestamosMock.add(prestamo1);
        prestamosMock.add(prestamo2);

        when(prestamoService.getPrestamos()).thenReturn(prestamosMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/prestamos"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()", Matchers.is(prestamosMock.size())))
               .andExpect(jsonPath("$[0].libro.titulo", Matchers.is("titulo1")))
               .andExpect(jsonPath("$[1].usuario.email", Matchers.is("email1@gmail.com")));

        verify(prestamoService, times(1)).getPrestamos();
    }

    @Test
    void getPrestamoId() throws Exception {
        PrestamoDTO prestamo1 = crearPrestamoMock();

        when(prestamoService.getPrestamoId(1L)).thenReturn(prestamo1);

        mockMvc.perform(MockMvcRequestBuilders.get("/prestamos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.libro.titulo", Matchers.is("titulo1")));

        verify(prestamoService, times(1)).getPrestamoId(1L);
    }

    @Test
    void postPrestamo() throws Exception {
        PrestamoDTO prestamo1 = crearPrestamoMock();

        when(prestamoService.postPrestamo(any(PrestamoDTO.class))).thenReturn(prestamo1);

        String json = """
            {
              "libro": {
                "id": 1,
                "titulo": "titulo1",
                "autor": "autor1",
                "isbn": "isbn1",
                "fechaPublicacion": "2021-01-01"
              },
              "usuario": {
                "id": 1,
                "nombre": "Usuario1",
                "email": "email1@gmail.com",
                "telefono": "000000001",
                "fechaRegistro": "2020-01-01"
              },
              "fechaPrestamo": "2023-01-10",
              "fechaDevolucion": "2023-01-24"
            }
            """;

            mockMvc.perform(MockMvcRequestBuilders.post("/prestamos")
                    .contentType("application/json")
                    .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.libro.titulo", Matchers.is("titulo1")));

        verify(prestamoService, times(1)).postPrestamo(any(PrestamoDTO.class));
    }

    @Test
    void putPrestamo() throws Exception {
        PrestamoDTO p = crearPrestamoMock();

        when(prestamoService.putPrestamo(eq(1L), any(PrestamoDTO.class))).thenReturn(p);

        String json = """
            {
              "libro": {
                "id": 1,
                "titulo": "titulo1",
                "autor": "autor1",
                "isbn": "isbn1",
                "fechaPublicacion": "2021-01-01"
              },
              "usuario": {
                "id": 1,
                "nombre": "Usuario1",
                "email": "email1@gmail.com",
                "telefono": "000000001",
                "fechaRegistro": "2020-01-01"
              },
              "fechaPrestamo": "2023-01-10",
              "fechaDevolucion": "2023-01-24"
            }
            """;

            mockMvc.perform(MockMvcRequestBuilders.put("/prestamos/1")
                    .contentType("application/json")
                    .content(json))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.libro.titulo", Matchers.is("titulo1")));

        verify(prestamoService, times(1)).putPrestamo(eq(1L), any(PrestamoDTO.class));
    }

    @Test
    void patchPrestamo() throws Exception {
        PrestamoDTO p = crearPrestamoMock();

        when(prestamoService.patchPrestamo(eq(1L), any(PrestamoDTO.class))).thenReturn(p);

        String json = """
            {
              "fechaDevolucion": "2023-01-24"
            }
            """;

            mockMvc.perform(MockMvcRequestBuilders.patch("/prestamos/1")
                    .contentType("application/json")
                    .content(json))
                    .andExpect(status().isOk());

        verify(prestamoService, times(1)).patchPrestamo(eq(1L), any(PrestamoDTO.class));
    }

    @Test
    void deletePrestamo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/prestamos/1"))
                .andExpect(status().isNoContent());

        verify(prestamoService, times(1)).deletePrestamo(1L);
    }
}
