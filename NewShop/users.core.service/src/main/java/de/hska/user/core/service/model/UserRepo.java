package de.hska.user.core.service.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
	List<User> findByUsername(String username);
}
