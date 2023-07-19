package com.sprintform.stt.services.impl;

import com.sprintform.stt.dto.requests.LoginRequest;
import com.sprintform.stt.dto.requests.SignupRequest;
import com.sprintform.stt.dto.responses.JwtResponse;
import com.sprintform.stt.dto.responses.MessageResponse;
import com.sprintform.stt.exceptions.SttException;
import com.sprintform.stt.mongodb.entities.Role;
import com.sprintform.stt.mongodb.entities.User;
import com.sprintform.stt.mongodb.repositories.RoleRepository;
import com.sprintform.stt.mongodb.repositories.UserRepository;
import com.sprintform.stt.services.AuthenticationService;
import com.sprintform.stt.enums.ERole;
import com.sprintform.stt.services.models.UserDetailsImpl;
import com.sprintform.stt.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;


	@Override
	public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		Set<String> roles = userDetails.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toSet());

		return new JwtResponse(jwt,
				userDetails.getId(),
				userDetails.getUsername(),
				userDetails.getEmail(),
				roles);
	}

	@Override
	public MessageResponse registerUser(@RequestBody SignupRequest signUpRequest) {
		if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
			throw new SttException("Username is already taken!", HttpStatus.BAD_REQUEST);
		}

		if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
			throw new SttException("Email is already taken!", HttpStatus.BAD_REQUEST);
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(),
				signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()), LocalDateTime.now());

		Set<String> strRoles = signUpRequest.getRoles();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
					case "admin":
						Role adminRole = roleRepository.findByName(ERole.ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "mod":
						Role modRole = roleRepository.findByName(ERole.MOD)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					default:
						Role userRole = roleRepository.findByName(ERole.USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.insert(user);

		return new MessageResponse("User registered successfully!");
	}

}
