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
import javax.swing.table.DefaultTableModel;

import controller.ProductController;
import controller.TransactionController;
import core.view.View;
import model.Product;
import model.TransactionDetail;

public class DetailTransactionHistoryMenu extends View implements ActionListener {

	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore;
	JMenu menuBack;
	JPanel top, mid, bot;
	JTable table;
	JLabel titleLbl, priceLbl, priceTxt;
	JScrollPane sp;
	JButton backBtn;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	
	public DetailTransactionHistoryMenu() {
		super();
		this.height = 600;
		this.width = 600;
	}
	
	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		menuBack = new JMenu("Back");
		logout = new JMenuItem("Logout");
		backBtn = new JButton("Back");
		
		top = new JPanel(new GridLayout(1,0));
		mid = new JPanel(new GridLayout(2,0));
		bot = new JPanel();
		
		table = new JTable();
		sp = new JScrollPane(table);
		
		titleLbl = new JLabel("Transaction History");
		priceLbl = new JLabel("Total Price");
		priceTxt = new JLabel("-");
	}

	@Override
	public void initializeComponent() {
		menuMore.add(logout);
		menuBar.add(menuBack);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);
		
		top.add(titleLbl);
		top.add(sp);
		
		mid.add(priceLbl);
		mid.add(priceTxt);
		
		bot.add(backBtn);
		
		backBtn.addActionListener(this);
		logout.addActionListener(this);
		
		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
		
		loadData();
	}
	
	public void loadData() {
		// Tampilin detail transaction
		data = new Vector<>();
		
		header = new Vector<>();
		header.add("Product Id");
		header.add("Product Name");
		header.add("Product Price");
		header.add("Quantity");
		
		TransactionController controller = TransactionController.getInstance();
		
		Integer totalPrice = 0;
		Integer transactionId = controller.getTransactionId();
		Vector<TransactionDetail> detailList = controller.getTransactionDetail(transactionId);
		for (TransactionDetail transactionDetail : detailList) {
			detail = new Vector<>();
			Integer productId = transactionDetail.getProductId();
			Integer qty = transactionDetail.getProductQty();
			
			Product prod = ProductController.getInstance().getOneProduct(productId);
			
			detail.add(productId.toString());
			detail.add(prod.getProductName());
			detail.add(prod.getProductPrice().toString());
			detail.add(qty.toString());
			
			totalPrice += (prod.getProductPrice()*qty);
			
			data.add(detail);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data, header);
		
		priceTxt.setText(totalPrice.toString());
		
		table.setModel(dtm);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == backBtn) {
			this.dispose();
			TransactionController.getInstance().viewTransactionHistoryMenu();
		}
	}
	
}
