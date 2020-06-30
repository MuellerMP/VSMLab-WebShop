package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AddCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6704600867133294378L;
	
	private String newCatName = null;
	
	private List<Category> categories;
	
	User user;
	
	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	private static final String GET_CATEGORIES_URL = "http://localhost:8081/categories-service/categories";

	public String execute() throws Exception {

		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		if(user != null && (user.getRoletype().equalsIgnoreCase("admin"))) {
			// Add category
			Category cat = new Category(newCatName);
			oAuth2RestTemplate.postForEntity(GET_CATEGORIES_URL, cat, Category.class);
			
			// Go and get new Category list
			this.setCategories(Arrays.asList(oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL, Category[].class).getBody()));
			
			res = "success";
		}
		
		return res;
	
	}
	
	@Override
	public void validate(){
		if (getNewCatName().length() == 0) {
			addActionError(getText("error.catname.required"));
		}
		// Go and get new Category list
		this.setCategories(Arrays.asList(oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL, Category[].class).getBody()));
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	
	public String getNewCatName() {
		return newCatName;
	}

	public void setNewCatName(String newCatName) {
		this.newCatName = newCatName;
	}
}
