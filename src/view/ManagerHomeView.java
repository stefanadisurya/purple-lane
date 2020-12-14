package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.AuthController;
import controller.TransactionController;
import controller.UserController;
import core.model.Model;
import core.view.View;
import model.Transaction;

public class ManagerHomeView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu menuMore, menuTrans, menuRegister, menuFinancial;
	JMenuItem logout, viewAllTransaction, transReport, hireStaff, home, finance;
	JPanel top, mid, bot, pnlbottombottom;
	JTable table;
	JButton TransReportBtn;
	JLabel titleLbl;
	JScrollPane sp;
	private Integer transactionId = 0;

	public ManagerHomeView() {
		super();
		addListeners();
		this.height = 700;
		this.width = 1000;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("Home");
		menuTrans = new JMenu("Report");
		menuRegister = new JMenu("Register");
		home = new JMenuItem("Home");
		logout = new JMenuItem("Logout");
		viewAllTransaction = new JMenuItem("Get All Transaction");
		transReport = new JMenuItem("Transaction Report");
		hireStaff = new JMenuItem("Hire Staff");

		top = new JPanel();
		titleLbl = new JLabel("List All Transaction");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		
		sp = new JScrollPane(table);
		sp.setPreferredSize(new Dimension(800, 450));
		
		mid = new JPanel();
		bot = new JPanel(new BorderLayout());

		pnlbottombottom = new JPanel();

		TransReportBtn = new JButton("Transaction Report");
	}

	@Override
	public void initializeComponent() {
		menuMore.add(home);
		menuMore.add(logout);
		menuRegister.add(hireStaff);
		menuTrans.add(viewAllTransaction);
		menuTrans.add(transReport);
		menuBar.add(menuMore);
		menuBar.add(menuTrans);
		menuBar.add(menuRegister);
		setJMenuBar(menuBar);

		top.add(titleLbl);
		loadData();

		mid.add(sp, BorderLayout.CENTER);
		
		pnlbottombottom.add(TransReportBtn);
		bot.add(pnlbottombottom, BorderLayout.CENTER);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		home.addActionListener(this);
		logout.addActionListener(this);
		hireStaff.addActionListener(this);
		viewAllTransaction.addActionListener(this);
		transReport.addActionListener(this);
		TransReportBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == home || e.getSource() == viewAllTransaction) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		} else if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		} else if (e.getSource() == hireStaff) {
			this.dispose();
			new HireStaffView().showForm();
		} else if (e.getSource() == TransReportBtn || e.getSource() == transReport) {
			this.dispose();
			new TransactionReportView().showForm();
		}
	}

	public void loadData() {
		Vector<String> header = new Vector<>();
		header.add("Transaction Id");
		header.add("Transaction Date");
		header.add("Product Name");
		header.add("User Name");
		header.add("Product Quantity");
		header.add("Payment Type");
		header.add("Promo Code");
		DefaultTableModel dtm = new DefaultTableModel(header, 0);
		Vector<Model> transaction = TransactionController.getInstance().getAll();
		if (transaction != null) {
			for (Model model : transaction) {
				Transaction p = (Transaction) model;
				Vector<Object> row = new Vector<>();
				row.add(p.getTransactionId());
				row.add(p.getTransactionDate());
				row.add(p.getTransactionId());
				row.add(p.getUserId());
				row.add(p.getTransactionId());
				row.add(p.getPaymentType());
				row.add(p.getPromoCode());
				dtm.addRow(row);
			}
		}
		table.setModel(dtm);
	}

}
