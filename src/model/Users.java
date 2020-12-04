package model;

import java.util.Vector;

public class Users {

	public int userId;
	public String username;
	public String email;
	public String role;
	public String password;
	
	public Users(String username, String email, String role, String password) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
		this.password = password;
	}

	public Vector<Object> toObjects() {
		Vector<Object> ret = new Vector<Object>();
		
		ret.add(userId);
		ret.add(username);
		ret.add(email);
		ret.add(password);
		
		return ret;
	}

}
