package com.sprintform.stt.mongodb.repositories;

import com.sprintform.stt.mongodb.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	@Query("{$or: [{firstName: /?0/}, {lastName: /?0/}, {login: /?0/}]}")
	List<User> searchUsers(String term);
}
