package com.brag.biblioteca.mapper;

import com.brag.biblioteca.model.Prestamo;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PrestamoMapper {

	List<Prestamo> findAll();

	Optional<Prestamo> findById(Integer id);

	void insert(Prestamo prestamo);

	int update(Prestamo prestamo);

	void delete(Integer id);
}
