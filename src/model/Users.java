
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

	public Users() {
		this.tableName = "users";
	}

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

//	@Override
	public void insert() {
		String query = String.format("" + "INSERT INTO %s VALUES " + "(null, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, roleId);
			ps.setString(2, username);
			ps.setString(3, password);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
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
					} else if (rs.getString("roleId").equals(Integer.toString(4))) { // Promotion
						JOptionPane.showMessageDialog(null, "Login Success!");
//						new PromoController();
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
