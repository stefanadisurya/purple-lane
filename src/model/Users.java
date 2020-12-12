
package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import connect.Connect;
import controller.AdminController;
import controller.CustomerController;
import controller.LoginController;
import controller.ManagerController;
import controller.PromoController;
import core.model.Model;

public class Users extends Model {

	private Integer userId;
	private String username;
	private Integer roleId;
	private String password;

	Connect db;
	ResultSet rs;
	
	private Users(Integer userId, String username, Integer roleId, String password) {
		super();
		this.tableName = "users";
		this.userId = userId;
		this.username = username;
		this.roleId = roleId;
		this.password = password;
	}

	public Users() {
		this.tableName = "users";
	}

	public Integer getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Users getOneUser(String username, String password) throws SQLException {
		db = Connect.getConnection();
		
		String query = String.format("SELECT * FROM %s WHERE username='%s' AND password='%s'", tableName, username, password);
		rs = db.executeQuery(query);
		
		Users u = map(rs);
		return u;
		
	}
	
	public Users createCustomerAccount(String username, String password) {
		String query = String.format("INSERT INTO %s VALUES(null,?,?,?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, 2);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
			return new Users(null, username, 2, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Users createAdminAccount(String username, String password) {
		String query = String.format("INSERT INTO %s VALUES(null,?,?,?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, 1);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
			return new Users(null, username, 1, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Users createPromotionTeamAccount(String username, String password) {
		String query = String.format("INSERT INTO %s VALUES(null,?,?,?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);
		
		try {
			ps.setInt(1, 3);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
			return new Users(null, username, 3, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Users map(ResultSet rs) {
		try {
			if(rs.next()) {
				Integer userId = rs.getInt("userId");
				String username = rs.getString("username");
				Integer roleId = rs.getInt("roleId");
				String password = rs.getString("password");
				return new Users(userId, username, roleId, password);
			} else {
				JOptionPane.showMessageDialog(null, "Wrong username or password!", "Warning!",
						JOptionPane.WARNING_MESSAGE);
				LoginController.getInstance().view().showForm();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return null;
	}

	public Users insert() {
		String query = String.format("" + "INSERT INTO %s VALUES " + "(null, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, roleId);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
			return new Users(null, username, roleId, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}


	@Override
	public Vector<Model> getAll() {
		return null;
	}

	public void verifyLogin() {
		db = Connect.getConnection();

		try {
			rs = db.executeQuery(
					"SELECT * FROM users WHERE username='" + username + "' AND password='" + password + "'");
			if (rs.next()) {
				if (username.equals(rs.getString("username")) && password.equals(rs.getString("password"))) {					
					if (rs.getString("roleId").equals(Integer.toString(1))) { // Admin
						JOptionPane.showMessageDialog(null, "Login Success!");
						AdminController.getInstance().view().showForm();
					} else if (rs.getString("roleId").equals(Integer.toString(2))) { // Member/Customer
						JOptionPane.showMessageDialog(null, "Login Success!");
						CustomerController.getInstance().view().showForm();
					} else if (rs.getString("roleId").equals(Integer.toString(3))) { // Manager
						JOptionPane.showMessageDialog(null, "Login Success!");
						new ManagerController();
					} else if (rs.getString("roleId").equals(Integer.toString(4))) { // Promotion Team
						JOptionPane.showMessageDialog(null, "Login Success!");
						PromoController.getInstance();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Wrong username or password!", "Warning!",
						JOptionPane.WARNING_MESSAGE);
				LoginController.getInstance().view().showForm();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
