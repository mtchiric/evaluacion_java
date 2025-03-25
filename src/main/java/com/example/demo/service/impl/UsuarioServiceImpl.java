package com.example.demo.service.impl;

import com.example.demo.dao.UsuarioDAO;
import com.example.demo.mapper.UsuarioMapper;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.PrestamoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOG = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    private static final UsuarioMapper usuarioMapper = UsuarioMapper.INSTANCE;

    @Override
    public List<UsuarioDTO> getUsuarios() {

        LOG.debug("UsuarioServiceImpl getUsuarios...");

        List<UsuarioDAO> result = usuarioRepository.findAll();

        List<UsuarioDTO> resultado = usuarioMapper.UsuarioDaosToUsuarioDtos(result);

        LOG.debug("UsuarioServiceImpl getUsuarios se han obtenido {} resultados", resultado.size());

        return resultado;

    }

    @Override
    public UsuarioDTO getUsuarioId(Long id){
        LOG.debug("UsuarioServiceImpl getUsuarioId...");

        UsuarioDAO result = usuarioRepository.findById(id)
                                        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        UsuarioDTO resultado = usuarioMapper.UsuarioDaoToUsuarioDto(result);

        LOG.debug("UsuarioServiceImpl getUsuarioId se ha el resultados");

        return resultado;
    }

    @Override
    public UsuarioDTO postUsuario(UsuarioDTO usuarioDto){
        LOG.debug("UsuarioServiceImpl postUsuario...");

        UsuarioDAO usuarioDAO = usuarioMapper.UsuarioDtoToUsuarioDao(usuarioDto);
        usuarioRepository.save(usuarioDAO);
        UsuarioDTO resultado = usuarioMapper.UsuarioDaoToUsuarioDto(usuarioDAO);

        LOG.debug("UsuarioServiceImpl postUsuario se ha creado el usuario con id {}", resultado.getId());

        return resultado;
    }

    @Override
    public UsuarioDTO putUsuario(Long id, UsuarioDTO usuarioDto){
        LOG.debug("UsuarioServiceImpl putUsuario...");

        UsuarioDAO existeUsuario = usuarioRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        existeUsuario.setNombre(usuarioDto.getNombre());
        existeUsuario.setEmail(usuarioDto.getEmail());
        existeUsuario.setTelefono(usuarioDto.getTelefono());
        existeUsuario.setFechaRegistro(usuarioDto.getFechaRegistro());

        usuarioRepository.save(existeUsuario);

        UsuarioDTO resultado = usuarioMapper.UsuarioDaoToUsuarioDto(existeUsuario);

        LOG.debug("UsuarioServiceImpl putUsuario se ha actualizado el usuario con id {}", resultado.getId());

        return resultado;
    }

    @Override
    public UsuarioDTO patchUsuario(Long id, UsuarioDTO usuarioDto){
        LOG.debug("UsuarioServiceImpl patchUsuario...");

        UsuarioDAO existeUsuario = usuarioRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if(usuarioDto.getNombre() != null) existeUsuario.setNombre(usuarioDto.getNombre());
        if(usuarioDto.getEmail() != null) existeUsuario.setEmail(usuarioDto.getEmail());
        if(usuarioDto.getTelefono() != null) existeUsuario.setTelefono(usuarioDto.getTelefono());
        if(usuarioDto.getFechaRegistro() != null) existeUsuario.setFechaRegistro(usuarioDto.getFechaRegistro());

        usuarioRepository.save(existeUsuario);

        UsuarioDTO resultado = usuarioMapper.UsuarioDaoToUsuarioDto(existeUsuario);

        LOG.debug("UsuarioServiceImpl patchUsuario se ha actualizado el usuario con id {}", resultado.getId());

        return resultado;
    }

    @Override
    public void deleteUsuario(Long id){
        LOG.debug("UsuarioServiceImpl deleteUsuario...");

        UsuarioDAO existeUsuario = usuarioRepository.findById(id)
                                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        if(prestamoRepository.existsByUsuarioId(id)){
            throw new RuntimeException("No se puede eliminar el usuario porque tiene prestamos asociados");
        }

        usuarioRepository.deleteById(id);

        LOG.debug("UsuarioServiceImpl deleteUsuario se ha eliminado el usuario con id {}", id);
    }
}
