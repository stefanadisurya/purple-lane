package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.LoginController;
import controller.TransactionController;
import controller.UserController;
import core.view.View;
import model.Transaction;

public class TransactionReportListView extends View implements ActionListener, MouseListener {

	JPanel top, mid, bot;
	JMenuBar menuBar;
	JMenu menuHome, menuTrans, menuRegister, menuFinancial;
	JMenuItem logout, viewAllTransaction, transReport, hireStaff, home, finance;
	JTable table;
	JScrollPane sp;
	JButton backBtn, detailBtn;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	private Integer transactionId = 0;

	public TransactionReportListView() {
		super();
		this.height = 600;
		this.width = 700;
	}

	@Override
	public void initialize() {
		 top = new JPanel();
		 mid = new JPanel();
		 bot = new JPanel();
		 
		 home = new JMenuItem("Home");
		 logout = new JMenuItem("Log out");
		 transReport = new JMenuItem("Transaction Report");
		 viewAllTransaction = new JMenuItem("View All Transaction");
		 menuTrans = new JMenu("Report");
		 hireStaff = new JMenuItem("Hire Staff");
		 menuRegister = new JMenu("Register");
		 menuHome = new JMenu("Home");
		 
		 menuBar = new JMenuBar();
		 
		 table = new JTable();
		 sp = new JScrollPane(table);
		 
		 backBtn = new JButton("Back");
		 detailBtn = new JButton("View Detail");
	}

	@Override
	public void initializeComponent() {
		
		menuTrans.add(transReport);
		menuTrans.add(viewAllTransaction);
		menuRegister.add(hireStaff);
		menuHome.add(home);
		menuHome.add(logout);
		menuBar.add(menuHome);
		menuBar.add(menuTrans);
		menuBar.add(menuRegister);
		setJMenuBar(menuBar);

		top.add(sp);
		bot.add(backBtn);
		bot.add(detailBtn);

		add(top, BorderLayout.NORTH);
		add(bot, BorderLayout.SOUTH);

		addListener();
		loadData();
	}

	public void addListener() {
		backBtn.addActionListener(this);
		home.addActionListener(this);
		logout.addActionListener(this);
		table.addMouseListener(this);
		detailBtn.addActionListener(this);
		hireStaff.addActionListener(this);
		transReport.addActionListener(this);
		viewAllTransaction.addActionListener(this);
	}

	public void loadData() {
		data = new Vector<>();

		header = new Vector<>();
		header.add("Transaction Id");
		header.add("Transaction Date");
		header.add("Payment Type");
		header.add("Card Number");
		header.add("Promo Code");

		TransactionController tcontroller = TransactionController.getInstance();
		Integer year = tcontroller.getYear();
		Integer month = tcontroller.getMonth();
		Vector<Transaction> transactionList = tcontroller.getTransactionReport(month, year);

		 if (transactionList != null) {
			for (Transaction transaction : transactionList) {
				detail = new Vector<>();

				detail.add(transaction.getTransactionId().toString());
				detail.add(transaction.getTransactionDate().toString());
				detail.add(transaction.getPaymentType());
				detail.add(transaction.getCardNumber());
				detail.add(transaction.getPromoCode());

				data.add(detail);
			}
		}
		DefaultTableModel dtm = new DefaultTableModel(data, header);

		table.setModel(dtm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			this.dispose();
			new TransactionReportView().showForm();
		} else if (e.getSource() == logout) {
			this.dispose();
			UserController.getInstance().disposeUser();
			LoginController.getInstance().view().showForm();
		} else if (e.getSource() == detailBtn) {
			selectDetail();
		} else if(e.getSource() == hireStaff) {
			this.dispose();
			new HireStaffView().showForm();
		} else if(e.getSource() == transReport) {
			this.dispose();
			new TransactionReportView().showForm();
		} else if(e.getSource() == home || e.getSource() == viewAllTransaction) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		}
	}

	public void selectDetail() {
		if (transactionId == 0) {
			JOptionPane.showMessageDialog(null, "Choose the Transaction First!", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int ans = JOptionPane.showConfirmDialog(this, String.format("View This Detail Product? : %d", transactionId));
		if (ans == JOptionPane.YES_OPTION) {
			TransactionController.getInstance().setTransactionId(transactionId);
			this.dispose();
			new DetailTransactionReportView().showForm();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		String id = (String) table.getValueAt(row, 0);

		transactionId = Integer.parseInt(id);
	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

}
