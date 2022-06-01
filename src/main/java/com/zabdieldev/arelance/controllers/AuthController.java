/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.zabdieldev.arelance.controllers;

import com.zabdieldev.arelance.dto.*;
import com.zabdieldev.arelance.models.*;
import com.zabdieldev.arelance.repositories.*;
import com.zabdieldev.arelance.security.*;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author jonat
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmpleadoRepository empleadoRepository;

	@Autowired
	private RolRepository rolRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/iniciarSesion")
	public ResponseEntity<JWTAuthResonseDTO> authenticateUser(@RequestBody LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginDTO.getUsernameOrEmail(), loginDTO.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// obtenemos el token del jwtTokenProvider
		String token = jwtTokenProvider.generarToken(authentication);

		return ResponseEntity.ok(new JWTAuthResonseDTO(token));
	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrarUsuario(@RequestBody RegistroDTO registroDTO) {
		if (empleadoRepository.existsByUsername(registroDTO.getUsername())) {
			return new ResponseEntity<>("Ese nombre de usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		if (empleadoRepository.existByEmail(registroDTO.getEmail())) {
			return new ResponseEntity<>("Ese email de usuario ya existe", HttpStatus.BAD_REQUEST);
		}

		Empleado empleado = new Empleado();
		empleado.setNombre(registroDTO.getNombre());
                empleado.setApellido(registroDTO.getApellido());
                empleado.setDni(registroDTO.getDni());
                empleado.setDireccion(registroDTO.getDireccion());
		empleado.setUsername(registroDTO.getUsername());
		empleado.setEmail(registroDTO.getEmail());
		empleado.setPassword(passwordEncoder.encode(registroDTO.getPassword()));

		Rol roles = rolRepository.findByNombre("ROLE_ADMIN").get();
		empleado.setRol(Collections.singleton(roles));

		empleadoRepository.save(empleado);
		return new ResponseEntity<>("Empleado registrado exitosamente", HttpStatus.OK);
	}
}
