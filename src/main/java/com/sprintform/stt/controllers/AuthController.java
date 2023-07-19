package com.sprintform.stt.controllers;

import com.sprintform.stt.dto.requests.LoginRequest;
import com.sprintform.stt.dto.requests.SignupRequest;
import com.sprintform.stt.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(authenticationService.authenticateUser(loginRequest));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
		return ResponseEntity.ok(authenticationService.registerUser(signUpRequest));
	}
}
