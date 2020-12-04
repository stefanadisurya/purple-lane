package controller;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


public class AuthController extends JFrame implements ActionListener {
	
	JPanel pos;
	JButton regist, login;

	public void initialize() {
		GridLayout grid = new GridLayout(1, 2);
		grid.setVgap(120);
		pos = new JPanel(new FlowLayout());
		
		regist = new JButton("Register");
		login = new JButton("Login");
		
		pos.add(regist);
		pos.add(login);
		
		regist.addActionListener(this);
		login.addActionListener(this);
		
		pos.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		add(pos, BorderLayout.CENTER);
		
		setSize(600, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public AuthController() {
		initialize();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == regist) {
			this.dispose();
			new RegisterController();
		} else if(e.getSource() == login) {
			this.dispose();
			new LoginController();
		}
	}

}
