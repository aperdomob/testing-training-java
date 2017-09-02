package aperdomobo.training.unit_test.model;

import java.util.Objects;

public class User {
	private String email;
	private String username;
	private String password;

	public User() {

	}

	/**
	 * Constructor with parameters
	 *
	 * @param username
	 * @param password
	 * @param email
	 */
	public User(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object other) {
		if (other == null) {
			return false;
		}
			
		if (other == this) {
			return true;
		}
			
		if (!(other instanceof User)) {
			return false;
		}
			
		User otherMyClass = (User) other;
		
		return this.email.equals(otherMyClass.getEmail()) &&
				this.password.equals(otherMyClass.getPassword()) &&
				this.username.equals(otherMyClass.getUsername());
	}
	
	public int hashCode() {
        return Objects.hash(this.email, this.password, this.username);
    }
}
