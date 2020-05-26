package de.hska.search.api.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class SearchApiController {

	private static String PRODUCTS_COMP_URI = "http://localhost:8004/products";
	
	// Thread save
	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/productsWithCategories", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam("description") String description, @RequestParam("minPrice") String minPrice,
			@RequestParam("maxPrice") String maxPrice) {
		return restTemplate.getForEntity(PRODUCTS_COMP_URI, ResponseEntity.class, description, minPrice, maxPrice);
	}
}
