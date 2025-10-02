package com.brag.biblioteca.service;

import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.brag.biblioteca.exception.CustomException;
import com.brag.biblioteca.mapper.EstudianteMapper;
import com.brag.biblioteca.model.Estudiante;

@Service
public class EstudianteService {
	private static final Logger log = LoggerFactory.getLogger(EstudianteService.class);

	@Autowired
	private EstudianteMapper estudianteMapper;

	/**
	 * Búsqueda de todos los estudiantes
	 * @return Lista de estudiantes
	 */
	public List<Estudiante> findAll() {
		log.info("Obteniendo todos los estudiantess");
		try {
			return estudianteMapper.findAll();
		} catch (PersistenceException e) {
			log.error("Error al obtener los estudiantes: {}", e.getMessage());
			throw new CustomException("Error al obtener los estudiantes", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Búsqueda de un estudiante por su ID
	 * @param id
	 * @return Estudiante encontrado
	 */
	public Estudiante findById(Integer id) {
		log.info("Obteniendo estudiante con ID: {}", id);
		try {
			return estudianteMapper.findById(id)
					.orElseThrow(() -> new CustomException("Estudiante no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al obtener Estudiante: {}", e.getMessage());
			throw new CustomException("Error al obtener Estudiante", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Creación de un nuevo estudiante
	 * @param estudiante
	 */
	public Estudiante save(Estudiante estudiante) {
		log.info("Guardando nuevo estudiante: {}", estudiante);
		try {
			estudianteMapper.insert(estudiante);
			
			return estudianteMapper.findById(estudiante.getIdEstudiante()).orElseThrow(() -> new CustomException("Estudiante no encontrado", HttpStatus.NOT_FOUND));
		} catch (PersistenceException e) {
			log.error("Error al guardar estudiante: {}", e.getMessage());
			throw new CustomException("Error al guardar estudiante", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Actualización de un estudiante existente
	 * @param estudiante
	 */
	public Estudiante update(Estudiante estudiante) {
		log.info("Actualizando estudiante: {}", estudiante);
		try {
			int filasAfectadas = estudianteMapper.update(estudiante);
			
			if (filasAfectadas == 0) {
				throw new CustomException("Estudiante no encontrado para actualizar", HttpStatus.NOT_FOUND);
			}
			
			return estudianteMapper.findById(estudiante.getIdEstudiante())
					.orElseThrow(() -> new CustomException("Estudiante no encontrado", HttpStatus.NOT_FOUND));
			
		} catch (PersistenceException e) {
			log.error("Error al actualizar estudiante: {}", e.getMessage());
			throw new CustomException("Error al actualizar estudiante", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Eliminación lógica de un estudiante
	 * @param id
	 * @return Estudiante eliminado
	 */
	public Estudiante delete(Integer id) {
		log.info("Eliminando estudiante con ID: {}", id);
		Estudiante estudiante = null;
		try {
			estudiante = estudianteMapper.findById(id)
					.orElseThrow(() -> new CustomException("Estudiante no encontrado", HttpStatus.NOT_FOUND));
			
			estudianteMapper.delete(id);
			estudiante.setActivo(false);
		} catch (PersistenceException e) {
			log.error("Error al eliminar estudiante: {}", e.getMessage());
			throw new CustomException("Error al eliminar estudiante", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return estudiante;
	}
}
