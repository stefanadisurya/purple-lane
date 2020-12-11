package view;

import java.awt.BorderLayout;
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

import controller.AuthController;
import controller.PromoController;
import core.model.Model;
import core.view.View;
import model.Promo;

public class PromoCodeView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore, menuBack;
	JPanel top, mid, bot, pnlbottombottom;
	JTable table;
	JButton backBtn;
	JLabel titleLbl;

	public PromoCodeView() {
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
		titleLbl = new JLabel("View All Promo Code");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mid = new JPanel();
		bot = new JPanel(new BorderLayout());

		pnlbottombottom = new JPanel();

		backBtn = new JButton("Back to Menu");
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

		pnlbottombottom.add(backBtn);
		bot.add(pnlbottombottom, BorderLayout.CENTER);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		backBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backBtn) {
			this.dispose();
			new ManagePromoMenuView().showForm();
		}
		if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

	public void loadData() {
		Vector<String> header = new Vector<>();
		header.add("Promo Code");
		header.add("Promo Discount");
		header.add("Promo Note");
		DefaultTableModel dtm = new DefaultTableModel(header, 0);

		Vector<Model> promos = PromoController.getInstance().getAll();
		for (Model model : promos) {
			Promo p = (Promo) model;
			Vector<Object> row = new Vector<>();
			row.add(p.getPromoCode());
			row.add(p.getPromoDiscount().toString());
			row.add(p.getPromoNote());
			dtm.addRow(row);
		}
		table.setModel(dtm);
	}

}
