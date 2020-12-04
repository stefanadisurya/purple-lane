package controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Connect;

public class LoginController extends JFrame implements ActionListener {
	
	JPanel top, mid, bot;
	JLabel titleLbl, usernameLbl, passwordLbl;
	JTextField usernameTxt, passwordTxt;
	JButton cancel, login;
	
	Connect db;
	ResultSet rs;
	
	public void initialize() {
		db = Connect.getInstance();
		
		top = new JPanel(new FlowLayout());
		GridLayout gl = new GridLayout(2, 2);
		gl.setVgap(120);
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
		
		top.add(titleLbl);
		
		mid.add(usernameLbl);
		mid.add(usernameTxt);
		mid.add(passwordLbl);
		mid.add(passwordTxt);
		
		bot.add(cancel);
		bot.add(login);
		
		mid.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
		
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public LoginController() {
		initialize();
	}
	
	void verifyLogin() {
		try {
			rs = db.executeQuery("SELECT * FROM users WHERE username='" + usernameTxt.getText() + "' AND password='" + passwordTxt.getText() + "'");
			if(rs.next()) {
				if(usernameTxt.getText().equals(rs.getString("username")) && passwordTxt.getText().equals(rs.getString("password"))) {
					if(rs.getString("role").equals("Admin")) {
						JOptionPane.showMessageDialog(this, "Login Success!");
						this.dispose();
						new AdminController();
					} else if(rs.getString("role").equals("Customer")) {
						JOptionPane.showMessageDialog(this, "Login Success!");
						this.dispose();
						new CustomerController();
					} else if(rs.getString("role").equals("Manager")) {
						JOptionPane.showMessageDialog(this, "Login Success!");
						this.dispose();
						new ManagerController();
					} else if(rs.getString("role").equals("Promotion")) {
						JOptionPane.showMessageDialog(this, "Login Success!");
						this.dispose();
						new PromoController();
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Wrong username or password!", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancel) {
			this.dispose();
			new AuthController();
		} else if(e.getSource() == login) {
			verifyLogin();
		}
	}

}
