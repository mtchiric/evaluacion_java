package com.example.demo.mapper;

import com.example.demo.dao.PrestamoDAO;
import com.example.demo.dto.PrestamoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface PrestamoMapper {
    PrestamoMapper INSTANCE = Mappers.getMapper(PrestamoMapper.class);

    PrestamoDTO PrestamoDaoToPrestamoDto(PrestamoDAO prestamoDao);

    List<PrestamoDTO> PrestamoDaosToPrestamoDtos(List<PrestamoDAO> prestamoDao);

    PrestamoDAO PrestamoDtoToPrestamoDao(PrestamoDTO prestamoDto);

}
