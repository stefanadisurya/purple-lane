package view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.AuthController;
import controller.PromoController;
import controller.UserController;
import core.model.Model;
import core.view.View;
import model.Promo;

public class PromoCodeView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu menuMore, menuTransactionHistory, menuPromo;
	JMenuItem logout, cart, viewTransaction, viewPromo, home;
	JPanel top, mid, bot, pnlbottombottom;
	JTable table;
	JButton backBtn;
	JLabel titleLbl;

	public PromoCodeView() {
		super();
		addListeners();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("Home");
		if(UserController.getInstance().getActiveUser().getRoleId()==2) {
			menuPromo = new JMenu("Promo");
			menuTransactionHistory = new JMenu("Transaction History");
			cart = new JMenuItem("My Cart");
			viewTransaction = new JMenuItem("View Transaction History");
			viewPromo = new JMenuItem("View Promo");
			
		}
		home = new JMenuItem("Home");
		logout = new JMenuItem("Logout");

		top = new JPanel();
		titleLbl = new JLabel("View All Promo Code");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mid = new JPanel();
		bot = new JPanel(new BorderLayout());

		pnlbottombottom = new JPanel();

		backBtn = new JButton("Back to Menu");
	}

	@Override
	public void initializeComponent() {
		menuMore.add(home);
		if(UserController.getInstance().getActiveUser().getRoleId()==2) {
			menuMore.add(cart);
			menuPromo.add(viewPromo);
			menuTransactionHistory.add(viewTransaction);
			
		}
		menuMore.add(logout);
		menuBar.add(menuMore);
		if(UserController.getInstance().getActiveUser().getRoleId()==2) {
			menuBar.add(menuPromo);
			menuBar.add(menuTransactionHistory);
			
		}
		setJMenuBar(menuBar);

		top.add(titleLbl);
		loadData();

		mid.add(new JScrollPane(table), BorderLayout.CENTER);

		pnlbottombottom.add(backBtn);
		bot.add(pnlbottombottom, BorderLayout.CENTER);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		home.addActionListener(this);
		logout.addActionListener(this);
		if(UserController.getInstance().getActiveUser().getRoleId()==2) {
			cart.addActionListener(this);
			viewTransaction.addActionListener(this);
			viewPromo.addActionListener(this);	
		}
		
		backBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == home || e.getSource() == backBtn) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		} else if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		} else if (e.getSource() == cart) {
			this.dispose();
			new ManageCartMenuView().showForm();
		} else if (e.getSource() == viewTransaction) {
			this.dispose();
			new TransactionHistoryMenu().showForm();
		} else if (e.getSource() == viewPromo) {
			this.dispose();
			new PromoCodeView().showForm();
		}
	}

	public void loadData() {
		Vector<String> header = new Vector<>();
		header.add("Promo Code");
		header.add("Promo Discount");
		header.add("Promo Note");
		DefaultTableModel dtm = new DefaultTableModel(header, 0);

		Vector<Model> promos = PromoController.getInstance().getAll();
		for (Model model : promos) {
			Promo p = (Promo) model;
			Vector<Object> row = new Vector<>();
			row.add(p.getPromoCode());
			row.add(p.getPromoDiscount().toString());
			row.add(p.getPromoNote());
			dtm.addRow(row);
		}
		table.setModel(dtm);
	}

}
