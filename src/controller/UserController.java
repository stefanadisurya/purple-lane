package controller;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import controller.PromotionTeamController;
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
		if (username.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Username cannot be empty!", "Warning!", JOptionPane.WARNING_MESSAGE);
			return null;
		} else if (password.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Password cannot be empty!", "Warning!", JOptionPane.WARNING_MESSAGE);
			return null;
		}
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
		if (user.getRoleId() == 1) {
			AdminController.getInstance().view().showForm();
		} else if (user.getRoleId() == 2) {
			CustomerController.getInstance().view().showForm();
		} else if (user.getRoleId() == 3) {
			ManagerController.getInstance().view().showForm();
		} else if (user.getRoleId() == 4) {
			PromotionTeamController.getInstance().view().showForm();
		}
	}

	public Users getActiveUser() {
		return activeUser;
	}

	public void setActiveUser(Users activeUser) {
		this.activeUser = activeUser;
	}

	public void disposeUser() {
		this.controller = null;
		this.activeUser = null;
	}

}
