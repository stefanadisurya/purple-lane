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

import controller.LoginController;
import controller.PromoController;
import controller.UserController;
import core.model.Model;
import core.view.View;
import model.Promo;

public class DeletePromoView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout, home;
	JMenu menuMore, menuHome;
	JPanel top, mid, bot, pnlbottomtop, pnlbottombottom;
	JTable table;
	JButton deleteBtn, backBtn;
	JLabel titleLbl, promoIdLbl, idLbl;

	public DeletePromoView() {
		super();
		addListeners();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuHome = new JMenu("Home");
		menuMore = new JMenu("More");
		home = new JMenuItem("Home");
		logout = new JMenuItem("Logout");

		top = new JPanel();
		titleLbl = new JLabel("Delete Ongoing Promo Code");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mid = new JPanel();
		bot = new JPanel(new BorderLayout());
		GridLayout layout = new GridLayout(1, 2);

		pnlbottomtop = new JPanel(layout);
		pnlbottomtop.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

		pnlbottombottom = new JPanel();

		promoIdLbl = new JLabel("Promo Id");
		idLbl = new JLabel();

		deleteBtn = new JButton("Delete");
		deleteBtn.setBackground(Color.RED);
		backBtn = new JButton("Back to Menu");
	}

	@Override
	public void initializeComponent() {
		menuHome.add(home);
		menuMore.add(logout);
		menuBar.add(menuHome);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);

		top.add(titleLbl);
		loadData();

		mid.add(new JScrollPane(table), BorderLayout.CENTER);
		pnlbottomtop.add(promoIdLbl);
		pnlbottomtop.add(idLbl);

		pnlbottombottom.add(backBtn);
		pnlbottombottom.add(deleteBtn);
		bot.add(pnlbottomtop, BorderLayout.NORTH);
		bot.add(pnlbottombottom, BorderLayout.SOUTH);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		home.addActionListener(this);
		logout.addActionListener(this);
		deleteBtn.addActionListener(this);
		backBtn.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String id = (String) table.getValueAt(row, 0);

				idLbl.setText(id);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == deleteBtn) {
			delete();
		} else if (e.getSource() == backBtn || e.getSource() == home) {
			this.dispose();
			UserController.getInstance().processRole(UserController.getInstance().getActiveUser());
		} else if (e.getSource() == logout) {
			this.dispose();
			UserController.getInstance().disposeUser();
			LoginController.getInstance().view().showForm();
		}
	}

	public void loadData() {
		Vector<String> header = new Vector<>();
		header.add("Promo id");
		header.add("Promo Code");
		header.add("Promo Discount");
		header.add("Promo Note");
		DefaultTableModel dtm = new DefaultTableModel(header, 0);

		Vector<Model> promos = PromoController.getInstance().getAll();
		for (Model model : promos) {
			Promo p = (Promo) model;
			Vector<Object> row = new Vector<>();
			row.add(p.getPromoId().toString());
			row.add(p.getPromoCode());
			row.add(p.getPromoDiscount().toString());
			row.add(p.getPromoNote());
			dtm.addRow(row);
		}
		table.setModel(dtm);
	}

	private void delete() {

		int ans = JOptionPane.showConfirmDialog(this, "Are you sure Delete This Promo Code?");
		if (ans == JOptionPane.YES_OPTION) {
			String id = idLbl.getText();
			Boolean promo = PromoController.getInstance().delete(id);
			if (promo == false) {
				JOptionPane.showMessageDialog(this, PromoController.getInstance().getErrorMessage());
			} else {
				JOptionPane.showMessageDialog(this, "Delete Success!");
				loadData();
			}
			idLbl.setText("");
		} else if (ans == JOptionPane.NO_OPTION) {
			idLbl.setText("");
		}

	}

}
