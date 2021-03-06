
package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import controller.CartController;
import controller.LoginController;
import controller.UserController;
import core.model.Model;
import core.view.View;
import model.Cart;
import model.Product;
import model.Users;

public class CustomerHomeView extends View implements ActionListener, MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel top, mid, bot, welcomePnl;
	JTable table;
	JScrollPane sp;
	JMenuBar menuBar;
	JMenu menuMore, menuTransactionHistory, menuPromo;
	JMenuItem logout, cart, viewTransaction, viewPromo, home;
	JButton addCart;
	JLabel nameLbl, authorLbl, priceLbl, titleLbl;
	JLabel nameTxt, authorTxt, priceTxt;
	JLabel qtyLbl;
	JTextField qtyTxt;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	Integer productId = 0;

	public CustomerHomeView() {
		super();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		top = new JPanel(new BorderLayout());
		mid = new JPanel(new GridLayout(4, 2));
		bot = new JPanel();
		welcomePnl = new JPanel(new FlowLayout());
		table = new JTable();
		titleLbl = new JLabel("Welcome " + UserController.getInstance().getActiveUser().getUsername());
		sp = new JScrollPane(table);

		table.addMouseListener(this);

		menuBar = new JMenuBar();
		menuMore = new JMenu("Home");
		menuPromo = new JMenu("Promo");
		menuTransactionHistory = new JMenu("Transaction History");

		nameLbl = new JLabel("Product Name: ");
		authorLbl = new JLabel("Product Author: ");
		priceLbl = new JLabel("Product Price: ");
		qtyLbl = new JLabel("Input Quantity: ");

		nameTxt = new JLabel("-");
		authorTxt = new JLabel("-");
		priceTxt = new JLabel("-");
		qtyTxt = new JTextField();

		home = new JMenuItem("Home");
		logout = new JMenuItem("Logout");
		cart = new JMenuItem("My Cart");
		viewTransaction = new JMenuItem("View Transaction History");
		viewPromo = new JMenuItem("View Promo");
		addCart = new JButton("Add To Cart");

		home.addActionListener(this);
		logout.addActionListener(this);
		cart.addActionListener(this);
		addCart.addActionListener(this);
		viewTransaction.addActionListener(this);
		viewPromo.addActionListener(this);
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

		welcomePnl.add(titleLbl);
		top.add(welcomePnl, BorderLayout.NORTH);
		top.add(sp, BorderLayout.CENTER);
		top.setBorder(new EmptyBorder(10, 10, 10, 10));

		mid.add(nameLbl);
		mid.add(nameTxt);
		mid.add(authorLbl);
		mid.add(authorTxt);
		mid.add(priceLbl);
		mid.add(priceTxt);
		mid.add(qtyLbl);
		mid.add(qtyTxt);
		mid.setBorder(new EmptyBorder(10, 50, 10, 50));

		bot.add(addCart);
		bot.setBorder(new EmptyBorder(10, 10, 10, 10));

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);

		loadData();
	}

	public void loadData() {
		data = new Vector<>();

		header = new Vector<>();
		header.add("Product ID");
		header.add("Product Name");
		header.add("Product Author");
		header.add("Product Price");
		header.add("Product Stock");

		Vector<Model> listProduct = AdminController.getInstance().getAll();

		for (Model model : listProduct) {
			Product p = (Product) model;
			detail = new Vector<>();

			detail.add(p.getProductId().toString());
			detail.add(p.getProductName());
			detail.add(p.getProductAuthor());
			detail.add(p.getProductPrice().toString());
			detail.add(p.getProductStock().toString());

			data.add(detail);
		}

		DefaultTableModel dtm = new DefaultTableModel(data, header);

		table.setModel(dtm);
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
		} else if (e.getSource() == addCart) {
			addToCart();
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

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			int row = table.getSelectedRow();
			productId = Integer.parseInt(table.getValueAt(row, 0).toString());
			nameTxt.setText(table.getValueAt(row, 1).toString());
			authorTxt.setText(table.getValueAt(row, 2).toString());
			priceTxt.setText(table.getValueAt(row, 3).toString());
		}
	}

	private void addToCart() {
		String qty = qtyTxt.getText();

		Users u = UserController.getInstance().getActiveUser();
		Cart cart;
		cart = CartController.getInstance().addToCart(u.getUserId().toString(), productId.toString(), qty);
		nameTxt.setText("");
		authorTxt.setText("");
		priceTxt.setText("");
		qtyTxt.setText("");
		productId = 0;
		if (cart == null) {
			JOptionPane.showMessageDialog(this, CartController.getInstance().getErrorMessage());
		} else {
			JOptionPane.showMessageDialog(this, "Add To Cart Successful!");
			loadData();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

}
