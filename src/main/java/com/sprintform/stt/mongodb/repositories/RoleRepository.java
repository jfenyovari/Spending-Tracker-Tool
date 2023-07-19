package com.sprintform.stt.mongodb.repositories;

import com.sprintform.stt.mongodb.entities.Role;
import com.sprintform.stt.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {

	Optional<Role> findByName(ERole name);
}
