package de.hska.products.comp.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import de.hska.products.comp.service.model.Product;

@RestController
public class ProductsCompController {
	
	@Autowired
	ProductCompClient client;
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<?> search(@RequestParam("description") String description, @RequestParam("minPrice") String minPrice,
			@RequestParam("maxPrice") String maxPrice) {
		Product[] prod = client.getProducts(description, minPrice, maxPrice);
		return new ResponseEntity<Product[]>(prod, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/categories/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> search(@PathVariable Long id) {
		client.deleteCategory(id);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}

