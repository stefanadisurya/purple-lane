package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.BorderFactory;
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
import controller.CartController;
import controller.ProductController;
import controller.UserController;
import core.model.Model;
import core.view.View;
import model.Cart;
import model.Users;

public class ManageCartMenuView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore, menuBack;
	JPanel top, mid, bot, pnlbottomtop, pnlbottombottom;
	JTable table;
	JButton selectBtn, checkOutBtn;
	JLabel titleLbl, productNameLbl, pmLbl, productQtyLbl, qtyLbl;
	private Integer productId = 0;

	public ManageCartMenuView() {
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
		titleLbl = new JLabel("My Cart");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mid = new JPanel();
		bot = new JPanel(new BorderLayout());
		GridLayout layout = new GridLayout(2, 2, 10, 10);

		pnlbottomtop = new JPanel(layout);
		pnlbottomtop.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

		pnlbottombottom = new JPanel();

		productNameLbl = new JLabel("Product Name");
		pmLbl = new JLabel("-");
		productQtyLbl = new JLabel("Promo Quantity");
		qtyLbl = new JLabel("0");

		checkOutBtn = new JButton("CheckOut");
		checkOutBtn.setBackground(Color.RED);
		selectBtn = new JButton("Select");
		selectBtn.setBackground(Color.YELLOW);
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
		pnlbottomtop.add(productNameLbl);
		pnlbottomtop.add(pmLbl);
		pnlbottomtop.add(productQtyLbl);
		pnlbottomtop.add(qtyLbl);

		pnlbottombottom.add(selectBtn);
		pnlbottombottom.add(checkOutBtn);
		bot.add(pnlbottomtop, BorderLayout.NORTH);
		bot.add(pnlbottombottom, BorderLayout.SOUTH);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		checkOutBtn.addActionListener(this);
		selectBtn.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String id = (String) table.getValueAt(row, 0);
				String name = (String) table.getValueAt(row, 1);
				String qty = (String) table.getValueAt(row, 2);
				
				productId = Integer.parseInt(id);
				pmLbl.setText(name);
				qtyLbl.setText(qty);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectBtn) {
			selectCart();
		} else if (e.getSource() == checkOutBtn) {
			this.dispose();
			new SelectedCartView().showForm();
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
		DefaultTableModel dtm = new DefaultTableModel(header, 0);

		Vector<Cart> carts = CartController.getInstance().getCartList();
		for (Cart c : carts) {
			Vector<Object> row = new Vector<>();
			row.add(c.getProductId().toString());
			row.add(ProductController.getInstance().getOneProduct(c.getProductId()).getProductName());
			row.add(c.getProductQuantity().toString());
			dtm.addRow(row);
		}
		table.setModel(dtm);
	}

	private void selectCart() {
		int ans = JOptionPane.showConfirmDialog(this, "Select This Product?");
		if (ans == JOptionPane.YES_OPTION) {
			Integer productQuantity = Integer.parseInt(qtyLbl.getText());

			Users u = UserController.getInstance().getActiveUser();
			CartController.getInstance().setCart(new Cart(u.getUserId(), productId, productQuantity));

			Cart cart = CartController.getInstance().selectCart();

			if (cart == null) {
				JOptionPane.showMessageDialog(this, CartController.getInstance().getErrorMessage());
			} else {
				JOptionPane.showMessageDialog(this, "Select Cart Successful!");
				loadData();
			}
			pmLbl.setText("-");
			qtyLbl.setText("0");
			productId=0;
		} else if (ans == JOptionPane.NO_OPTION) {
			productId=0;
			pmLbl.setText("-");
			qtyLbl.setText("0");
		}

	}
}
