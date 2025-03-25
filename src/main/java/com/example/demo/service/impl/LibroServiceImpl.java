package com.example.demo.service.impl;

import com.example.demo.dao.LibrosDAO;
import com.example.demo.mapper.LibroMapper;
import com.example.demo.repository.LibroRepository;
import com.example.demo.repository.PrestamoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.dto.LibrosDTO;
import com.example.demo.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroServiceImpl implements LibroService {

    private static final Logger LOG = LoggerFactory.getLogger(LibroServiceImpl.class);

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    private static final LibroMapper libroMapper = LibroMapper.INSTANCE;

    @Override
    public List<LibrosDTO> obtenerLibros() {

        LOG.debug("LibroServiceImpl obtenerLibros...");

        List<LibrosDAO> result = libroRepository.findAll();

        List<LibrosDTO> resultado = libroMapper.LibroDaosToLibroDtos(result);

        LOG.debug("LibroServiceImpl obtenerLibros se han obtenido {} resultados", resultado.size());

        return resultado;

    }

    @Override
    public LibrosDTO obtenerLibroId(Long id){
        LOG.debug("LibroServiceImpl obtenerLibroId...");

        LibrosDAO result = libroRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        LibrosDTO resultado = libroMapper.LibroDaoToLibroDto(result);

        LOG.debug("LibroServiceImpl obtenerLibroId se ha el resultados");

        return resultado;
    }

    @Override
    public LibrosDTO crearLibro(LibrosDTO libroDto){
        LOG.debug("LibroServiceImpl crearLibro...");

        LibrosDAO libroDAO = libroMapper.LibroDtoToLibroDao(libroDto);
        libroRepository.save(libroDAO);
        LibrosDTO resultado = libroMapper.LibroDaoToLibroDto(libroDAO);

        LOG.debug("LibroServiceImpl crearLibro se ha creado el libro con id {}", resultado.id());

        return resultado;
    }

    @Override
    public LibrosDTO putLibro(Long id, LibrosDTO libroDto){
        LOG.debug("LibroServiceImpl putLibro...");

        LibrosDAO existeLibro = libroRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        existeLibro.setTitulo(libroDto.titulo());
        existeLibro.setAutor(libroDto.autor());
        existeLibro.setIsbn(libroDto.isbn());
        existeLibro.setFechaPublicacion(libroDto.fechaPublicacion());

        libroRepository.save(existeLibro);

        LibrosDTO resultado = libroMapper.LibroDaoToLibroDto(existeLibro);

        LOG.debug("LibroServiceImpl putLibro se ha actualizado el libro con id {}", resultado.id());

        return resultado;
    }

    @Override
    public LibrosDTO patchLibro(Long id, LibrosDTO libroDto){
        LOG.debug("LibroServiceImpl patchLibro...");

        LibrosDAO existeLibro = libroRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        if(libroDto.titulo() != null) existeLibro.setTitulo(libroDto.titulo());
        if(libroDto.autor() != null) existeLibro.setAutor(libroDto.autor());
        if(libroDto.isbn() != null) existeLibro.setIsbn(libroDto.isbn());
        if(libroDto.fechaPublicacion() != null) existeLibro.setFechaPublicacion(libroDto.fechaPublicacion());

        libroRepository.save(existeLibro);

        LibrosDTO resultado = libroMapper.LibroDaoToLibroDto(existeLibro);

        LOG.debug("LibroServiceImpl patchLibro se ha actualizado el libro con id {}", resultado.id());

        return resultado;
    }

    @Override
    public void deleteLibro(Long id){
        LOG.debug("LibroServiceImpl deleteLibro...");

        LibrosDAO existeLibro = libroRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Libro no encontrado con id: " + id));

        if(prestamoRepository.existsByLibroId(id)){
            throw new RuntimeException("No se puede eliminar el libro porque tiene prestamos asociados");
        }

        libroRepository.deleteById(id);

        LOG.debug("LibroServiceImpl deleteLibro se ha eliminado el libro con id {}", id);
    }
}
