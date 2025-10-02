package com.brag.biblioteca.mapper;

import org.apache.ibatis.annotations.*;

import com.brag.biblioteca.model.Estudiante;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EstudianteMapper {

	List<Estudiante> findAll();

    Optional<Estudiante> findById(Integer id);

	void insert(Estudiante libro);

	int update(Estudiante libro);

	void delete(Integer id);
}
