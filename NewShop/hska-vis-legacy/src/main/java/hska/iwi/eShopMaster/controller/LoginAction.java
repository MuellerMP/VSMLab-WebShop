package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.database.dataobjects.User;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Service
public class LoginAction extends ActionSupport {

	/**
     *
     */
	private static final long serialVersionUID = -983183915002226000L;
	private String username = null;
	private String password = null;
	private String firstname;
	private String lastname;
	private String role;
	
	private OAuth2RestTemplate oAuth2RestTemplate = OAuth2Config.getTemplate();
	
	private static final String USERS_URL = "http://localhost:8001/users";

	@Override
	public String execute() throws Exception {

		// Return string:
		String result = "input";

		Map<String, Object> session = ActionContext.getContext().getSession();
		User user = oAuth2RestTemplate.getForEntity(USERS_URL.concat("/"+getUsername()), User.class).getBody();
		
		// Does user exist?
		if (user != null) {
			// Is the password correct?
			session.put("webshop_user", user);
			session.put("message", "");
			result = "success";
			//else {
			//	addActionError(getText("error.password.wrong"));
			//}
		}
		else {
			addActionError(getText("error.username.wrong"));
		}

		return result;
	}
	
	@Override
	public void validate() {
		if (getUsername().length() == 0) {
			addActionError(getText("error.username.required"));
		}
		if (getPassword().length() == 0) {
			addActionError(getText("error.password.required"));
		}
	}

	public String getUsername() {
		return (this.username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return (this.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
