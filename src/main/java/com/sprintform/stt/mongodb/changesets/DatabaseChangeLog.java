package com.sprintform.stt.mongodb.changesets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.sprintform.stt.mongodb.entities.Role;
import com.sprintform.stt.mongodb.entities.Transaction;
import com.sprintform.stt.mongodb.entities.User;
import com.sprintform.stt.mongodb.repositories.RoleRepository;
import com.sprintform.stt.mongodb.repositories.TransactionRepository;
import com.sprintform.stt.mongodb.repositories.UserRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@ChangeLog
public class DatabaseChangeLog {


	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@ChangeSet(order = "001", id = "initRoles", author = "Jakab")
	public void initRoles(RoleRepository roleRepository) {
		List<Role> roles = new ArrayList<>();
		roles.add(new Role("1", "ADMIN"));
		roles.add(new Role("2", "MOD"));
		roles.add(new Role("3", "USER"));

		roleRepository.insert(roles);
	}

	@ChangeSet(order = "002", id = "initUsers", author = "Jakab")
	public void initUsers(UserRepository userRepository) {
		List<User> users = new ArrayList<>();
		users.add(new User("jakab", "jakab@mail.com", passwordEncoder.encode("qwer1234"), LocalDateTime.now(), new HashSet<>(List.of(new Role("1", "ADMIN")))));
		users.add(new User("lena", "lena@mail.com", passwordEncoder.encode("qwer1234"), LocalDateTime.now(), new HashSet<>(List.of(new Role("3", "USER")))));
		userRepository.insert(users);
	}

	@ChangeSet(order = "003", id = "addTransactions", author = "Jakab")
	public void addTransactions(TransactionRepository transactionRepository) throws IOException {

		File resource = new ClassPathResource("transactions.json").getFile();
		String transactionsJson = new String(Files.readAllBytes(resource.toPath()));

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		List<Transaction> transactions = mapper.readValue(transactionsJson, new TypeReference<>() {
		});

		AtomicReference<Integer> days = new AtomicReference<>(100);
		transactions.forEach(t -> t.setPaid(LocalDateTime.now().minusDays(days.getAndSet(days.get() - 1))));

		transactionRepository.insert(transactions);
	}
}
