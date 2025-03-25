package com.example.demo.service;

import com.example.demo.dto.UsuarioDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UsuarioService {
    List<UsuarioDTO> getUsuarios();

    UsuarioDTO getUsuarioId(Long id);

    UsuarioDTO postUsuario(UsuarioDTO UsuarioDto);

    UsuarioDTO putUsuario(Long id, UsuarioDTO UsuarioDto);

    UsuarioDTO patchUsuario(Long id, UsuarioDTO UsuarioDto);

    void deleteUsuario(Long id);

}
