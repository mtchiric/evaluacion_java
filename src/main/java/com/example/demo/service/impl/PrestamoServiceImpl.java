package com.example.demo.service.impl;

import com.example.demo.dao.PrestamoDAO;
import com.example.demo.mapper.PrestamoMapper;
import com.example.demo.mapper.LibroMapper;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.PrestamoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.dto.PrestamoDTO;
import com.example.demo.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private static final Logger LOG = LoggerFactory.getLogger(PrestamoServiceImpl.class);

    @Autowired
    private PrestamoRepository prestamoRepository;

    private static final PrestamoMapper prestamoMapper = PrestamoMapper.INSTANCE;
    private static final LibroMapper libroMapper = LibroMapper.INSTANCE;
    private static final UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;


    @Override
    public List<PrestamoDTO> getPrestamos() {

        LOG.debug("PrestamoServiceImpl getPrestamos...");

        List<PrestamoDAO> result = prestamoRepository.findAll();

        List<PrestamoDTO> resultado = prestamoMapper.PrestamoDaosToPrestamoDtos(result);

        LOG.debug("PrestamoServiceImpl getPrestamos se han obtenido {} resultados", resultado.size());

        return resultado;

    }

    @Override
    public PrestamoDTO getPrestamoId(Long id){
        LOG.debug("PrestamoServiceImpl getPrestamoId...");

        PrestamoDAO result = prestamoRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));

        PrestamoDTO resultado = prestamoMapper.PrestamoDaoToPrestamoDto(result);

        LOG.debug("PrestamoServiceImpl getPrestamoId se ha el resultados");

        return resultado;
    }

    @Override
    public PrestamoDTO postPrestamo(PrestamoDTO prestamoDto){
        LOG.debug("PrestamoServiceImpl postPrestamo...");

        PrestamoDAO prestamoDAO = prestamoMapper.PrestamoDtoToPrestamoDao(prestamoDto);
        prestamoRepository.save(prestamoDAO);
        PrestamoDTO resultado = prestamoMapper.PrestamoDaoToPrestamoDto(prestamoDAO);

        LOG.debug("PrestamoServiceImpl postPrestamo se ha creado el prestamo con id {}", resultado.id());

        return resultado;
    }

    @Override
    public PrestamoDTO putPrestamo(Long id, PrestamoDTO prestamoDto){
        LOG.debug("PrestamoServiceImpl putPrestamo...");

        PrestamoDAO existePrestamo = prestamoRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));

        existePrestamo.setLibro(libroMapper.LibroDtoToLibroDao(prestamoDto.libro()));
        existePrestamo.setUsuario(usuarioMapper.UsuarioDtoToUsuarioDao(prestamoDto.usuario()));
        existePrestamo.setFechaPrestamo(prestamoDto.fechaPrestamo());
        existePrestamo.setFechaDevolucion(prestamoDto.fechaDevolucion());

        prestamoRepository.save(existePrestamo);

        PrestamoDTO resultado = prestamoMapper.PrestamoDaoToPrestamoDto(existePrestamo);

        LOG.debug("PrestamoServiceImpl putPrestamo se ha actualizado el prestamo con id {}", resultado.id());

        return resultado;
    }

    @Override
    public PrestamoDTO patchPrestamo(Long id, PrestamoDTO prestamoDto){
        LOG.debug("PrestamoServiceImpl patchPrestamo...");

        PrestamoDAO existePrestamo = prestamoRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));

        if(prestamoDto.libro() != null) existePrestamo.setLibro(libroMapper.LibroDtoToLibroDao(prestamoDto.libro()));
        if(prestamoDto.usuario() != null) existePrestamo.setUsuario(usuarioMapper.UsuarioDtoToUsuarioDao(prestamoDto.usuario()));
        if(prestamoDto.fechaPrestamo() != null) existePrestamo.setFechaPrestamo(prestamoDto.fechaPrestamo());
        if(prestamoDto.fechaDevolucion() != null) existePrestamo.setFechaDevolucion(prestamoDto.fechaDevolucion());

        prestamoRepository.save(existePrestamo);

        PrestamoDTO resultado = prestamoMapper.PrestamoDaoToPrestamoDto(existePrestamo);

        LOG.debug("PrestamoServiceImpl patchPrestamo se ha actualizado el prestamo con id {}", resultado.id());

        return resultado;
    }

    @Override
    public void deletePrestamo(Long id){
        LOG.debug("PrestamoServiceImpl deletePrestamo...");

        PrestamoDAO existePrestamo = prestamoRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado con id: " + id));

        prestamoRepository.deleteById(id);

        LOG.debug("PrestamoServiceImpl deletePrestamo se ha eliminado el libro con id {}", id);
    }
}
