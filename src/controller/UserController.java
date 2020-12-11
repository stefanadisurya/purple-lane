package controller;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Users;

public class UserController extends Controller {

	private Users activeUser;
	private static UserController controller;
	
	private UserController() {
		activeUser = new Users();
	}
	
	public static UserController getInstance() {
		return controller = (controller == null) ? new UserController() : controller;
	}

	@Override
	public View view() {
		return null;
	}

	@Override
	public Vector<Model> getAll() {
		return null;
	}
	
	public Users getOneUser(String username, String password) {
		Users s;
		try {
			s = activeUser.getOneUser(username, password);
			return s;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void processRole(Users user) {
		if(user.getRoleId() == 1) {
			AdminController.getInstance().view().showForm();
		} else if(user.getRoleId() == 2) {
			CustomerController.getInstance().view().showForm();
		} else if(user.getRoleId() == 3) {
			new ManagerController();
		} else if(user.getRoleId() == 4) {
			PromoController.getInstance();
		}
	}

	public Users getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(Users activeUser) {
		this.activeUser = activeUser;
	}

}
