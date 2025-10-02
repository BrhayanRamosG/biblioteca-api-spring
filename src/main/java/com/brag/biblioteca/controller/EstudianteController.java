package com.brag.biblioteca.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brag.biblioteca.model.ApiResponse;
import com.brag.biblioteca.model.Estudiante;
import com.brag.biblioteca.service.EstudianteService;
import com.brag.biblioteca.service.ResponseService;

@RestController
@RequestMapping("/api/estudiantes")
@Validated
public class EstudianteController {

	private static Logger log = LoggerFactory.getLogger(EstudianteController.class);

	@Autowired
	private EstudianteService estudianteService;
	@Autowired
	private ResponseService responseService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Estudiante>>> listar() {
		log.info("Empieza peticion listar {} ", System.currentTimeMillis());
		List<Estudiante> resEstudiantes = estudianteService.findAll();
		log.info("Termina peticion listar {} ", System.currentTimeMillis());
		return responseService.success(resEstudiantes);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Estudiante>> obtener(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id) {
		log.info("Empieza peticion obtener {} ", System.currentTimeMillis());
		Estudiante estudiante = estudianteService.findById(id);
		log.info("Termina peticion obtener {} ", System.currentTimeMillis());
		return responseService.success(estudiante);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Estudiante>> crear(@RequestBody Estudiante estudiante) {
		log.info("Empieza peticion crear {} ", System.currentTimeMillis());
		Estudiante resEstudiante = estudianteService.save(estudiante);
		log.info("Termina peticion crear {} ", System.currentTimeMillis());
		return responseService.created(resEstudiante, "Estudiante creado con éxito");
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Estudiante>> actualizar(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id,
			@RequestBody Estudiante estudiante) {
		log.info("Empieza peticion actualizar {} ", System.currentTimeMillis());
		log.info("Actualizando estudiante con ID: {}", id);
		estudiante.setIdEstudiante(id);
		Estudiante resEstudiante = estudianteService.update(estudiante);
		log.info("Estudiante actualizado con ID: {}", id);
		log.info("Termina peticion actualizar {} ", System.currentTimeMillis());
		return responseService.success(resEstudiante, "Estudiante actualizado con éxito");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Estudiante>> eliminar(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id) {
		log.info("Empieza peticion eliminar {} ", System.currentTimeMillis());
		log.info("Eliminando estudiante con ID: {}", id);
		Estudiante estudiante = estudianteService.delete(id);
		log.info("Estudiante eliminado con ID: {}", id);
		log.info("Termina peticion eliminar {} ", System.currentTimeMillis());
		return responseService.success(estudiante, "Estudiante eliminado con éxito");
	}
}
