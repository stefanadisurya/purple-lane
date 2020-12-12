
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.AuthController;
import controller.LoginController;
import controller.RegisterController;
import core.view.View;

public class LoginView extends View implements ActionListener {

	JPanel top, mid, bot;
	JLabel titleLbl, usernameLbl, passwordLbl;
	JTextField usernameTxt, passwordTxt;
	JButton cancel, login;

	public LoginView() {
		super();
		this.height = 300;
		this.width = 400;
	}

	@Override
	public void initialize() {
		top = new JPanel(new FlowLayout());
		GridLayout gl = new GridLayout(2, 2);
		gl.setVgap(20);
		mid = new JPanel(gl);
		bot = new JPanel(new FlowLayout());

		titleLbl = new JLabel("Login");
		usernameLbl = new JLabel("Username");
		passwordLbl = new JLabel("Password");

		usernameTxt = new JTextField();
		passwordTxt = new JPasswordField();

		cancel = new JButton("Cancel");
		login = new JButton("Login");

		cancel.addActionListener(this);
		login.addActionListener(this);
	}

	@Override
	public void initializeComponent() {
		top.add(titleLbl);

		mid.add(usernameLbl);
		mid.add(usernameTxt);
		mid.add(passwordLbl);
		mid.add(passwordTxt);

		bot.add(cancel);
		bot.add(login);

		mid.setBorder(new EmptyBorder(30, 40, 30, 40));

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancel) {
			this.dispose();
			AuthController.getInstance().view().showForm();
		} else if (e.getSource() == login) {
			String username = usernameTxt.getText();
			String password = passwordTxt.getText();

			LoginController.getInstance().verifyLogin(username, password);
			this.dispose();
		}
	}

}
