package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.database.dataAccessObjects.ProductDAO;
import hska.iwi.eShopMaster.model.database.dataobjects.Category;
import hska.iwi.eShopMaster.model.database.dataobjects.Product;
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
	
	private final String PRODUCT_URL = "http://zuul:8081/products-service/products";
	private final String GET_CATEGORIES_URL = "http://zuul:8081/categories-service/categories";

	public String execute() throws Exception {
		
		String res = "input";
		OAuth2RestTemplate oAuth2RestTemplate = OAuth2Config.getTemplate();
		
		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = (User) session.get("webshop_user");
		
		if(user != null && (user.getRoletype().equalsIgnoreCase("admin"))) {
			try {
				Product product = oAuth2RestTemplate.getForEntity(PRODUCT_URL.concat("/"+id),Product.class).getBody();
				Category cat = oAuth2RestTemplate.getForEntity(GET_CATEGORIES_URL.concat("/"+product.getCategoryId()),Category.class).getBody();
				if(cat.getProductIds().contains(","+product.getCategoryId())) {
					cat.setProductIds(cat.getProductIds().replaceAll(","+product.getCategoryId(), ""));
				} else {
					cat.setProductIds(null);
				}
				
				oAuth2RestTemplate.delete(PRODUCT_URL.concat("/"+id));
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
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
