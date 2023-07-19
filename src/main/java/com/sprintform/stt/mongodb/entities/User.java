package com.sprintform.stt.mongodb.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class User {

	@Transient
	public static final String SEQUENCE_NAME = "user_sequence";

	@Id
	private String id;
	private String username;
	private String email;
	private String password;
	private LocalDateTime createdDate;
	private Set<Role> roles;

	public User(String username, String email, String password, LocalDateTime createdDate) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.createdDate = createdDate;
	}
}
