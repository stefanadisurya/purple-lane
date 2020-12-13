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

import controller.AuthController;
import controller.TransactionController;
import controller.UserController;
import core.view.View;
import model.Transaction;

public class TransactionHistoryMenu extends View implements ActionListener, MouseListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore;
	JPanel top, mid, bot;
	JTable table;
	JLabel titleLbl;
	JScrollPane sp;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	JButton detailBtn;
	private Integer transactionId = 0;
	
	public TransactionHistoryMenu() {
		super();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");
		
		top = new JPanel();
		mid = new JPanel();
		bot = new JPanel();
		
		table = new JTable();
		sp = new JScrollPane(table);
		
		titleLbl = new JLabel("Transaction History");
		table.addMouseListener(this);
		
		detailBtn = new JButton("View Detail");
	}

	@Override
	public void initializeComponent() {
		
		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);
		
		top.add(titleLbl);
		
		mid.add(sp);
		
		bot.add(detailBtn);
		
		logout.addActionListener(this);
		detailBtn.addActionListener(this);
		
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
		
		for (Transaction transaction : listTransaction) {
			detail = new Vector<>();
			
			detail.add(transaction.getTransactionId().toString());
			detail.add(transaction.getTransactionDate().toString());
			detail.add(transaction.getPaymentType());
			detail.add(transaction.getCardNumber());
			detail.add(transaction.getPromoCode());
			
			data.add(detail);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data, header);
		
		table.setModel(dtm);
	}
	
	public void selectDetail() {
		if(transactionId == 0) {
			JOptionPane.showMessageDialog(null, "Choose the Transaction First!", "Warning!",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		int ans = JOptionPane.showConfirmDialog(this, String.format("View This Detail Product? : %d", transactionId));
		if(ans == JOptionPane.YES_OPTION) {
			TransactionController.getInstance().setTransactionId(transactionId);
			this.dispose();
			new DetailTransactionHistoryMenu().showForm();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == logout) {
			this.dispose();
			new AuthController();
		} else if(e.getSource() == detailBtn) {
			selectDetail();
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
