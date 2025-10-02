package com.brag.biblioteca.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Prestamo {
    private Integer idPrestamo;
    private Integer idEstudiante;
    private Integer idLibro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private char devuelto;
    private boolean isActivo;
}