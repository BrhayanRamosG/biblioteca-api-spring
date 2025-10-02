package com.brag.biblioteca.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Libro {
    private Integer idLibro;
    private String titulo;
    private String autor;
    private Integer anioPublicacion;
    private Integer cantidadDisponible;
    private LocalDate fechaRegistro;
    private boolean isActivo;
}
