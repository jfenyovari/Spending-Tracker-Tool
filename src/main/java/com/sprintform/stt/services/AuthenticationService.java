package com.sprintform.stt.services;

import com.sprintform.stt.dto.requests.LoginRequest;
import com.sprintform.stt.dto.requests.SignupRequest;
import com.sprintform.stt.dto.responses.JwtResponse;
import com.sprintform.stt.dto.responses.MessageResponse;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticationService {

	JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest);

	MessageResponse registerUser(@RequestBody SignupRequest signUpRequest);
}
