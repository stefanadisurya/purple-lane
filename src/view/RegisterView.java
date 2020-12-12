
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.AdminController;
import controller.AuthController;
import controller.LoginController;
import controller.RegisterController;
import core.view.View;

public class RegisterView extends View implements ActionListener {

	JPanel top, mid, bot, rolePnl;
	JLabel titleLbl, usernameLbl, passwordLbl, roleLbl;
	JTextField usernameTxt, passwordTxt;
	JRadioButton admin, customer, manager, promotion;
	JButton cancel, register;

	ButtonGroup roleGroup;

	public RegisterView() {
		super();
		this.height = 600;
		this.width = 500;
	}

	@Override
	public void initialize() {
		top = new JPanel(new FlowLayout());
		GridLayout gl = new GridLayout(3, 2);
		gl.setVgap(100);
		mid = new JPanel(gl);
		bot = new JPanel(new FlowLayout());
		rolePnl = new JPanel();

		titleLbl = new JLabel("Register");
		usernameLbl = new JLabel("Username");
		roleLbl = new JLabel("Role");
		passwordLbl = new JLabel("Password");

		usernameTxt = new JTextField();
		passwordTxt = new JPasswordField();

		admin = new JRadioButton("1");
		admin.setActionCommand("1");
		customer = new JRadioButton("2");
		customer.setActionCommand("2");
		manager = new JRadioButton("3");
		manager.setActionCommand("3");
		promotion = new JRadioButton("4");
		promotion.setActionCommand("4");
		rolePnl.add(admin);
		rolePnl.add(customer);
		rolePnl.add(manager);
		rolePnl.add(promotion);
		roleGroup = new ButtonGroup();
		roleGroup.add(admin);
		roleGroup.add(customer);
		roleGroup.add(manager);
		roleGroup.add(promotion);

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
		mid.add(roleLbl);
		mid.add(rolePnl);
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
		if (e.getSource() == cancel) {
			this.dispose();
			AuthController.getInstance().view().showForm();
		} else if (e.getSource() == register) {
			String username = usernameTxt.getText();
//			Integer roleId = Integer.parseInt(roleGroup.getSelection().getActionCommand());
			String password = passwordTxt.getText();
			if(roleGroup.getSelection().getActionCommand().equals(Integer.toString(1))) {
				RegisterController.getInstance().createAdminAccount(username, password);
			} else if(roleGroup.getSelection().getActionCommand().equals(Integer.toString(2))) {
				RegisterController.getInstance().createCustomerAccount(username, password);
			} else if(roleGroup.getSelection().getActionCommand().equals(Integer.toString(3))) {
				RegisterController.getInstance().createManagerAccount(username, password);
			} else if(roleGroup.getSelection().getActionCommand().equals(Integer.toString(4))) {
				RegisterController.getInstance().createPromotionTeamAccount(username, password);
			}
			
			JOptionPane.showMessageDialog(null, "Register Success!");
			this.dispose();
			LoginController.getInstance().view().showForm();
		}
	}

}
