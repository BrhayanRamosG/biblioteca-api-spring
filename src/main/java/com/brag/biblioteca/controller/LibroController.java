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
import com.brag.biblioteca.model.Libro;
import com.brag.biblioteca.service.LibroService;
import com.brag.biblioteca.service.ResponseService;
@RestController
@RequestMapping("/api/libros")
@Validated
public class LibroController {

	private static Logger log = LoggerFactory.getLogger(LibroController.class);

	@Autowired
	private LibroService libroService;
	@Autowired
	private ResponseService responseService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Libro>>> listar() {
		log.info("Empieza peticion listar {} ", System.currentTimeMillis());
		List<Libro> resLibros = libroService.findAll();
		log.info("Termina peticion listar {} ", System.currentTimeMillis());
		return responseService.success(resLibros);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Libro>> obtener(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id) {
		log.info("Empieza peticion obtener {} ", System.currentTimeMillis());
		Libro libro = libroService.findById(id);
		log.info("Termina peticion obtener {} ", System.currentTimeMillis());
		return responseService.success(libro);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Libro>> crear(@RequestBody Libro libro) {
		log.info("Empieza peticion crear {} ", System.currentTimeMillis());
		Libro resLibro = libroService.save(libro);
		log.info("Termina peticion crear {} ", System.currentTimeMillis());
		return responseService.created(resLibro, "Libro creado con éxito");
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Libro>> actualizar(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id,
			@RequestBody Libro libro) {
		log.info("Empieza peticion actualizar {} ", System.currentTimeMillis());
		log.info("Actualizando libro con ID: {}", id);
		libro.setIdLibro(id);
		Libro resLibro = libroService.update(libro);
		log.info("Libro actualizado con ID: {}", id);
		log.info("Termina peticion actualizar {} ", System.currentTimeMillis());
		return responseService.success(resLibro, "Libro actualizado con éxito");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Libro>> eliminar(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id) {
		log.info("Empieza peticion eliminar {} ", System.currentTimeMillis());
		log.info("Eliminando libro con ID: {}", id);
		Libro libro = libroService.delete(id);
		log.info("Libro eliminado con ID: {}", id);
		log.info("Termina peticion eliminar {} ", System.currentTimeMillis());
		return responseService.success(libro, "Libro eliminado con éxito");
	}
}
