package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.service.UsuarioService;
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

@WebMvcTest(UsuarioController.class)
class UsuarioControllerTest { 

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    void getUsuarios() throws Exception {

        List<UsuarioDTO> usuariosMock = new ArrayList<>();

        LocalDate fecha1 = LocalDate.now();
        LocalDate fecha2 = LocalDate.now();

        UsuarioDTO usuario1 = new UsuarioDTO(1L, "Usuario1", "usuario1@gmail.com", "000000001", fecha1);
        UsuarioDTO usuario2 = new UsuarioDTO(1L, "Usuario2", "usuario2@gmail.com", "000000002", fecha2);

        usuariosMock.add(usuario1);
        usuariosMock.add(usuario2);

        when(usuarioService.getUsuarios()).thenReturn(usuariosMock);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.size()", Matchers.is(usuariosMock.size())))
               .andExpect(jsonPath("$[0].nombre", Matchers.is("Usuario1")))
               .andExpect(jsonPath("$[1].email", Matchers.is("usuario2@gmail.com")));

        verify(usuarioService, times(1)).getUsuarios();
    }

    @Test
    void getUsuarioId() throws Exception {
        LocalDate fecha1 = LocalDate.now();

        UsuarioDTO usuario1 = new UsuarioDTO(1L, "Usuario1", "usuario1@gmail.com", "000000001", fecha1);

        when(usuarioService.getUsuarioId(1L)).thenReturn(usuario1);

        mockMvc.perform(MockMvcRequestBuilders.get("/usuarios/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.nombre", Matchers.is("Usuario1")))
               .andExpect(jsonPath("$.email", Matchers.is("usuario1@gmail.com")));

        verify(usuarioService, times(1)).getUsuarioId(1L);
    }

    @Test
    void postUsuario() throws Exception {
        LocalDate fecha1 = LocalDate.of(2021, 10, 10);

        UsuarioDTO usuario1 = new UsuarioDTO(1L, "Usuario1", "usuario1@gmail.com", "000000001", fecha1);

        when(usuarioService.postUsuario(any(UsuarioDTO.class))).thenReturn(usuario1);

        String json = "{\"id\":1,\"nombre\":\"Usuario1\",\"email\":\"usuario1@gmail.com\",\"telefono\":\"00000001\",\"fecha\":\"2021-10-10\"}";

        mockMvc.perform(MockMvcRequestBuilders.post("/usuarios")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", Matchers.is("Usuario1")))
                .andExpect(jsonPath("$.email", Matchers.is("usuario1@gmail.com")));

        verify(usuarioService, times(1)).postUsuario(any(UsuarioDTO.class));
    }

    @Test
    void putUsuario() throws Exception {
        LocalDate fecha1 = LocalDate.of(2021, 10, 10);

        UsuarioDTO usuario1 = new UsuarioDTO(1L, "Usuario1", "usuario1@gmail.com", "000000001", fecha1);

        when(usuarioService.putUsuario(any(Long.class), any(UsuarioDTO.class))).thenReturn(usuario1);

        String json = "{\"id\":1,\"nombre\":\"Usuario1\",\"email\":\"usuario1@gmail.com\",\"telefono\":\"00000001\",\"fecha\":\"2021-10-10\"}";

        mockMvc.perform(MockMvcRequestBuilders.put("/usuarios/1")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", Matchers.is("Usuario1")))
                .andExpect(jsonPath("$.email", Matchers.is("usuario1@gmail.com")));

        verify(usuarioService, times(1)).putUsuario(any(Long.class), any(UsuarioDTO.class));
    }

    @Test
    void patchUsuario() throws Exception {
        LocalDate fecha1 = LocalDate.of(2021, 10, 10);

        UsuarioDTO usuario1 = new UsuarioDTO(1L, "Usuario1", "usuario1@gmail.com", "000000001", fecha1);

        when(usuarioService.patchUsuario(any(Long.class), any(UsuarioDTO.class))).thenReturn(usuario1);

        String json = "{\"id\":1,\"nombre\":\"Usuario1\",\"email\":\"usuario1@gmail.com\",\"telefono\":\"00000001\",\"fecha\":\"2021-10-10\"}";

        mockMvc.perform(MockMvcRequestBuilders.patch("/usuarios/1")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", Matchers.is("Usuario1")))
                .andExpect(jsonPath("$.email", Matchers.is("usuario1@gmail.com")));

        verify(usuarioService, times(1)).patchUsuario(any(Long.class), any(UsuarioDTO.class));
    }

    @Test
    void deleteUsuario() throws Exception {
            
            mockMvc.perform(MockMvcRequestBuilders.delete("/usuarios/1"))
                .andExpect(status().isNoContent());
    
            verify(usuarioService, times(1)).deleteUsuario(1L);
    }
}