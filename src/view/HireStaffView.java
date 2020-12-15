
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import controller.LoginController;
import controller.RegisterController;
import controller.UserController;
import core.view.View;
import model.Users;

public class HireStaffView extends View implements ActionListener {

	JPanel top, mid, midTop, rolePnl, bot, usernamePnl, usernameLabelPnl, roleLabelPnl, passwordPnl, passwordLabelPnl;
	JLabel titleLbl, usernameLbl, passwordLbl, roleLbl;
	JTextField usernameTxt, passwordTxt;
	JRadioButton admin, customer, manager, promotion;
	JButton cancel, submit;
	JComboBox roleBox;
	ButtonGroup roleGroup;

	public HireStaffView() {
		super();
		this.height = 250;
		this.width = 500;
	}

	@Override
	public void initialize() {
		top = new JPanel(new FlowLayout());
		usernamePnl = new JPanel();
		usernameLabelPnl = new JPanel();
		passwordPnl = new JPanel();
		passwordLabelPnl = new JPanel();
		roleLabelPnl = new JPanel();
		roleBox = new JComboBox();
		GridLayout gl = new GridLayout(3, 2);
		gl.setVgap(0);
		mid = new JPanel(gl);
		midTop = new JPanel();
		bot = new JPanel(new FlowLayout());

		rolePnl = new JPanel(new GridLayout(4, 1));

		titleLbl = new JLabel("Hire Staff");
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

		roleBox.addItem("Admin");
		roleBox.addItem("Promotion");

		cancel = new JButton("Cancel");
		submit = new JButton("Submit");

		cancel.addActionListener(this);
		submit.addActionListener(this);
	}

	@Override
	public void initializeComponent() {
		top.add(titleLbl);

		mid.add(usernameLabelPnl);
		mid.add(usernamePnl);
		mid.add(passwordLabelPnl);
		mid.add(passwordPnl);
		mid.add(roleLabelPnl);
		mid.add(roleBox);
		midTop.add(mid);
		bot.add(cancel);
		bot.add(submit);

		mid.setBorder(new EmptyBorder(0, 50, 50, 50));

		add(top, BorderLayout.NORTH);
		add(midTop, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String value = (String) roleBox.getSelectedItem();
		if (e.getSource() == cancel) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		} else if (e.getSource() == submit) {
			String username = usernameTxt.getText();
			String password = passwordTxt.getText();
			Users user = null;
			if (value.equals("Admin")) {
				user = RegisterController.getInstance().createAdminAccount(username, password);
			} else if (value.equals("Promotion")) {
				user = RegisterController.getInstance().createPromotionTeamAccount(username, password);
			}
			if (user != null) {
				JOptionPane.showMessageDialog(null, "Register Success!");
				this.dispose();
				UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
			}

		}
	}

}
