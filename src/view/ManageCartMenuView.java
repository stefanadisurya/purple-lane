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
import core.model.Model;
import core.view.View;
import model.Cart;

public class ManageCartMenuView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore, menuBack;
	JPanel top, mid, bot, pnlbottomtop, pnlbottombottom;
	JTable table;
	JButton selectBtn, checkOutBtn;
	JLabel titleLbl, productNameLbl, pmLbl, productQtyLbl, qtyLbl;

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
		pmLbl = new JLabel();
		productQtyLbl = new JLabel("Promo Quantity");
		qtyLbl = new JLabel();

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
				String name = (String) table.getValueAt(row, 1);
				String qty = (String) table.getValueAt(row, 2);

				pmLbl.setText(name);
				qtyLbl.setText(qty);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == selectBtn) {
			// display select list
		}
		if (e.getSource() == checkOutBtn) {
			// checkout
		}
		if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

	public void loadData() {
		
		Vector<String> header = new Vector<>();
		header.add("Index");
		header.add("Product Name");
		header.add("Product Quantity");
		DefaultTableModel dtm = new DefaultTableModel(header, 0);

		Vector<Model> carts = CartController.getInstance().getAll();
		System.out.println("testst"+carts.size());
		Integer index = 0;
		for (Model model : carts) {
			Cart c = (Cart) model;
			index++;
			Vector<Object> row = new Vector<>();
			row.add(index.toString());
			row.add(ProductController.getInstance().getOneProduct(c.getProductId()).getProductName());
			row.add(c.getProductQuantity().toString());
			dtm.addRow(row);
		}
		table.setModel(dtm);
		System.out.println("TEST");
	}

	private void delete() {

//		int ans = JOptionPane.showConfirmDialog(this, "Are you sure Delete This Promo Code?");
//		if (ans == JOptionPane.YES_OPTION) {
//			String id = idLbl.getText();
//			Boolean promo = PromoController.getInstance().delete(id);
//			if (promo == false) {
//				JOptionPane.showMessageDialog(this, PromoController.getInstance().getErrorMessage());
//			} else {
//				JOptionPane.showMessageDialog(this, "Delete Success!");
//				loadData();
//			}
//			idLbl.setText("");
//		} else if (ans == JOptionPane.NO_OPTION) {
//			idLbl.setText("");
//		}
//
	}

}
