package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import connect.Connect;
import controller.AdminController;
import controller.CustomerController;
import controller.ManagerController;
import controller.PromoController;
import core.model.Model;

public class Users extends Model {

	private Integer userId;
	private String username;
	private String email;
	private String role;
	private String password;
	
	Connect db;
	ResultSet rs;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Users() {
		this.tableName = "users";
	}

	@Override
	public void insert() {
		String query = String.format("" + "INSERT INTO %s VALUES " + "(null, ?, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setString(1, username);
			ps.setString(2, email);
			ps.setString(3, role);
			ps.setString(4, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update() {
		
	}

	@Override
	public void delete() {
		
	}

	@Override
	public Vector<Model> getAll() {
		return null;
	}
	
	public void verifyLogin() {
		db = Connect.getConnection();
		
		try {
		   rs = db.executeQuery("SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'");
		   if(rs.next()) {
			   if(username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {
				   if(rs.getString("role").equals("Admin")) {
					   JOptionPane.showMessageDialog(null, "Login Success!");
					   AdminController.getInstance().view().showForm();
				   } else if(rs.getString("role").equals("Customer")) {
					   JOptionPane.showMessageDialog(null, "Login Success!");
					   CustomerController.getInstance().view().showForm();
				   } else if(rs.getString("role").equals("Manager")) {
					   JOptionPane.showMessageDialog(null, "Login Success!");
					   new ManagerController();
				   } else if(rs.getString("role").equals("Promotion")) {
					   JOptionPane.showMessageDialog(null, "Login Success!");
					   new PromoController();
				   }
			   }
		   } else {
			   		JOptionPane.showMessageDialog(null, "Wrong username or password!", "Warning!", JOptionPane.WARNING_MESSAGE);
		   }
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
