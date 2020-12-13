
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

	JPanel top, mid, midTop,bot,usernameLblPnl, usernameFieldPnl, passwordLblPnl, passwordFieldPnl;
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
		usernameLblPnl = new JPanel();
		usernameFieldPnl = new JPanel();
		passwordLblPnl = new JPanel();
		passwordFieldPnl = new JPanel();
		GridLayout gl = new GridLayout(2, 2);

		gl.setVgap(20);

		mid = new JPanel(gl);
		midTop = new JPanel();
		bot = new JPanel(new FlowLayout());
		titleLbl = new JLabel("Login");
		usernameLbl = new JLabel("Username");
		usernameLblPnl.add(usernameLbl);
		passwordLbl = new JLabel("Password");
		passwordLblPnl.add(passwordLbl);

		usernameTxt = new JTextField();
		usernameTxt.setPreferredSize(new Dimension(150, 30));
		usernameFieldPnl.add(usernameTxt);
		passwordTxt = new JPasswordField();
		passwordTxt.setPreferredSize(new Dimension(150, 30));
		passwordFieldPnl.add(passwordTxt);
		
		cancel = new JButton("Cancel");
		login = new JButton("Login");

		cancel.addActionListener(this);
		login.addActionListener(this);
	}

	@Override
	public void initializeComponent() {
		top.add(titleLbl);

		mid.add(usernameLblPnl);
		mid.add(usernameFieldPnl);
		mid.add(passwordLblPnl);
		mid.add(passwordFieldPnl);
		midTop.add(mid);
		bot.add(cancel);
		bot.add(login);

		mid.setBorder(new EmptyBorder(30, 40, 30, 40));

		add(top, BorderLayout.NORTH);
		add(midTop, BorderLayout.CENTER);
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
