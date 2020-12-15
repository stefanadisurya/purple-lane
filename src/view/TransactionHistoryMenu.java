package view;

import java.awt.BorderLayout;
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

import controller.LoginController;
import controller.TransactionController;
import controller.UserController;
import core.view.View;
import model.Transaction;

public class TransactionHistoryMenu extends View implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenu menuMore, menuTransactionHistory, menuPromo;
	JMenuItem logout, cart, viewTransaction, viewPromo, home;
	JPanel top, mid, bot;
	JTable table;
	JLabel titleLbl;
	JScrollPane sp;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	JButton detailBtn, backBtn;
	private Integer transactionId = 0;

	public TransactionHistoryMenu() {
		super();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("Home");
		menuPromo = new JMenu("Promo");
		menuTransactionHistory = new JMenu("Transaction History");
		home = new JMenuItem("Home");
		logout = new JMenuItem("Logout");
		cart = new JMenuItem("My Cart");
		viewTransaction = new JMenuItem("View Transaction History");
		viewPromo = new JMenuItem("View Promo");

		top = new JPanel();
		mid = new JPanel();
		bot = new JPanel();

		table = new JTable();
		sp = new JScrollPane(table);

		titleLbl = new JLabel("Transaction History");
		table.addMouseListener(this);

		detailBtn = new JButton("View Detail");
		backBtn = new JButton("Back");
	}

	@Override
	public void initializeComponent() {
		menuMore.add(home);
		menuMore.add(cart);
		menuMore.add(logout);
		menuPromo.add(viewPromo);
		menuTransactionHistory.add(viewTransaction);
		menuBar.add(menuMore);
		menuBar.add(menuPromo);
		menuBar.add(menuTransactionHistory);
		setJMenuBar(menuBar);

		top.add(titleLbl);

		mid.add(sp);

		bot.add(backBtn);
		bot.add(detailBtn);

		home.addActionListener(this);
		logout.addActionListener(this);
		cart.addActionListener(this);
		viewTransaction.addActionListener(this);
		viewPromo.addActionListener(this);
		detailBtn.addActionListener(this);
		backBtn.addActionListener(this);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);

		loadData();
	}

	private void loadData() {
		data = new Vector<>();

		header = new Vector<>();
		header.add("Transaction Id");
		header.add("Transaction Date");
		header.add("Payment Type");
		header.add("Card Number");
		header.add("Promo Code");

		Integer userId = UserController.getInstance().getActiveUser().getUserId();
		Vector<Transaction> listTransaction = TransactionController.getInstance().getTransactionHistory(userId);

		if (listTransaction != null) {
			for (Transaction transaction : listTransaction) {
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
			new DetailTransactionHistoryMenu().showForm();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == home) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		} else if (e.getSource() == logout) {
			this.dispose();
			UserController.getInstance().disposeUser();
			LoginController.getInstance().view().showForm();
		} else if (e.getSource() == cart) {
			this.dispose();
			new ManageCartMenuView().showForm();
		} else if (e.getSource() == viewTransaction) {
			this.dispose();
			new TransactionHistoryMenu().showForm();
		} else if (e.getSource() == viewPromo) {
			this.dispose();
			new PromoCodeView().showForm();
		} else if (e.getSource() == detailBtn) {
			selectDetail();
		} else if (e.getSource() == backBtn) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
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
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
