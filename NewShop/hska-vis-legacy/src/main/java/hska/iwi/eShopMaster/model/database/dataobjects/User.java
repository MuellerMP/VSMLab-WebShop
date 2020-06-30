package hska.iwi.eShopMaster.model.database.dataobjects;


import javax.persistence.*;

/**
 * This class contains the users of the webshop.
 */
@Entity
@Table(name = "user")
public class User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;

	
	@Column(name = "username", unique = true, nullable = false)
	private String username;

	
	@Column(name = "name", nullable = false)
	private String firstname;

	
	@Column(name = "lastname", nullable = false)
	private String lastname;

	
	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "roletype")
	private String roletype;
	
	
	@Column(name = "rolelevel")
	private int rolelevel;

	public User() {
	}

	public User(String username, String firstname, String lastname,
			String password, String roletype, int rolelevel) {
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.roletype = roletype;
		this.rolelevel = rolelevel;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoletype() {
		return this.roletype;
	}

	public void setRoletype(String roletype) {
		this.roletype = roletype;
	}

	public int getRolelevel() {
		return this.rolelevel;
	}

	public void setRolelevel(int rolelevel) {
		this.rolelevel = rolelevel;
	}
}
