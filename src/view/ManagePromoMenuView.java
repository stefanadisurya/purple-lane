package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import controller.AuthController;
import core.view.View;

public class ManagePromoMenuView extends View implements ActionListener {

	private static final long serialVersionUID = 1L;
	JMenuBar menuBar;
	JMenuItem logout;
	JMenu menuMore;
	JPanel top, mid, bot, pnlbottombottom;
	JButton createBtn, updateBtn, deleteBtn, viewBtn;
	JLabel titleLbl;

	public ManagePromoMenuView() {
		super();
		addListeners();
		this.height = 700;
		this.width = 600;
	}

	@Override
	public void initialize() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");

		top = new JPanel();
		titleLbl = new JLabel("Manage Promo Code Menu");

		mid = new JPanel(new BorderLayout());

		pnlbottombottom = new JPanel(new GridLayout(4, 1, 10, 10));
		bot = new JPanel();

		createBtn = new JButton("Create");
		updateBtn = new JButton("Update");
		deleteBtn = new JButton("Delete");
		viewBtn = new JButton("Get All Promo Code");
	}

	@Override
	public void initializeComponent() {
		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);

		top.add(titleLbl);

		pnlbottombottom.add(createBtn);
		pnlbottombottom.add(updateBtn);
		pnlbottombottom.add(deleteBtn);
		pnlbottombottom.add(viewBtn);
		mid.add(pnlbottombottom, BorderLayout.CENTER);
		mid.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

		add(top, BorderLayout.NORTH);
		add(mid, BorderLayout.CENTER);
	}

	private void addListeners() {
		viewBtn.addActionListener(this);
		logout.addActionListener(this);
		createBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == viewBtn) {
			this.dispose();
			new PromoCodeView().showForm();
		}
		if (e.getSource() == createBtn) {
			this.dispose();
			new CreateNewPromoView().showForm();
		}
		if (e.getSource() == updateBtn) {
			this.dispose();
			new UpdatePromoView().showForm();
		}
		if (e.getSource() == deleteBtn) {
			this.dispose();
			new DeletePromoView().showForm();
		}
		if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

}
