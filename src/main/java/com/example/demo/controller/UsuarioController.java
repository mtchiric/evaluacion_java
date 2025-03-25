package com.example.demo.controller;

import com.example.demo.dto.UsuarioDTO;
import com.example.demo.service.UsuarioService;
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
@RequestMapping("/usuarios")
public class UsuarioController {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    ResponseEntity<List<UsuarioDTO>> getUsuarios(){

        LOG.debug("UsuarioController getUsuarios...");

        List<UsuarioDTO> resultado = usuarioService.getUsuarios();

        LOG.debug("UsuarioController getUsuarios se han obtenido {} resultados", resultado.size());

        ResponseEntity<List<UsuarioDTO>> respuesta = ResponseEntity.ok(resultado);

        return respuesta;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioId(@PathVariable Long id) {
        LOG.debug("UsuarioController getUsuarioId...");

        UsuarioDTO resultado = usuarioService.getUsuarioId(id);

        LOG.debug("UsuarioController getUsuarioId se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> postUsuario(@RequestBody UsuarioDTO usuarioDto){
        LOG.debug("UsuarioController postUsuario...");

        UsuarioDTO resultado = usuarioService.postUsuario(usuarioDto);

        LOG.debug("UsuarioController postUsuario se ha creado el usuario con id {}", resultado.id());

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> putUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDto) {
        LOG.debug("UsuarioController putUsuario...");

        UsuarioDTO resultado = usuarioService.putUsuario(id, usuarioDto);

        LOG.debug("UsuarioController putUsuario se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> patchUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDto) {
        LOG.debug("UsuarioController patchusuario...");

        UsuarioDTO resultado = usuarioService.patchUsuario(id, usuarioDto);

        LOG.debug("UsuarioController patchUsuario se ha obtenido el resultado");

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        LOG.debug("UsuarioController deleteUsuario...");

        usuarioService.deleteUsuario(id);

        LOG.debug("UsuarioController deleteUsuario se ha obtenido el resultado");

        return ResponseEntity.noContent().build();
    }
}
