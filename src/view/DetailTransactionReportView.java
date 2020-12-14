package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.AuthController;
import controller.ProductController;
import controller.TransactionController;
import controller.UserController;
import core.view.View;
import model.Product;
import model.TransactionDetail;

public class DetailTransactionReportView extends View implements ActionListener {

	JMenuBar menuBar;
	JMenu menuMore;
	JMenuItem logout, home;
	JPanel top, mid, bot;
	JTable table;
	JLabel titleLbl, priceLbl, priceTxt;
	JScrollPane sp;
	JButton backBtn;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	
	public DetailTransactionReportView() {
		super();
		this.height = 600;
		this.width = 600;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("Home");
		home = new JMenuItem("Home");
		logout = new JMenuItem("Logout");
		backBtn = new JButton("Back");
		
		top = new JPanel();
		mid = new JPanel(new GridLayout(0, 2));
		bot = new JPanel();

		table = new JTable();
		sp = new JScrollPane(table);

		titleLbl = new JLabel("Transaction History");
		priceLbl = new JLabel("Total Price");
		priceTxt = new JLabel("-");
	}

	@Override
	public void initializeComponent() {
		menuMore.add(home);
		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);

		top.add(sp);
		mid.add(priceLbl);
		mid.add(priceTxt);
		bot.add(backBtn);
		
		mid.setBorder(new EmptyBorder(10, 50, 10, 10));

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);

		addListener();
		loadData();
	}
	
	public void addListener() {
		backBtn.addActionListener(this);
		home.addActionListener(this);
		logout.addActionListener(this);
	}
	
	public void loadData() {
		data = new Vector<>();

		header = new Vector<>();
		header.add("Product Id");
		header.add("Product Name");
		header.add("Product Price");
		header.add("Quantity");
		header.add("Product Sub Total");

		TransactionController controller = TransactionController.getInstance();

		Integer totalPrice = 0;
		Integer transactionId = controller.getTransactionId();

		Vector<TransactionDetail> detailList = controller.getTransactionDetail(transactionId);
		for (TransactionDetail transactionDetail : detailList) {
			detail = new Vector<>();
			Integer productId = transactionDetail.getProductId();
			Integer qty = transactionDetail.getProductQty();

			Product prod = ProductController.getInstance().getOneProduct(productId);
			Integer subtotal = prod.getProductPrice() * qty;

			detail.add(productId.toString());
			detail.add(prod.getProductName());
			detail.add(prod.getProductPrice().toString());
			detail.add(qty.toString());
			detail.add(subtotal.toString());

			totalPrice += subtotal;

			data.add(detail);
		}

		DefaultTableModel dtm = new DefaultTableModel(data, header);

		priceTxt.setText(totalPrice.toString());

		table.setModel(dtm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == home) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		} else if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		} else if (e.getSource() == backBtn) {
			this.dispose();
			new TransactionReportView().showForm();
		}
	}

}
