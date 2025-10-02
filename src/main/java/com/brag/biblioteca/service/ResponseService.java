package com.brag.biblioteca.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.brag.biblioteca.model.ApiResponse;

@Service
public class ResponseService {
	
	/**
	 * Respuesta exitosa con datos
	 * 
	 * @param <T>  Tipo de dato en la respuesta
	 * @param data Datos a incluir en la respuesta
	 * @return ResponseEntity con ApiResponse
	 */
	public <T> ResponseEntity<ApiResponse<T>> success(T data) {
		ApiResponse<T> response = new ApiResponse<>(true, "Operación exitosa", data, null, HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * Respuesta exitosa con datos y mensaje personalizado
	 * 
	 * @param <T>     Tipo de dato en la respuesta
	 * @param data    Datos a incluir en la respuesta
	 * @param message Mensaje personalizado
	 * @return ResponseEntity con ApiResponse
	 */
	public <T> ResponseEntity<ApiResponse<T>> success(T data, String message) {
		ApiResponse<T> response = new ApiResponse<>(true, message, data, null, HttpStatus.OK.value());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * Respuesta de creación exitosa con datos y mensaje personalizado
	 * 
	 * @param <T>     Tipo de dato en la respuesta
	 * @param data    Datos a incluir en la respuesta
	 * @param message Mensaje personalizado
	 * @return ResponseEntity con ApiResponse
	 */
	public <T> ResponseEntity<ApiResponse<T>> created(T data, String message) {
		ApiResponse<T> response = new ApiResponse<>(true, message, data, null, HttpStatus.CREATED.value());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
