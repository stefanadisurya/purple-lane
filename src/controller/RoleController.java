package controller;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Role;

public class RoleController {

	public static RoleController controller = null;
	public Role role;

	private RoleController() {
		role = new Role();
	}

	public static RoleController getInstance() {
		if (controller == null) {
			controller = new RoleController();
		}
		return controller;
	}
	
	public Role getOneRole(Integer roleId) {
		//validasi
		return role.getOneRole(roleId);
	}
}
