
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import controller.RegisterController;
import core.view.View;
import model.Users;

public class RegisterView extends View implements ActionListener {

	JPanel top, mid, midTop, bot, rolePnl, usernamePnl, usernameLabelPnl, roleLabelPnl, passwordPnl, passwordLabelPnl;
	JLabel titleLbl, usernameLbl, passwordLbl, roleLbl;
	JTextField usernameTxt, passwordTxt;
	JRadioButton admin, customer, manager, promotion;
	JButton login, register;

	ButtonGroup roleGroup;

	public RegisterView() {
		super();
		this.height = 300;
		this.width = 400;
	}

	@Override
	public void initialize() {
		top = new JPanel(new FlowLayout());
		usernamePnl = new JPanel();
		usernameLabelPnl = new JPanel();
		passwordPnl = new JPanel();
		passwordLabelPnl = new JPanel();
		roleLabelPnl = new JPanel();
		GridLayout gl = new GridLayout(3, 2);
		gl.setVgap(0);
		mid = new JPanel(gl);
		midTop = new JPanel();
		bot = new JPanel(new FlowLayout());

		rolePnl = new JPanel(new GridLayout(4, 1));

		titleLbl = new JLabel("Register");
		usernameLbl = new JLabel("Username");
		usernameLabelPnl.add(usernameLbl);
		roleLbl = new JLabel("Role");
		roleLabelPnl.add(roleLbl);
		passwordLbl = new JLabel("Password");
		passwordLabelPnl.add(passwordLbl);

		usernameTxt = new JTextField();
		usernameTxt.setPreferredSize(new Dimension(150, 30));
		usernamePnl.add(usernameTxt);
		passwordTxt = new JPasswordField();
		passwordTxt.setPreferredSize(new Dimension(150, 30));
		passwordPnl.add(passwordTxt);

		login = new JButton("Login");
		register = new JButton("Submit");

		login.addActionListener(this);
		register.addActionListener(this);
	}

	@Override
	public void initializeComponent() {
		top.add(titleLbl);

		mid.add(usernameLabelPnl);
		mid.add(usernamePnl);
		mid.add(passwordLabelPnl);
		mid.add(passwordPnl);
		midTop.add(mid);
		bot.add(login);
		bot.add(register);

		mid.setBorder(new EmptyBorder(50, 50, 50, 50));

		add(top, BorderLayout.NORTH);
		add(midTop, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			this.dispose();
			LoginController.getInstance().view().showForm();
		} else if (e.getSource() == register) {
			register();
		}
	}

	public void register() {
		String username = usernameTxt.getText();
		String password = passwordTxt.getText();
		Users user = RegisterController.getInstance().createCustomerAccount(username, password);

		if (user != null) {
			JOptionPane.showMessageDialog(null, "Register Success!");
			this.dispose();
			LoginController.getInstance().view().showForm();
		}
	}

}
