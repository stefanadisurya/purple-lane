
package controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import connect.Connect;
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
		if(u != null) {
			c.setActiveUser(u);
			JOptionPane.showMessageDialog(null, "Login Success!");
			UserController.getInstance().processRole(u);
		}
	}
}