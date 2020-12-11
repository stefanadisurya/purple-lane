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
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import controller.AuthController;
import controller.PromoController;
import core.model.Model;
import core.view.View;
import model.Promo;

public class CreateNewPromoView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore, menuBack;
	JPanel top, mid, bot, pnlbottomtop, pnlbottombottom;
	JTextField promoCodeTxt, promoNoteTxt, promoDiscountTxt;
	JTable table;
	JButton createBtn,backBtn;
	JLabel titleLbl, promoCodeLbl, promoDiscountLbl, promoNoteLbl;

	public CreateNewPromoView() {
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
		titleLbl = new JLabel("Create New Promo Code");
		table = new JTable() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		mid = new JPanel();
		bot = new JPanel(new BorderLayout());
		GridLayout layout = new GridLayout(4, 1, 10, 10);

		pnlbottomtop = new JPanel(layout);
		pnlbottomtop.setBorder(BorderFactory.createEmptyBorder(10, 40, 10, 40));

		pnlbottombottom = new JPanel();

		promoCodeLbl = new JLabel("Promo Code");
		promoCodeTxt = new JTextField();
		promoDiscountLbl = new JLabel("Promo Discount");
		promoDiscountTxt = new JTextField();
		promoNoteLbl = new JLabel("Promo Note");
		promoNoteTxt = new JTextField();

		createBtn = new JButton("Submit");
		createBtn.setBackground(Color.GREEN);
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
		pnlbottomtop.add(promoCodeLbl);
		pnlbottomtop.add(promoCodeTxt);
		pnlbottomtop.add(promoDiscountLbl);
		pnlbottomtop.add(promoDiscountTxt);
		pnlbottomtop.add(promoNoteLbl);
		pnlbottomtop.add(promoNoteTxt);

		pnlbottombottom.add(backBtn);
		pnlbottombottom.add(createBtn);
		bot.add(pnlbottomtop, BorderLayout.NORTH);
		bot.add(pnlbottombottom, BorderLayout.SOUTH);

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
		add(bot, BorderLayout.SOUTH);
	}

	private void addListeners() {
		createBtn.addActionListener(this);
		backBtn.addActionListener(this);
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				String code = (String) table.getValueAt(row, 0);
				String discount = (String) table.getValueAt(row, 1);
				String note = (String) table.getValueAt(row, 2);

				promoCodeTxt.setText(code);
				promoDiscountTxt.setText(discount + "");
				promoNoteTxt.setText(note);
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == createBtn) {
			create();
		}
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

	private void create() {
		String code = promoCodeTxt.getText();
		String discount = promoDiscountTxt.getText();
		String note = promoNoteTxt.getText();
		Promo promo;
		promo = PromoController.getInstance().create(code, discount, note);
		if (promo == null) {
			JOptionPane.showMessageDialog(this, PromoController.getInstance().getErrorMessage());
		} else {
			JOptionPane.showMessageDialog(this, "Create Success!");
			loadData();
		}
	}

}
