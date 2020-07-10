package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.businessLogic.manager.CategoryManager;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteCategoryAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1254575994729199914L;
	
	private int catId;
	private List<Category> categories;
	
	
	private final String CATEGORIES_URL = "http://zuul:8081/products-comp-service/categories";
	private final String GET_CATEGORIES_URL = "http://zuul:8081/categories-service/categories";

	public String execute() throws Exception {
		OAuth2RestTemplate oAuth2RestTemplate = OAuth2Config.getTemplate();
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRoletype().equalsIgnoreCase("admin"))) {

			// Helper inserts new Category in DB:
			try {
				oAuth2RestTemplate.delete(CATEGORIES_URL.concat("/"+catId));

				Category[] arr2 = oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL,Category[].class).getBody();
				if(arr2 != null) {
					this.categories = Arrays.asList(arr2);
				} else {
					this.categories = Collections.emptyList();
				}
			} catch(Exception e) {
				e.printStackTrace();
				throw e;
			}				
			res = "success";

		}
		
		return res;
		
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
}
