package com.sprintform.stt.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtResponse {

	private String token;
	private Long id;
	private String username;
	private String email;
	private Set<String> roles;
}
