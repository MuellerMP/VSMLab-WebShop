package de.hska.products.comp.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import de.hska.products.comp.service.model.Category;
import de.hska.products.comp.service.model.Product;

@RestController
public class ProductsCompController {
	private static String PRODUCTS_URI = "http://localhost:8002/products";
	private static String CATEGORIES_URI = "http://localhost:8003/categories/{id}";
	
	// Thread save
	private static RestTemplate restTemplate = new RestTemplate();
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam("description") String description, @RequestParam("minPrice") String minPrice,
			@RequestParam("maxPrice") String maxPrice) {
		String uri = UriComponentsBuilder.fromHttpUrl(PRODUCTS_URI).queryParam("description", description)
				.queryParam("minPrice", minPrice)
				.queryParam("maxPrice", maxPrice)
				.toUriString();
		ResponseEntity<Product[]> prod = restTemplate.getForEntity(uri, Product[].class);
		for(Product p : prod.getBody()) {
			ResponseEntity<Category> c = restTemplate.getForEntity(CATEGORIES_URI, Category.class, p.getCategory().getId());
			p.setCategory(c.getBody());
		}
		return prod;
	}
}

