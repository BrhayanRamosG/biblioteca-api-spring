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
import com.brag.biblioteca.model.Prestamo;
import com.brag.biblioteca.service.PrestamoService;
import com.brag.biblioteca.service.ResponseService;

@RestController
@RequestMapping("/api/prestamos")
@Validated
public class PrestamoController {

	private static Logger log = LoggerFactory.getLogger(PrestamoController.class);

	@Autowired
	private PrestamoService prestamoService;
	@Autowired
	private ResponseService responseService;

	@GetMapping
	public ResponseEntity<ApiResponse<List<Prestamo>>> listar() {
		log.info("Empieza peticion listar {} ", System.currentTimeMillis());
		List<Prestamo> resPrestamos = prestamoService.findAll();
		log.info("Termina peticion listar {} ", System.currentTimeMillis());
		return responseService.success(resPrestamos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<Prestamo>> obtener(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id) {
		log.info("Empieza peticion obtener {} ", System.currentTimeMillis());
		Prestamo prestamo = prestamoService.findById(id);
		log.info("Termina peticion obtener {} ", System.currentTimeMillis());
		return responseService.success(prestamo);
	}

	@PostMapping
	public ResponseEntity<ApiResponse<Prestamo>> crear(@RequestBody Prestamo prestamo) {
		log.info("Empieza peticion crear {} ", System.currentTimeMillis());
		Prestamo resPrestamo = prestamoService.save(prestamo);
		log.info("Termina peticion crear {} ", System.currentTimeMillis());
		return responseService.created(resPrestamo, "Préstamo creado con éxito");
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<Prestamo>> actualizar(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id,
			@RequestBody Prestamo prestamo) {
		log.info("Actualizando prestamo con ID: {}", id);
		prestamo.setIdPrestamo(id);
		Prestamo resPrestamo = prestamoService.update(prestamo);
		log.info("Préstamo actualizado con ID: {}", id);
		return responseService.success(resPrestamo, "Préstamo actualizado con éxito");
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Prestamo>> eliminar(
			@PathVariable @Valid @Min(value = 1, message = "No puede ser menor a {value}") Integer id) {
		log.info("Eliminando prestamo con ID: {}", id);
		Prestamo prestamo = prestamoService.delete(id);
		log.info("Prestamo eliminado con ID: {}", id);
		return responseService.success(prestamo, "Préstamo eliminado con éxito");
	}
}
