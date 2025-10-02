package com.brag.biblioteca.service;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brag.biblioteca.exception.CustomException;
import com.brag.biblioteca.mapper.LibroMapper;
import com.brag.biblioteca.model.Libro;

@Service
public class LibroService {
	private static final Logger log = LoggerFactory.getLogger(LibroService.class);

	@Autowired
	private LibroMapper libroMapper;

	/**
	 * Búsqueda de todos los libros
	 * @return Lista de libros
	 */
	public List<Libro> findAll() {
		log.info("Obteniendo todos los libros");
		try {
			return libroMapper.findAll();
		} catch (PersistenceException e) {
			log.error("Error al obtener los libros: {}", e.getMessage());
			throw new CustomException("Error al obtener los libros", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Búsqueda de un libro por su ID
	 * @param id
	 * @return Libro encontrado
	 */
	public Libro findById(Integer id) {
		log.info("Obteniendo libro con ID: {}", id);
		try {
			return libroMapper.findById(id)
					.orElseThrow(() -> new CustomException("Libro no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al obtener libro: {}", e.getMessage());
			throw new CustomException("Error al obtener libro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Creación de un nuevo libro
	 * @param libro
	 */
	public Libro save(Libro libro) {
		log.info("Guardando nuevo libro: {}", libro);
		try {
			libroMapper.insert(libro);
			
			return libroMapper.findById(libro.getIdLibro())
					.orElseThrow(() -> new CustomException("Libro no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al guardar libro: {}", e.getMessage());
			throw new CustomException("Error al guardar libro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualización de un libro existente
	 * @param libro
	 */
	public Libro update(Libro libro) {
		log.info("Actualizando libro: {}", libro);
		try {
			int filasAfectadas = libroMapper.update(libro);
			
			if (filasAfectadas == 0) {
				throw new CustomException("Libro no encontrado para actualizar", HttpStatus.NOT_FOUND);
			}
			
			return libroMapper.findById(libro.getIdLibro())
					.orElseThrow(() -> new CustomException("Libro no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al actualizar libro: {}", e.getMessage());
			throw new CustomException("Error al actualizar libro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Eliminación lógica de un libro
	 * @param id
	 * @return Libro eliminado
	 */
	public Libro delete(Integer id) {
		log.info("Eliminando libro con ID: {}", id);
		Libro libro = null;
		try {
			libro = libroMapper.findById(id)
					.orElseThrow(() -> new CustomException("Libro no encontrado", HttpStatus.NOT_FOUND));
			
			libroMapper.delete(id);
			libro.setActivo(false);
		} catch (PersistenceException e) {
			log.error("Error al eliminar libro: {}", e.getMessage());
			throw new CustomException("Error al eliminar libro", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return libro;
	}
}
