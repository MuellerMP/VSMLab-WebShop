package de.hska.products.core.service.model;

import javax.persistence.*;

/**
 * This class contains details about categories.
 */
@Entity
@Table(name = "category")
public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String productIds;

	public Category() {
	}

	public Category(String name) {
		this.name = name;
	}

	public Category(String name, String productIds) {
		this.name = name;
		this.productIds = productIds;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "productIds", nullable = true)
	public String getProductIds() {
		return productIds;
	}

	public void setProductIds(String productIds) {
		this.productIds = productIds;
	}

}
