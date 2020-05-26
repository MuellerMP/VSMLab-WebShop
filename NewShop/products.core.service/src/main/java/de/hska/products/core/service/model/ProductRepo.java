package de.hska.products.core.service.model;

import java.util.List;

import org.springframework.lang.Nullable;

public interface ProductRepo extends org.springframework.data.repository.CrudRepository<Product, Long> {
	List<Product> findByDetailsAndPriceLessThanAndPriceGreaterThan(@Nullable String details, @Nullable String maxPrice, 
			@Nullable String minPrice);
}
