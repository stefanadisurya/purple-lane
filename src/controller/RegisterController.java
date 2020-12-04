package controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Connect;
import model.Users;

public class RegisterController extends JFrame implements ActionListener {
	
	JPanel top, mid, bot;
	JLabel titleLbl, usernameLbl, passwordLbl, emailLbl, roleLbl;
	JTextField usernameTxt, passwordTxt, emailTxt;
	JComboBox<String> roleBox;
	JButton cancel, register;
	
	Vector<String> roleList;
	
	Connect con;
	
	public void initialize() {
		con = Connect.getInstance();
		
		top = new JPanel(new FlowLayout());
		GridLayout gl = new GridLayout(4, 2);
		gl.setVgap(30);
		mid = new JPanel(gl);
		bot = new JPanel(new FlowLayout());
		
		titleLbl = new JLabel("Register");
		usernameLbl = new JLabel("Username");
		emailLbl = new JLabel("Email");
		roleLbl = new JLabel("Role");
		passwordLbl = new JLabel("Password");
		
		usernameTxt = new JTextField();
		emailTxt = new JTextField();
		passwordTxt = new JPasswordField();
		
		roleList = new Vector<>();
		roleList.add("Customer");
		roleList.add("Manager");
		roleList.add("Admin");
		roleList.add("Promotion");
		
		roleBox = new JComboBox<>(roleList);
		
		cancel = new JButton("Cancel");
		register = new JButton("Register");
		
		cancel.addActionListener(this);
		register.addActionListener(this);
		
		top.add(titleLbl);
		
		mid.add(usernameLbl);
		mid.add(usernameTxt);
		mid.add(emailLbl);
		mid.add(emailTxt);
		mid.add(roleLbl);
		mid.add(roleBox);
		mid.add(passwordLbl);
		mid.add(passwordTxt);
		
		bot.add(cancel);
		bot.add(register);
		
		mid.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
		
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public RegisterController() {
		initialize();
	}
	
	Users getData() {
		String username = usernameTxt.getText();
		String email = emailTxt.getText();
		String role = roleBox.getSelectedItem().toString();
		String password = passwordTxt.getText();
		
		return new Users(username, email, role, password);
	}
	
	void insert() {
		Users user = getData();
		
		if(usernameTxt.getText().isEmpty() == true) {
			JOptionPane.showMessageDialog(this, "Please fill the username column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else if(emailTxt.getText().isEmpty() == true) {
			JOptionPane.showMessageDialog(this, "Please fill the email column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else if(passwordTxt.getText().isEmpty() == true) {
			JOptionPane.showMessageDialog(this, "Please fill the password column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else if(usernameTxt.getText().length() < 5) {
			JOptionPane.showMessageDialog(this, "Username min. 5 characters!", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else if(passwordTxt.getText().length() < 6) {
			JOptionPane.showMessageDialog(this, "Password min. 6 characters!", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else if(usernameTxt.getText().length() > 20) {
			JOptionPane.showMessageDialog(this, "Username max. 20 characters!", "Warning!", JOptionPane.WARNING_MESSAGE);
		} else {
			String query = String.format("" + "INSERT INTO users(username, email, role, password) VALUES " + "('%s', '%s', '%s', '%s')", user.username, user.email, user.role, user.password);
			con.executeUpdate(query);
			JOptionPane.showMessageDialog(this, "Registration success!");
			this.dispose();
			new LoginController();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancel) {
			this.dispose();
			new AuthController();
		} else if(e.getSource() == register) {
			insert();
		}
	}

}
