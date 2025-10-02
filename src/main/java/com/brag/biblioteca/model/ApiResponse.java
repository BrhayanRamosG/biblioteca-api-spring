package com.brag.biblioteca.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T data;
	private List<ValidationError> errors;
	private int statusCode;
	private LocalDateTime timestamp;

	public ApiResponse(boolean success, String message, T data, List<ValidationError> errors, int statusCode) {
		this.success = success;
		this.message = message;
		this.data = data;
		this.errors = errors;
		this.statusCode = statusCode;
		this.timestamp = LocalDateTime.now();
	}

}
