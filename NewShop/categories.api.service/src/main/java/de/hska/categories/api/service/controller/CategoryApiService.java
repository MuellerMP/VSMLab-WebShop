package de.hska.categories.api.service.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.hska.categories.api.service.model.Category;

@RestController
public class CategoryApiService {
private static String CATEGORIES_URI = "http://localhost:8003/categories";
	
	// Thread save
	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/categories", method = RequestMethod.GET)
	public ResponseEntity<?> getByName(@RequestParam("name") String name) {
		String uri = UriComponentsBuilder.fromHttpUrl(CATEGORIES_URI).queryParam("name", name).toUriString();
		return restTemplate.getForEntity(uri, Category.class);
	}
	
	@RequestMapping(value = "/categories", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Category category, Object Category) {
		return restTemplate.postForEntity(CATEGORIES_URI, Category, Category.class);
	}
	
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathParam("id") String id) {
		return restTemplate.getForEntity(CATEGORIES_URI.concat("/{id}"), Category.class, id);
	}
	
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getById(@RequestBody Category category, @PathParam("id") String id) {
		restTemplate.put(CATEGORIES_URI.concat("/{id}"), category, id);
		return null;
	}
	
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathParam("id") String id) {
		restTemplate.delete(CATEGORIES_URI.concat("/{id}"), id);
		return null;
	}
}
