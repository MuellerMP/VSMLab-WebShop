package de.hska.products.comp.service.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import de.hska.products.comp.service.model.Category;
import de.hska.products.comp.service.model.Product;

@RestController
public class ProductsCompController {
	private static String PRODUCTS_URI = "http://localhost:8002/products";
	private static String CATEGORIES_URI = "http://localhost:8003/categories/{id}";
	
	private final Map<Long, Double> productPriceCache = new LinkedHashMap<Long, Double>();
	private final Map<Long, String> productDescCache = new LinkedHashMap<Long, String>();
	private final Map<Long, Product> productCache = new LinkedHashMap<Long, Product>();
	
	// Thread save
	private static final RestTemplate restTemplate = new RestTemplate();
	
	@HystrixCommand(fallbackMethod = "getProductsCache", 
			commandProperties = { @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "2") })
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
			productCache.putIfAbsent(p.getId(), p);
			productDescCache.putIfAbsent(p.getId(), p.getDetails());
			productPriceCache.putIfAbsent(p.getId(), p.getPrice());
		}
		return prod;
	}
	
	public Product[] getProductsCache(String description, String minPrice, String maxPrice) {
		List<Product> list = new ArrayList<>();
		if(description != null && productDescCache.containsValue(description)) {
			for(Long key : getKeysByValue(productDescCache, description)) {
				list.add(productCache.get(key));
			}
		}
		if(minPrice != null && maxPrice != null) {
			Double min = Double.parseDouble(minPrice);
			Double max = Double.parseDouble(maxPrice);
			Map<Long, Product> resmap = productCache.entrySet() 
	          .stream() 
	          .filter(map -> min < map.getKey().doubleValue() && map.getKey().doubleValue() < max) 
	          .collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
			for(Long key : resmap.keySet()) {
				list.add(productCache.get(key));
			}
		}
		return (Product[]) list.toArray();
	}
	
	private <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
	    Set<T> keys = new HashSet<T>();
	    for (Entry<T, E> entry : map.entrySet()) {
	        if (Objects.equals(value, entry.getValue())) {
	            keys.add(entry.getKey());
	        }
	    }
	    return keys;
	}
}

