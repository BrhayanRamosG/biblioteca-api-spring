package com.brag.biblioteca.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.brag.biblioteca.model.ApiResponse;
import com.brag.biblioteca.service.ResponseService;
import com.brag.biblioteca.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

	private static Logger log = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private ResponseService responseService;
	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/login")
	public ResponseEntity<ApiResponse<String>> login(@RequestParam String username) {
		log.info("Inicio de peticion login {}", System.currentTimeMillis());
		log.info("Usuario {} ha iniciado sesión", username);
		
		log.info("Fin de peticion login {}", System.currentTimeMillis());
		return responseService.success(JwtUtil.generateToken(username));
	}
	
	

}
