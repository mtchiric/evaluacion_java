package com.example.demo.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.example.demo.dao.LibrosDAO;
import com.example.demo.dao.PrestamoDAO;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.dto.PrestamoDTO;
import com.example.demo.mapper.PrestamoMapper;
import com.example.demo.repository.PrestamoRepository;

public class PrestamoServiceImplTest {
    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoServiceImpl prestamoServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PrestamoDAO crearPrestamoDAOMock(){
        LibrosDAO libroDAO = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.of(2021, 1, 1));

        UsuarioDAO usuarioDAO = new UsuarioDAO("Usuario1", "usuario1@gmail.com", "000000001", LocalDate.of(2020, 1, 1));

        return new PrestamoDAO(libroDAO, usuarioDAO, LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 14));
    }

    @Test
    void getPrestamos() {

        List<PrestamoDAO> prestamosMock = new ArrayList<>();

        PrestamoDAO prestamo1 = crearPrestamoDAOMock();
        PrestamoDAO prestamo2 = crearPrestamoDAOMock();

        prestamosMock.add(prestamo1);
        prestamosMock.add(prestamo2);

        when(prestamoRepository.findAll()).thenReturn(prestamosMock);

        List<PrestamoDTO> resultado = prestamoServiceImpl.getPrestamos();

        assertEquals(2, resultado.size());
        assertEquals("titulo1", resultado.get(0).libro().titulo());
    }

    @Test
    void getPrestamoId() {
        PrestamoDAO prestamo1 = crearPrestamoDAOMock();

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo1));

        PrestamoDTO resultado = prestamoServiceImpl.getPrestamoId(1L);

        assertEquals("Usuario1", resultado.usuario().nombre());
    }

    @Test
    void postPrestamo() {
        PrestamoDAO prestamo1 = crearPrestamoDAOMock();

        when(prestamoRepository.save(any())).thenReturn(prestamo1);

        PrestamoDTO prestamoDTO = PrestamoMapper.INSTANCE.PrestamoDaoToPrestamoDto(prestamo1);

        PrestamoDTO resultado = prestamoServiceImpl.postPrestamo(prestamoDTO);

        assertEquals("Usuario1", resultado.usuario().nombre());
        verify(prestamoRepository).save(any());
    }

    @Test
    void putPrestamo() {
        PrestamoDAO prestamo1 = crearPrestamoDAOMock();

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo1));
        when(prestamoRepository.save(any())).thenReturn(prestamo1);

        PrestamoDTO prestamoDTO = PrestamoMapper.INSTANCE.PrestamoDaoToPrestamoDto(prestamo1);

        PrestamoDTO resultado = prestamoServiceImpl.putPrestamo(1L, prestamoDTO);

        assertEquals("isbn1", resultado.libro().isbn());
    }

    @Test
    void patchPrestamo() {
        PrestamoDAO prestamo1 = crearPrestamoDAOMock();

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo1));
        when(prestamoRepository.save(any())).thenReturn(prestamo1);

        PrestamoDTO prestamoDTO = PrestamoMapper.INSTANCE.PrestamoDaoToPrestamoDto(prestamo1);

        PrestamoDTO resultado = prestamoServiceImpl.patchPrestamo(1L, prestamoDTO);

        assertEquals(LocalDate.of(2023, 1, 14), resultado.fechaDevolucion());
    }

    @Test
    void deletePrestamo() {
        PrestamoDAO prestamo1 = crearPrestamoDAOMock();

        when(prestamoRepository.findById(1L)).thenReturn(Optional.of(prestamo1));

        prestamoServiceImpl.deletePrestamo(1L);

        verify(prestamoRepository).deleteById(1L);
    }
}
