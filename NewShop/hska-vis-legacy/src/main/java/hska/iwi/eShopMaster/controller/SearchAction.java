package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class SearchAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6565401833074694229L;
	
	
	private String searchDescription = null;
	private String searchMinPrice;
	private String searchMaxPrice;
	
	private Double sMinPrice = null;
	private Double sMaxPrice = null;
	
	private User user;
	private List<Product> products;
	private List<Category> categories;

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	private final String PRODUCTS_URL = "http://localhost:8081/products-comp-service/products";
	private final String GET_CATEGORIES_URL = "http://localhost:8081/categories-service/categories";

	public String execute() throws Exception {
		
		String result = "input";
		
		// Get user:
		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		ActionContext.getContext().setLocale(Locale.US);  
		
		if(user != null){
			// Search products and show results:
//			this.products = productManager.getProductsForSearchValues(this.searchDescription, this.searchMinPrice, this.searchMaxPrice);
			if (!searchMinPrice.isEmpty()){
				sMinPrice =  Double.parseDouble(this.searchMinPrice);
			}
			if (!searchMaxPrice.isEmpty()){
				sMaxPrice =  Double.parseDouble(this.searchMaxPrice);
			}
			StringBuilder searchQuery = new StringBuilder();
			searchQuery.append(PRODUCTS_URL);
			if(searchDescription != null && sMinPrice != null && sMaxPrice != null) {
				boolean notFirst = false;
				searchQuery.append("?");
				if(searchDescription != null) {
					searchQuery.append("searchDescrption=").append(searchDescription);
					notFirst = true;
				}
				if(sMinPrice != null) {
					if(notFirst) {
						searchQuery.append("&");
					}
					searchQuery.append("minPrice=").append(sMinPrice);
				}
				if(sMaxPrice != null) {
					if(notFirst) {
						searchQuery.append("&");
					}
					searchQuery.append("maxPrice=").append(sMaxPrice);
				}
			}
			this.products = Arrays.asList(oAuth2RestTemplate.getForEntity(searchQuery.toString(),Product[].class).getBody());
			
			// Show all categories:
			this.categories = Arrays.asList(oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL.concat(searchQuery.toString()),Category[].class).getBody());
			result = "success";
		}
		
		return result;
	}
			
		
		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}
		
		public List<Product> getProducts() {
			return products;
		}

		public void setProducts(List<Product> products) {
			this.products = products;
		}
		
		public List<Category> getCategories() {
			return categories;
		}

		public void setCategories(List<Category> categories) {
			this.categories = categories;
		}
		
		


	public String getSearchValue() {
		return searchDescription;
	}


	public void setSearchValue(String searchValue) {
		this.searchDescription = searchValue;
	}


	public String getSearchMinPrice() {
		return searchMinPrice;
	}


	public void setSearchMinPrice(String searchMinPrice) {
		this.searchMinPrice = searchMinPrice;
	}


	public String getSearchMaxPrice() {
		return searchMaxPrice;
	}


	public void setSearchMaxPrice(String searchMaxPrice) {
		this.searchMaxPrice = searchMaxPrice;
	}


//	public Double getSearchMinPrice() {
//		return searchMinPrice;
//	}
//
//
//	public void setSearchMinPrice(Double searchMinPrice) {
//		this.searchMinPrice = searchMinPrice;
//	}
//
//
//	public Double getSearchMaxPrice() {
//		return searchMaxPrice;
//	}
//
//
//	public void setSearchMaxPrice(Double searchMaxPrice) {
//		this.searchMaxPrice = searchMaxPrice;
//	}
}
