package com.brag.biblioteca.mapper;

import org.apache.ibatis.annotations.*;

import com.brag.biblioteca.model.Libro;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LibroMapper {

	List<Libro> findAll();

    Optional<Libro> findById(Integer id);

	void insert(Libro libro);

	int update(Libro libro);

	void delete(Integer id);
}
