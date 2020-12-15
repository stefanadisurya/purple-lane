	package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import controller.LoginController;
import controller.TransactionController;
import controller.UserController;
import core.view.View;

public class TransactionReportView extends View implements ActionListener, ItemListener {

	JMenuBar menuBar;
	JMenu menuHome;
	JMenuItem logout, back;
	JPanel top, mid, bot;
	JScrollPane sp;
	JTable table;
	JButton listBtn;
	JLabel monthLbl;
	JLabel yearLbl;
	JTextField monthTxt, yearTxt;
	static JComboBox<String> monthList;
	private String month = "January";
	private Integer year = 0;
	
	public TransactionReportView() {
		super();
		this.height = 250;
		this.width = 400;
	}

	@Override
	public void initialize() {
		logout = new JMenuItem("Log Out");
		back = new JMenuItem("Back");
		menuHome = new JMenu("Home");
		menuBar = new JMenuBar();
		
		mid = new JPanel(new GridLayout(2,2));
		bot = new JPanel();
		
		monthLbl = new JLabel("Month");
		yearLbl = new JLabel("Year");
		monthTxt = new JTextField();
		yearTxt = new JTextField();
		
		String[] monthData = {
				"January",
				"February",
				"March",
				"April",
				"May",
				"June",
				"July",
				"August",
				"September",
				"October",
				"November",
				"December"
		};
		monthList = new JComboBox<String>(monthData);
		
		listBtn = new JButton("View Report");
	}

	@Override
	public void initializeComponent() {
		menuHome.add(back);
		menuHome.add(logout);
		menuBar.add(menuHome);
		setJMenuBar(menuBar);
		
		JPanel monthPnl = new JPanel();
			monthPnl.add(monthList);
		
		mid.add(monthLbl);
		mid.add(monthPnl);
		mid.add(yearLbl);
		mid.add(yearTxt);
		
		mid.setBorder(new EmptyBorder(30, 40, 30, 40));
		
		bot.add(listBtn);
		
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
		
		addListener();
	}
	
	public void addListener() {
		back.addActionListener(this);
		logout.addActionListener(this);
		listBtn.addActionListener(this);
		monthList.addItemListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == back) {
			this.dispose();
			new ManagerHomeView().showForm();
		} else if(e.getSource() == logout) {
			this.dispose();
			UserController.getInstance().disposeUser();
			LoginController.getInstance().view().showForm();
		} else if(e.getSource() == listBtn) {
			year = Integer.parseInt(yearTxt.getText().toString());
			if(!month.isEmpty() && year != 0) {
				
				TransactionController.getInstance().setMonth(getMonth(month));
				TransactionController.getInstance().setYear(year);

				this.dispose();
				new TransactionReportListView().showForm();
			} else {
				JOptionPane.showMessageDialog(null, "Please Input Month and Year!", "Warning!",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource() ==  monthList) {
			month = monthList.getSelectedItem().toString();
		}
	}
	
	private Integer getMonth(String month) {
		if(month.equals("January"))
			return 1;
		else if(month.equals("February"))
			return 2;
		else if(month.equals("March"))
			return 3;
		else if(month.equals("April"))
			return 4;
		else if(month.equals("May"))
			return 5;
		else if(month.equals("June"))
			return 6;
		else if(month.equals("July"))
			return 7;
		else if(month.equals("August"))
			return 8;
		else if(month.equals("September"))
			return 9;
		else if(month.equals("October"))
			return 10;
		else if(month.equals("November"))
			return 11;
		else if(month.equals("December"))
			return 12;
		else 
			return 0;
	}

}
