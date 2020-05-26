package de.hska.products.core.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.products.core.service.model.Product;
import de.hska.products.core.service.model.ProductRepo;

@RestController
public class ProductCoreController {
	@Autowired
	private ProductRepo repo;
	
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(@RequestParam("description") String description,
			@RequestParam("minPrice") String minPrice, @RequestParam("maxPrice") String maxPrice) {
		Iterable<Product> allPolls = repo.findAll();
		return new ResponseEntity<Iterable<Product>>(allPolls, HttpStatus.OK);
	}
	@RequestMapping(value = "/products", method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product product) {
		product = repo.save(product);
		return new ResponseEntity<Object>(null, HttpStatus.CREATED);
	}
	@RequestMapping(value = "/products/{productId}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable Long productId) {
		Product user = repo.findById(productId).orElse(null);
		return new ResponseEntity<Product>(user, HttpStatus.OK);
	}
	@RequestMapping(value = "/products/{productId}", method = RequestMethod.PUT)
	public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
		Product plocal = repo.findById(productId).orElse(null);
		if(plocal != null && product != null && plocal.getId() == product.getId()) {
			repo.save(product);
		}
		return new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	@RequestMapping(value = "/products/{productId}", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteProduct(@PathVariable Long productId) {
		repo.deleteById(productId);
		return new ResponseEntity<Object>(null, HttpStatus.OK);
	}
}
