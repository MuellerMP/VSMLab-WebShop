package de.hska.user.core.service.model;

import java.util.List;

public interface UserRepo extends org.springframework.data.repository.CrudRepository<User, Long> {
	List<User> findByUsername(String username);
}
