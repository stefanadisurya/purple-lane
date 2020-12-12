
package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Users;
import view.RegisterView;

public class RegisterController extends Controller {

	private Users user;
	private static RegisterController controller;
	private boolean valid;
	
	private RegisterController() {
		user = new Users();
		valid = true;
	}

	public static RegisterController getInstance() {
		return controller = (controller == null) ? new RegisterController() : controller;
	}

	@Override
	public View view() {
		return new RegisterView();
	}

	@Override
	public Vector<Model> getAll() {
		return null;
	}
	
	public void setUsername(String username) {
		if(this.user.setUsername(username) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Username column!", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}
	
	public void setRoleId(Integer roleId) {
		if(this.user.setRoleId(roleId) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Role ID column!", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}
	
	public void setPassword(String password) {
		if(this.user.setPassword(password) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Password column!", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public Users newUser() {
		return user;
	}
	
	public Users createAdminAccount(String username, String password) {
		newUser();
		return user.createAdminAccount(username, password);
	}
	
	public Users createCustomerAccount(String username, String password) {
		newUser();
		return user.createCustomerAccount(username, password);
	}
	
	public Users createManagerAccount(String username, String password) {
		newUser();
		return user.createManagerAccount(username, password);
	}
	
	public Users createPromotionTeamAccount(String username, String password) {
		newUser();
		return user.createPromotionTeamAccount(username, password);
	}

}
