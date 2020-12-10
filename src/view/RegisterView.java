package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.AdminController;
import controller.AuthController;
import controller.RegisterController;
import core.view.View;

public class RegisterView extends View implements ActionListener {
	
	JPanel top, mid, bot;
	JLabel titleLbl, usernameLbl, passwordLbl, emailLbl, roleLbl;
	JTextField usernameTxt, passwordTxt, emailTxt;
	JComboBox<String> roleBox;
	JButton cancel, register;
	
	Vector<String> roleList;

	public RegisterView() {
		super();
		this.height = 600;
		this.width = 600;
	}

	@Override
	public void initialize() {
		top = new JPanel(new FlowLayout());
		GridLayout gl = new GridLayout(4, 2);
		gl.setVgap(90);
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
	}

	@Override
	public void initializeComponent() {
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
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cancel) {
			this.dispose();
			AuthController.getInstance().view().showForm();
		} else if(e.getSource() == register) {
			String username = usernameTxt.getText();
			String email = emailTxt.getText();
			String role = roleBox.getSelectedItem().toString();
			String password = passwordTxt.getText();
			
			RegisterController.getInstance().insert(username, email, role, password);
		}
	}

}
