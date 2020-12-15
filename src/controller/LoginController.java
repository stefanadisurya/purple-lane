
package controller;

import java.util.Vector;

import javax.swing.JOptionPane;
import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Users;
import view.LoginView;

public class LoginController extends Controller {

	private static LoginController controller;

	public static LoginController getInstance() {
		return controller = (controller == null) ? new LoginController() : controller;
	}

	@Override
	public View view() {
		return new LoginView();
	}

	@Override
	public Vector<Model> getAll() {
		return null;
	}

	public void verifyLogin(String username, String password) {
		UserController c = UserController.getInstance();
		Users u = c.getOneUser(username, password);
		if (u != null) {
			c.setActiveUser(u);
			JOptionPane.showMessageDialog(null, "Login Success!");
			UserController.getInstance().processRole(u);
		} else {

			LoginController.getInstance().view().showForm();
		}
	}
}