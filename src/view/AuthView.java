package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import controller.RegisterController;
import core.view.View;

public class AuthView extends View implements ActionListener {
	
	JPanel pos;
	JButton register, login;

	public AuthView() {
		super();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		GridLayout grid = new GridLayout(1, 2);
		grid.setVgap(120);
		pos = new JPanel(new FlowLayout());
		
		register = new JButton("Register");
		login = new JButton("Login");
		
		register.addActionListener(this);
		login.addActionListener(this);
	}

	@Override
	public void initializeComponent() {
		pos.add(register);
		pos.add(login);
		
		pos.setBorder(new EmptyBorder(50, 50, 50, 50));
		
		add(pos, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == register) {
		   this.dispose();
		   RegisterController.getInstance().view().showForm();
		} else if(e.getSource() == login) {
		   this.dispose();
		   LoginController.getInstance().view().showForm();
		}
	}

}
