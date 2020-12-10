package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class ManagerController extends JFrame implements ActionListener {
	
	JMenuBar menuBar;
	JMenu menuMore;
	JMenuItem logout;
	
	void initializeComponent() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");
		
		logout.addActionListener(this);
		
		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);
	}
	
	public void initialize() {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		initializeComponent();
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

	public ManagerController() {
		initialize();
	}

}
