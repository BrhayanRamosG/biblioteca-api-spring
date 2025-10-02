package com.brag.biblioteca.exception;

import java.util.Collections;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.brag.biblioteca.model.ApiResponse;
import com.brag.biblioteca.model.ValidationError;

/**
 * Manejador global de excepciones para la aplicación.
 * Captura y maneja excepciones especificas lanzadas en los controladores.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ApiResponse<Void>> handleConstraintViolationException(ConstraintViolationException ex) {

		List<ValidationError> errors = ex.getConstraintViolations().stream().map(v -> {
			String fieldName = v.getPropertyPath().toString();

			if (fieldName.contains(".")) {
				String[] parts = fieldName.split("\\.");
				fieldName = parts[parts.length - 1];
			}
			return new ValidationError(fieldName, v.getMessage(), v.getInvalidValue());
		}).toList();

		ApiResponse<Void> response = new ApiResponse<>(false, "Error de validación", null, errors,
				HttpStatus.BAD_REQUEST.value());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<ApiResponse<List<?>>> handleCustomException(CustomException ex) {
		ApiResponse<List<?>> response = new ApiResponse<>(false, ex.getMessage(), Collections.emptyList(), null,
				ex.getStatus().value());

		return ResponseEntity.status(ex.getStatus()).body(response);
	}
}
