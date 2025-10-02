package com.brag.biblioteca.service;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brag.biblioteca.exception.CustomException;
import com.brag.biblioteca.mapper.PrestamoMapper;
import com.brag.biblioteca.model.Prestamo;

@Service
public class PrestamoService {
	private static final Logger log = LoggerFactory.getLogger(PrestamoService.class);

	@Autowired
	private PrestamoMapper prestamoMapper;

	/**
	 * Búsqueda de todos los prestamos
	 * @return Lista de prestamos
	 */
	public List<Prestamo> findAll() {
		log.info("Obteniendo todos los prestamos");
		try {
			return prestamoMapper.findAll();
		} catch (PersistenceException e) {
			log.error("Error al obtener los prestamos: {}", e.getMessage());
			throw new CustomException("Error al obtener los prestamos", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Búsqueda de un prestamo por su ID
	 * @param id
	 * @return Prestamo encontrado
	 */
	public Prestamo findById(Integer id) {
		log.info("Obteniendo prestamo con ID: {}", id);
		try {
			return prestamoMapper.findById(id)
					.orElseThrow(() -> new CustomException("Prestamo no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al obtener Prestamo: {}", e.getMessage());
			throw new CustomException("Error al obtener Prestamo", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Creación de un nuevo prestamo
	 * @param prestamo
	 */
	public Prestamo save(Prestamo prestamo) {
		log.info("Guardando nuevo prestamo: {}", prestamo);
		try {
			prestamoMapper.insert(prestamo);
			
			return prestamoMapper.findById(prestamo.getIdPrestamo()).orElseThrow(() -> new CustomException("Prestamo no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al guardar prestamo: {}", e.getMessage());
			throw new CustomException("Error al guardar prestamo", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualización de un prestamo existente
	 * @param prestamo
	 */
	public Prestamo update(Prestamo prestamo) {
		log.info("Actualizando prestamo: {}", prestamo);
		try {
			int filasAfectadas = prestamoMapper.update(prestamo);
			
			if (filasAfectadas == 0) {
				throw new CustomException("Prestamo no encontrado para actualizar", HttpStatus.NOT_FOUND);
			}
			
			return prestamoMapper.findById(prestamo.getIdPrestamo())
					.orElseThrow(() -> new CustomException("Prestamo no encontrado", HttpStatus.NOT_FOUND));
			
		} catch (PersistenceException e) {
			log.error("Error al actualizar prestamo: {}", e.getMessage());
			throw new CustomException("Error al actualizar prestamo", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Eliminación lógica de un prestamo
	 * @param id
	 * @return Prestamo eliminado
	 */
	public Prestamo delete(Integer id) {
		log.info("Eliminando prestamo con ID: {}", id);
		Prestamo prestamo = null;
		try {
			prestamo = prestamoMapper.findById(id)
					.orElseThrow(() -> new CustomException("Prestamo no encontrado", HttpStatus.NOT_FOUND));
			
			prestamoMapper.delete(id);
			prestamo.setActivo(false);
		} catch (PersistenceException e) {
			log.error("Error al eliminar prestamo: {}", e.getMessage());
			throw new CustomException("Error al eliminar prestamo", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return prestamo;
	}
}
