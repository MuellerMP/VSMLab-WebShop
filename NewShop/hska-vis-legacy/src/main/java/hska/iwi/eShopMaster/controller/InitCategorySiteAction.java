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

public class InitCategorySiteAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1108136421569378914L;

	private String pageToGoTo;
	private User user;

	private List<Category> categories;

	@Autowired
	private OAuth2RestTemplate oAuth2RestTemplate;
	
	private static final String GET_CATEGORIES_URL = "http://localhost:8081/categories-service/categories";

	public String execute() throws Exception {
		
		String res = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		user = (User) session.get("webshop_user");
		boolean isAdmin = true;
		if(user != null && isAdmin) {

			this.setCategories(Arrays.asList(oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL, Category[].class).getBody()));
			
			if(pageToGoTo != null){
				if(pageToGoTo.equals("c")){
					res = "successC";	
				}
				else if(pageToGoTo.equals("p")){
					res = "successP";
				}				
			}
		}
		
		return res;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public String getPageToGoTo() {
		return pageToGoTo;
	}

	public void setPageToGoTo(String pageToGoTo) {
		this.pageToGoTo = pageToGoTo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
