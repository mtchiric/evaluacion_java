package com.example.demo.controller;

import com.example.demo.dto.LibrosDTO;
import com.example.demo.service.LibroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private static final Logger LOG = LoggerFactory.getLogger(LibroController.class);

    @Autowired
    private LibroService libroService;

    @GetMapping
    ResponseEntity<List<LibrosDTO>> obtenerLibros(){

        LOG.debug("LibroController obtenerLibros...");

        List<LibrosDTO> resultado = libroService.obtenerLibros();

        LOG.debug("LibroController obtenerLibros se han obtenido {} resultados", resultado.size());

        ResponseEntity<List<LibrosDTO>> respuesta = ResponseEntity.ok(resultado);

        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibrosDTO> obtenerLibroPorId(@PathVariable Long id) {
        LOG.debug("LibroController obtenerLibroId...");

        LibrosDTO resultado = libroService.obtenerLibroId(id);

        LOG.debug("LibroController obtenerLibroId se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<LibrosDTO> crearLibro(@RequestBody LibrosDTO libroDto){
        LOG.debug("LibroController crearLibro...");

        LibrosDTO resultado = libroService.crearLibro(libroDto);

        LOG.debug("LibroController crearLibro se ha creado el libro con id {}", resultado.id());

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibrosDTO> putLibro(@PathVariable Long id, @RequestBody LibrosDTO libroDto) {
        LOG.debug("LibroController putLibro...");

        LibrosDTO resultado = libroService.putLibro(id, libroDto);

        LOG.debug("LibroController putLibro se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<LibrosDTO> patchLibro(@PathVariable Long id, @RequestBody LibrosDTO libroDto) {
        LOG.debug("LibroController patchLibro...");

        LibrosDTO resultado = libroService.patchLibro(id, libroDto);

        LOG.debug("LibroController patchLibro se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        LOG.debug("LibroController deleteLibro...");

        libroService.deleteLibro(id);

        LOG.debug("LibroController deleteLibro se ha obtenido el resultado");

        return ResponseEntity.noContent().build();
    }
}
