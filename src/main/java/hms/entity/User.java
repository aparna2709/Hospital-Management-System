package hms.entity;  // Package declaration for the hms.entity package

public class User {  // Declaration of the User class
	
	// Fields to represent various attributes of a user
	private String username;
	private String password;
	private String usertype;
	
	
	// Default constructor for the User class
	public User() {
		super();		
	}

	// Constructor with fields for the User class
	public User(String username, String password, String usertype) {
		super();
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}

	// Getter and Setter methods for various attributes of the User class
	
   // Getter method to retrieve the username of the user
	public String getUsername() {
		return username;
	}
	
	// Setter method to set the username of the user
	public void setUsername(String username) {
		this.username = username;
	}
	
	// Getter method to retrieve the password of the user
	public String getPassword() {
		return password;
	}
	
	// Setter method to set the password of the user
	public void setPassword(String password) {
		this.password = password;
	}
	
	// Getter method to retrieve the type of the user
	public String getUsertype() {
		return usertype;
	}
	
	// Setter method to set the type of the user
	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}
	

	// toString method to represent the User object as a string
	@Override
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", usertype=" + usertype + "]";
	}
}





