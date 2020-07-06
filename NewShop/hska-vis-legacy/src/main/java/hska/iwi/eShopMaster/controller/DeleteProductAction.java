package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeleteProductAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3666796923937616729L;

	private int id;

	private OAuth2RestTemplate oAuth2RestTemplate = OAuth2Config.getTemplate();
	
	private final String PRODUCT_URL = "http://zuul:8081/products-comp-service/products";

	public String execute() throws Exception {
		
		String res = "input";
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRoletype().equalsIgnoreCase("admin"))) {

			oAuth2RestTemplate.delete(PRODUCT_URL.concat("/"+id));
			{
				res = "success";
			}
		}
		
		return res;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
