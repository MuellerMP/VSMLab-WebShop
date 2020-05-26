package de.hska.products.api.service.controller;

import javax.websocket.server.PathParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.hska.products.api.service.model.Product;

@RestController
public class ProductsApiController {
	private static String PRODUCTS_URI = "http://localhost:8002/products";
	
	// Thread save
	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<?> getBySearchParams(@RequestParam("description") String description, @RequestParam("minPrice") String minPrice,
			@RequestParam("maxPrice") String maxPrice) {
		String uri = UriComponentsBuilder.fromHttpUrl(PRODUCTS_URI).queryParam("description", description)
				.queryParam("minPrice", minPrice)
				.queryParam("maxPrice", maxPrice)
				.toUriString();
		return restTemplate.getForEntity(uri, Product[].class);
	}
	
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody Product product) {
		return restTemplate.postForEntity(PRODUCTS_URI, product, Product.class);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getById(@PathParam("id") String id) {
		return restTemplate.getForEntity(PRODUCTS_URI.concat("/{id}"), Product.class, id);
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> getById(@RequestBody Product product, @PathParam("id") String id) {
		restTemplate.put(PRODUCTS_URI.concat("/{id}"), product, id);
		return null;
	}
	
	@RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathParam("id") String id) {
		restTemplate.delete(PRODUCTS_URI.concat("/{id}"), id);
		return null;
	}
}

