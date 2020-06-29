package de.hska.products.core.service.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;

public interface ProductRepo extends org.springframework.data.repository.CrudRepository<Product, Long> {
	@Query("SELECT p FROM Product p WHERE (:details is null or p.details= :details) and (:maxPrice is null"
			  + " or p.price < :maxPrice) and (:minPrice is null or p.price > :minPrice)")
	List<Product> findByDetailsAndPriceLessThanAndPriceGreaterThan(@Nullable String details, @Nullable Double maxPrice, 
			@Nullable Double minPrice);
}
