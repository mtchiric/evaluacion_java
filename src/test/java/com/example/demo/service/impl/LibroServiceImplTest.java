package com.example.demo.service.impl;

import com.example.demo.dao.LibrosDAO;
import com.example.demo.dto.LibrosDTO;
import com.example.demo.mapper.LibroMapper;
import com.example.demo.repository.LibroRepository;
import com.example.demo.repository.PrestamoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LibroServiceImplTest {

    @Mock
    private LibroRepository libroRepository;

    @Mock
    private PrestamoRepository prestamoRepository;

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

    @Test
    void obtenerLibroId() {

        LibrosDAO libro = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.now());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));

        LibrosDTO resultado = libroServiceImpl.obtenerLibroId(1L);

        assertEquals("autor1", resultado.autor());
    }

    @Test
    void crearLibro() {
        LibrosDAO libro = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.now());
        
        when(libroRepository.save(any(LibrosDAO.class))).thenReturn(libro);

        LibrosDTO libroDTO = LibroMapper.INSTANCE.LibroDaoToLibroDto(libro);

        LibrosDTO resultado = libroServiceImpl.crearLibro(libroDTO);

        assertEquals("titulo1", resultado.titulo());
        verify(libroRepository, times(1)).save(any());
    }

    @Test
    void putLibro() {
        LibrosDAO libro = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.now());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any())).thenReturn(libro);

        LibrosDTO libroDTO = LibroMapper.INSTANCE.LibroDaoToLibroDto(libro);

        LibrosDTO resultado = libroServiceImpl.putLibro(1L, libroDTO);

        assertEquals("titulo1", resultado.titulo());
    }

    @Test
    void patchLibro() {
        LibrosDAO libro = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.now());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(libroRepository.save(any())).thenReturn(libro);

        LibrosDTO libroDTO = LibroMapper.INSTANCE.LibroDaoToLibroDto(libro);

        LibrosDTO resultado = libroServiceImpl.patchLibro(1L, libroDTO);

        assertEquals("isbn1", resultado.isbn());
    }

    @Test
    void deleteLibro_sinPrestamos() {
        LibrosDAO libro = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.now());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByLibroId(1L)).thenReturn(false);

        libroServiceImpl.deleteLibro(1L);

        verify(libroRepository).deleteById(1L);
    }

    @Test
    void deleteLibro_conPrestamos() {
        LibrosDAO libro = new LibrosDAO("titulo1", "autor1", "isbn1", LocalDate.now());

        when(libroRepository.findById(1L)).thenReturn(Optional.of(libro));
        when(prestamoRepository.existsByLibroId(1L)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            libroServiceImpl.deleteLibro(1L);
        });

        assertEquals("No se puede eliminar el libro porque tiene prestamos asociados", ex.getMessage());
    }

}