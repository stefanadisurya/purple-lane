
package view;

import java.awt.BorderLayout;
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
import controller.AuthController;
import controller.CartController;
import controller.CustomerController;
import controller.ProductController;
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
	JPanel top, mid, bot;
	JTable table;
	JScrollPane sp;
	JMenuBar menuBar;
	JMenu menuMore, menuTransactionHistory;
	JMenuItem logout, cart;
	JButton addCart;
	JLabel nameLbl, authorLbl, priceLbl;
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
		top = new JPanel();
		mid = new JPanel(new GridLayout(4, 2));
		bot = new JPanel();
		table = new JTable();
		sp = new JScrollPane(table);

		table.addMouseListener(this);

		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		menuTransactionHistory = new JMenu("Transaction History");
		
		nameLbl = new JLabel("Product Name: ");
		authorLbl = new JLabel("Product Author: ");
		priceLbl = new JLabel("Product Price: ");
		qtyLbl = new JLabel("Input Quantity: ");
		
		nameTxt = new JLabel("-");
		authorTxt = new JLabel("-");
		priceTxt = new JLabel("-");
		qtyTxt = new JTextField();

		logout = new JMenuItem("Logout");
		cart = new JMenuItem("Cart");

		addCart = new JButton("Add To Cart");

		logout.addActionListener(this);
		cart.addActionListener(this);
		addCart.addActionListener(this);
	}

	@Override
	public void initializeComponent() {

		menuMore.add(cart);
		menuMore.add(logout);
		menuBar.add(menuMore);
		menuBar.add(menuTransactionHistory);
		setJMenuBar(menuBar);

		top.add(sp);
		
		mid.add(nameLbl);
		mid.add(nameTxt);
		mid.add(authorLbl);
		mid.add(authorTxt);
		mid.add(priceLbl);
		mid.add(priceTxt);
		mid.add(qtyLbl);
		mid.add(qtyTxt);
		mid.setBorder(new EmptyBorder(10,50,10,50));
		
		bot.add(addCart);
		bot.setBorder(new EmptyBorder(10,10,10,10));

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
		if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		} else if(e.getSource() == addCart) {
			addToCart();
		} else if(e.getSource() == cart) {
			this.dispose();
			new ManageCartMenuView().showForm();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == table) {
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
		productId=0;
		if (cart == null) {
			JOptionPane.showMessageDialog(this, CartController.getInstance().getErrorMessage());
		} else {
			JOptionPane.showMessageDialog(this, "Add To Cart Successful!");
			loadData();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
