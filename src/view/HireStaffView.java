
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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

public class HireStaffView extends View implements ActionListener {

	JPanel top, mid, midTop,rolePnl, bot, usernamePnl,usernameLabelPnl, roleLabelPnl, passwordPnl, passwordLabelPnl;
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
		
		rolePnl = new JPanel(new GridLayout(4,1));

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

//		admin = new JRadioButton("Admin");
//		admin.setActionCommand("1");
//		customer = new JRadioButton("Customer");
//		customer.setActionCommand("2");
//		manager = new JRadioButton("Manager");
//		manager.setActionCommand("3");
//		promotion = new JRadioButton("Promotion");
//		promotion.setActionCommand("4");
//		rolePnl.add(admin);
//		rolePnl.add(customer);
//		rolePnl.add(manager);
//		rolePnl.add(promotion);
//		roleGroup = new ButtonGroup();
//		roleGroup.add(admin);
//		roleGroup.add(customer);
//		roleGroup.add(manager);
//		roleGroup.add(promotion);
		
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
		String value =(String)roleBox.getSelectedItem();
		if (e.getSource() == cancel) {
			this.dispose();
			AuthController.getInstance().view().showForm();
		} else if (e.getSource() == submit) {
			String username = usernameTxt.getText();
//			Integer roleId = Integer.parseInt(roleGroup.getSelection().getActionCommand());
			String password = passwordTxt.getText();
			if(value.equals("Admin")) {
				RegisterController.getInstance().createAdminAccount(username, password);
			} else if(value.equals("Promotion")) {
				RegisterController.getInstance().createPromotionTeamAccount(username, password);
			}
			
			JOptionPane.showMessageDialog(null, "Register Success!");
			this.dispose();
			LoginController.getInstance().view().showForm();
		}
	}

}
