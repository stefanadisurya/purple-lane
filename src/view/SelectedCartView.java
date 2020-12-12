package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import controller.CartController;
import controller.ProductController;
import core.model.Model;
import core.view.View;
import model.Cart;

public class SelectedCartView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore, menuBack;
	JPanel top, mid, bot, pnlbottomtop, pnlbottombottom;
	JTable table;
	JButton cancelBtn, payBtn;
	JLabel titleLbl, totalPriceLbl, tpLbl;

	public SelectedCartView() {
		super();
		addListeners();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuBack = new JMenu("Back");
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");

		top = new JPanel();
		titleLbl = new JLabel("List Selected Product");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mid = new JPanel();
		bot = new JPanel(new BorderLayout());
		GridLayout layout = new GridLayout(1, 2, 10, 10);

		pnlbottomtop = new JPanel(layout);
		pnlbottomtop.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

		pnlbottombottom = new JPanel();

		totalPriceLbl = new JLabel("Total Price");
		tpLbl = new JLabel("");

		cancelBtn = new JButton("Cancel");
		payBtn = new JButton("Pay");
		payBtn.setBackground(Color.GREEN);
	}

	@Override
	public void initializeComponent() {
		menuMore.add(logout);
		menuBar.add(menuBack);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);

		top.add(titleLbl);
		loadData();

		mid.add(new JScrollPane(table), BorderLayout.CENTER);
		pnlbottomtop.add(totalPriceLbl);
		pnlbottomtop.add(tpLbl);

		pnlbottombottom.add(cancelBtn);
		pnlbottombottom.add(payBtn);
		bot.add(pnlbottomtop, BorderLayout.NORTH);
		bot.add(pnlbottombottom, BorderLayout.SOUTH);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		cancelBtn.addActionListener(this);
		payBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == cancelBtn) {
			this.dispose();
			CartController.getInstance().viewManageCartMenu();
		} else if (e.getSource() == payBtn) {
//			CartController.getInstance().processSelectedCart();
			this.dispose();
//			new SelectedCartView.showForm();
		} else if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

	public void loadData() {

		Vector<String> header = new Vector<>();
		header.add("Product Id");
		header.add("Product Name");
		header.add("Product Quantity");
		header.add("Product Price");
		DefaultTableModel dtm = new DefaultTableModel(header, 0);

		Integer totalPrice = 0;
		Vector<Cart> carts = CartController.getInstance().getSelectedCart();
		for (Cart c : carts) {
			Vector<Object> row = new Vector<>();
			row.add(c.getProductId().toString());
			row.add(ProductController.getInstance().getOneProduct(c.getProductId()).getProductName());
			row.add(c.getProductQuantity().toString());
			row.add(ProductController.getInstance().getOneProduct(c.getProductId()).getProductPrice());
			totalPrice += ProductController.getInstance().getOneProduct(c.getProductId()).getProductPrice();
			dtm.addRow(row);
		}
		table.setModel(dtm);
		tpLbl.setText(totalPrice.toString());
	}

}
