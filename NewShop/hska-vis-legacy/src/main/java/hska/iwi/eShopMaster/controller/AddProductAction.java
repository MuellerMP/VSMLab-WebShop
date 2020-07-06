package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddProductAction extends ActionSupport {

	private static final long serialVersionUID = 39979991339088L;

	private String name = null;
	private String price = null;
	private int categoryId = 0;
	private String details = null;
	private List<Category> categories;
	

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	private final String ADD_PRODUCT_URL = "http://zuul:8081/products-service/products";
	private final String GET_CATEGORIES_URL = "http://zuul:8081/categories-service/categories";

	public String execute() throws Exception {
		String result = "input";
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");

		if(user != null && (user.getRoletype().equalsIgnoreCase("admin"))) {
			
			Product product = new Product(name, Double.parseDouble(price), Long.valueOf(categoryId),
					details);
			
			int productId = Math.toIntExact(oAuth2RestTemplate.postForObject(ADD_PRODUCT_URL, product, Product.class).getId());

			Category cat = categories.stream().filter(c->c.getId() == Long.valueOf(categoryId)).findAny().get();
			cat.setProductIds(cat.getProductIds().concat(","+productId));
			oAuth2RestTemplate.put(GET_CATEGORIES_URL.concat("/"+categoryId), cat);

			if (productId > 0) {
				result = "success";
			}
		}

		return result;
	}

	@Override
	public void validate() {
		this.setCategories(Arrays.asList(oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL, Category[].class).getBody()));
		// Validate name:

		if (getName() == null || getName().length() == 0) {
			addActionError(getText("error.product.name.required"));
		}

		// Validate price:

		if (String.valueOf(getPrice()).length() > 0) {
			if (!getPrice().matches("[0-9]+(.[0-9][0-9]?)?")
					|| Double.parseDouble(getPrice()) < 0.0) {
				addActionError(getText("error.product.price.regex"));
			}
		} else {
			addActionError(getText("error.product.price.required"));
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
