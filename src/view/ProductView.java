
package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import controller.LoginController;
import controller.ProductController;
import controller.UserController;
import core.model.Model;
import core.view.View;
import model.Product;

public class ProductView extends View implements ActionListener, MouseListener {

	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore;
	JPanel top, mid, bot;
	JTable table;
	JScrollPane sp;
	JLabel idLbl, idValue, nameLbl, authorLbl, priceLbl, stockLbl, searchLbl, lbl;
	JTextField nameTxt, authorTxt, priceTxt, stockTxt, searchTxt;
	JButton insert, update, delete, search;
	Vector<Vector<String>> data;
	Vector<String> detail, header;

	public ProductView() {
		super();
		this.height = 800;
		this.width = 600;
	}

	@Override
	public void initialize() {
		top = new JPanel();
		mid = new JPanel(new GridLayout(7, 2, 4, 4));
		bot = new JPanel();
		table = new JTable();
		sp = new JScrollPane(table);

		table.addMouseListener(this);

		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");

		searchLbl = new JLabel("Search Product: ");
		searchTxt = new JTextField();

		idLbl = new JLabel("Product ID: ");
		nameLbl = new JLabel("Product Name: ");
		authorLbl = new JLabel("Product Author: ");
		priceLbl = new JLabel("Product Price: ");
		stockLbl = new JLabel("Product Stock: ");

		idValue = new JLabel();

		nameTxt = new JTextField();
		authorTxt = new JTextField();
		priceTxt = new JTextField();
		stockTxt = new JTextField();
		insert = new JButton("Insert");
		update = new JButton("Update");
		delete = new JButton("Delete");
		search = new JButton("Search");
		search.addActionListener(this);
		lbl = new JLabel();

		insert.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		logout.addActionListener(this);

	}

	@Override
	public void initializeComponent() {

		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);

		top.add(sp);

		mid.add(searchLbl);
		mid.add(searchTxt);
		mid.add(lbl);
		mid.add(search);
		mid.add(idLbl);
		mid.add(idValue);
		mid.add(nameLbl);
		mid.add(nameTxt);
		mid.add(authorLbl);
		mid.add(authorTxt);
		mid.add(priceLbl);
		mid.add(priceTxt);
		mid.add(stockLbl);
		mid.add(stockTxt);

		mid.setBorder(new EmptyBorder(20, 60, 20, 60));

		bot.add(insert);
		bot.add(update);
		bot.add(delete);

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

		Vector<Model> listProduct = ProductController.getInstance().getAll();

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

	public void searchResult() {
		data = new Vector<>();

		header = new Vector<>();
		header.add("Product ID");
		header.add("Product Name");
		header.add("Product Author");
		header.add("Product Price");
		header.add("Product Stock");

		Vector<Model> listProduct = ProductController.getInstance().searchProduct();

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
		if (e.getSource() == insert) {
			String name = nameTxt.getText();
			String author = authorTxt.getText();
			String price = priceTxt.getText();
			String stock = stockTxt.getText();

			AdminController.getInstance().create(name, author, price, stock);
			loadData();
		} else if (e.getSource() == update) {
			String id = idValue.getText();
			String name = nameTxt.getText();
			String author = authorTxt.getText();
			String price = priceTxt.getText();
			String stock = stockTxt.getText();

			AdminController.getInstance().update(id, name, author, price, stock);
			loadData();
		} else if (e.getSource() == delete) {
			String id = idValue.getText();

			AdminController.getInstance().delete(id);
			loadData();

			idValue.setText("-");
			nameTxt.setText("");
			authorTxt.setText("");
			priceTxt.setText("");
			stockTxt.setText("");
		} else if (e.getSource() == search) {
			String name = searchTxt.getText();

			AdminController.getInstance().searchProduct(name);
			searchResult();
		} else if (e.getSource() == logout) {
			this.dispose();
			UserController.getInstance().disposeUser();
			LoginController.getInstance().view().showForm();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == table) {
			int row = table.getSelectedRow();
			idValue.setText(table.getValueAt(row, 0).toString());
			nameTxt.setText(table.getValueAt(row, 1).toString());
			authorTxt.setText(table.getValueAt(row, 2).toString());
			priceTxt.setText(table.getValueAt(row, 3).toString());
			stockTxt.setText(table.getValueAt(row, 4).toString());
		}

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
