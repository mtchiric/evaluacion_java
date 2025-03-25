package com.example.demo.controller;

import com.example.demo.dto.PrestamoDTO;
import com.example.demo.service.PrestamoService;
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
@RequestMapping("/prestamos")
public class PrestamoController {

    private static final Logger LOG = LoggerFactory.getLogger(PrestamoController.class);

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    ResponseEntity<List<PrestamoDTO>> getPrestamos(){

        LOG.debug("PrestamoController getPrestamos...");

        List<PrestamoDTO> resultado = prestamoService.getPrestamos();

        LOG.debug("PrestamoController getPrestamos se han obtenido {} resultados", resultado.size());

        ResponseEntity<List<PrestamoDTO>> respuesta = ResponseEntity.ok(resultado);

        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> getPrestamoId(@PathVariable Long id) {
        LOG.debug("PrestamoController getPrestamoId...");

        PrestamoDTO resultado = prestamoService.getPrestamoId(id);

        LOG.debug("PrestamoController getPrestamoId se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<PrestamoDTO> postPrestamo(@RequestBody PrestamoDTO prestamoDto){
        LOG.debug("PrestamoController postPrestamo...");

        PrestamoDTO resultado = prestamoService.postPrestamo(prestamoDto);

        LOG.debug("PrestamoController postPrestamo se ha creado el prestamo con id {}", resultado.id());

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrestamoDTO> putPrestamo(@PathVariable Long id, @RequestBody PrestamoDTO prestamoDto) {
        LOG.debug("PrestamoController putPrestamo...");

        PrestamoDTO resultado = prestamoService.putPrestamo(id, prestamoDto);

        LOG.debug("PrestamoController putPrestamo se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PrestamoDTO> patchPrestamo(@PathVariable Long id, @RequestBody PrestamoDTO prestamoDto) {
        LOG.debug("PrestamoController patchPrestamo...");

        PrestamoDTO resultado = prestamoService.patchPrestamo(id, prestamoDto);

        LOG.debug("PrestamoController patchPrestamo se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrestamo(@PathVariable Long id) {
        LOG.debug("PrestamoController deletePrestamo...");

        prestamoService.deletePrestamo(id);

        LOG.debug("PrestamoController deletePrestamo se ha obtenido el resultado");

        return ResponseEntity.noContent().build();
    }
}
