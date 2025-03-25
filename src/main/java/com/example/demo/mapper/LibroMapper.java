package com.example.demo.mapper;

import com.example.demo.dao.LibrosDAO;
import com.example.demo.dto.LibrosDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LibroMapper {
    LibroMapper INSTANCE = Mappers.getMapper(LibroMapper.class);

    LibrosDTO LibroDaoToLibroDto(LibrosDAO libroDao);

    List<LibrosDTO> LibroDaosToLibroDtos(List<LibrosDAO> libroDao);

    LibrosDAO LibroDtoToLibroDao(LibrosDTO librosDto);

}
