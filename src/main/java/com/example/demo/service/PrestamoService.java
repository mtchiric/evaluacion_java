package com.example.demo.service;

import com.example.demo.dto.PrestamoDTO;

import java.util.List;

public interface PrestamoService {
    List<PrestamoDTO> getPrestamos();

    PrestamoDTO getPrestamoId(Long id);

    PrestamoDTO postPrestamo(PrestamoDTO prestamoDto);

    PrestamoDTO putPrestamo(Long id, PrestamoDTO prestamoDto);

    PrestamoDTO patchPrestamo(Long id, PrestamoDTO prestamoDto);

    void deletePrestamo(Long id);

}
