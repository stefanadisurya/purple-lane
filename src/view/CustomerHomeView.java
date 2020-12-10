package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controller.AdminController;
import controller.AuthController;
import controller.CustomerController;
import core.model.Model;
import core.view.View;
import model.Products;

public class CustomerHomeView extends View implements ActionListener, MouseListener {
	
	JPanel top, mid, bot;
	JTable table;
	JScrollPane sp;
	JMenuBar menuBar;
	JMenu menuMore, menuCart, menuTransactionHistory;
	JMenuItem logout;
	JButton addCart;
	Vector<Vector<String>> data;
	Vector<String> detail, header;
	
	public CustomerHomeView() {
		super();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		top = new JPanel(new GridLayout(6,2));
		bot = new JPanel();
		table = new JTable();
		sp = new JScrollPane(table);
		
		table.addMouseListener(this);
		
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		menuCart = new JMenu("Cart");
		menuTransactionHistory = new JMenu("Transaction History");
		
		logout = new JMenuItem("Logout");
		
		addCart = new JButton("Add To Cart");
		
		table.addMouseListener(this);
		logout.addActionListener(this);
		addCart.addActionListener(this);
		
	}

	@Override
	public void initializeComponent() {
		
		menuMore.add(logout);
		menuBar.add(menuMore);
		menuBar.add(menuCart);
		menuBar.add(menuTransactionHistory);
		setJMenuBar(menuBar);
		
		top.add(sp);
		bot.add(addCart);
		
		add(top, BorderLayout.NORTH);
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
			Products p = (Products) model;
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
		if(e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
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
