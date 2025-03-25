package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.dao.UsuarioDAO;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.PrestamoRepository;
import com.example.demo.repository.UsuarioRepository;

public class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getUsuarios() {

        List<UsuarioDAO> usuarios = new ArrayList<>();

        for(int i = 0; i < 2; i++) {
            String nombre =  "nombre" + i;
            String email =  "email" + i + "@gmail.com";
            String telefono =  "00000000" + i;
            LocalDate fechaRegistro = LocalDate.now();

            UsuarioDAO usuario = new UsuarioDAO(
                nombre,
                email,
                telefono,
                fechaRegistro
            );

            usuarios.add(usuario);
        }

        when(usuarioRepository.findAll()).thenReturn(usuarios);

        List<UsuarioDTO> resultado = usuarioServiceImpl.getUsuarios();

        assertEquals(2, resultado.size());
    }

    @Test
    void getUsuarioId() {
        UsuarioDAO usuario = new UsuarioDAO("nombre1", "email1@gmail.com", "000000001", LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioDTO resultado = usuarioServiceImpl.getUsuarioId(1L);

        assertEquals("nombre1", resultado.nombre());
    }

    @Test
    void postUsuario() {
        UsuarioDAO usuario = new UsuarioDAO("nombre1", "email1@gmail.com", "000000001", LocalDate.now());
        
        when(usuarioRepository.save(any(UsuarioDAO.class))).thenReturn(usuario);

        UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.UsuarioDaoToUsuarioDto(usuario);

        UsuarioDTO resultado = usuarioServiceImpl.postUsuario(usuarioDTO);

        assertEquals("email1@gmail.com", resultado.email());
        verify(usuarioRepository, times(1)).save(any());
    }

    @Test
    void putUsuario() {
        UsuarioDAO usuario = new UsuarioDAO("nombre1", "email1@gmail.com", "000000001", LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.UsuarioDaoToUsuarioDto(usuario);

        UsuarioDTO resultado = usuarioServiceImpl.putUsuario(1L, usuarioDTO);

        assertEquals("nombre1", resultado.nombre());
    }

    @Test
    void patchUsuario() {
        UsuarioDAO usuario = new UsuarioDAO("nombre1", "email1@gmail.com", "000000001", LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any())).thenReturn(usuario);

        UsuarioDTO usuarioDTO = UsuarioMapper.INSTANCE.UsuarioDaoToUsuarioDto(usuario);

        UsuarioDTO resultado = usuarioServiceImpl.patchUsuario(1L, usuarioDTO);

        assertEquals("nombre1", resultado.nombre());
    }

    @Test
    void deleteUsuario_sinPrestamos() {
        UsuarioDAO usuario = new UsuarioDAO("nombre1", "email1@gmail.com", "000000001", LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.existsByUsuarioId(1L)).thenReturn(false);

        usuarioServiceImpl.deleteUsuario(1L);

        verify(usuarioRepository).deleteById(1L);
    }

    @Test
    void deleteUsuario_conPrestamos() {
        UsuarioDAO usuario = new UsuarioDAO("nombre1", "email1@gmail.com", "000000001", LocalDate.now());

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(prestamoRepository.existsByUsuarioId(1L)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            usuarioServiceImpl.deleteUsuario(1L);
        });

        assertEquals("No se puede eliminar el usuario porque tiene prestamos asociados", ex.getMessage());
    }
    
}
