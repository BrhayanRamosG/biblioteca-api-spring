package com.brag.biblioteca.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 8192132608346417333L;
	private final HttpStatus status;

	public CustomException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public CustomException(String message, HttpStatus status, Throwable cause) {
		super(message, cause);
		this.status = status;
	}
}