package com.example.demo.dataloader;

import com.example.demo.dao.LibrosDAO;
import com.example.demo.repository.LibroRepository;
import com.example.demo.dao.UsuarioDAO;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.dao.PrestamoDAO;
import com.example.demo.repository.PrestamoRepository;
import jakarta.persistence.Column;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

@Component
public class DataLoader implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(DataLoader.class);

    private final LibroRepository libroRepository;

    private final UsuarioRepository usuarioRepository;

    private final PrestamoRepository prestamoRepository;


    public DataLoader(LibroRepository libroRepository, UsuarioRepository usuarioRepository, PrestamoRepository prestamoRepository) {
        this.libroRepository = libroRepository;
        this.usuarioRepository = usuarioRepository;
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        LOG.info("Cargando datos...");

        List<LibrosDAO> libros = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            libros.add(new LibrosDAO(
                "titulo" + i,
                "autor" + i,
                "isbn" + i,
                LocalDate.now()
            ));
        }

        libroRepository.saveAll(libros);



        List<UsuarioDAO> usuarios = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            usuarios.add(new UsuarioDAO(
                "nombre" + i,
                "nombre" + i + "@gmail.com",
                "00000000" + i,
                LocalDate.now()
            ));
        }

        usuarioRepository.saveAll(usuarios);



        List<PrestamoDAO> prestamos = new ArrayList<>();

        for(int i = 0; i < 3; i++) {
            prestamos.add(new PrestamoDAO(
                libros.get(i),
                usuarios.get(i),
                LocalDate.now(),
                LocalDate.now().plusDays(14)
            ));
        }

        prestamoRepository.saveAll(prestamos);

    }
}
