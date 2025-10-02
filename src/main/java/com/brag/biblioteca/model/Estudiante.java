package com.brag.biblioteca.model;

import java.time.LocalDate;

import lombok.Data;

@Data
public class Estudiante {
    private Integer idEstudiante;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaRegistro;
    private boolean isActivo;
}
