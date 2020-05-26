package de.hska.user.api.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import de.hska.user.api.service.model.User;

@RestController
public class UserApiController {

	private static String USER_URI = "http://localhost:8001/users";
	
	// Thread save
	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) {
		return restTemplate.postForEntity(USER_URI, user, ResponseEntity.class);
	}
}
