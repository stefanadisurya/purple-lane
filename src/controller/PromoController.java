
package controller;

import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import core.model.Model;
import model.Promo;
import model.Role;

public class PromoController extends JFrame implements ActionListener {

	JMenuBar menuBar;
	JMenu menuMore;
	JMenuItem logout;

	public static PromoController controller = null;
	public Promo promo;
	public String errorMessage;

	private PromoController() {
		initialize();
		promo = new Promo();
	}

	public static PromoController getInstance() {
		if (controller == null) {
			controller = new PromoController();
		}
		return controller;

	}

	public String getErrorMessage() {
		return errorMessage;
	}

//	public viewManagePromoMenu() {
//		//return view;
//	}

	private Promo getData() {
//		Integer promoId = Integer.parseInt(promoIdField.getText());
//		String promoCode = promoCodeField.getText();
//		Integer promoDiscount = Integer.parseInt(promoDiscountField.getText()));
//		String promoNote = promoNoteField.getText();
//		return new Promo(promoId,promoCode,promoDiscount,promoNote);
		return null;
	}

	public Promo create() {
		Promo pd = getData();

		if (pd.getPromoId() == null) {
			errorMessage = "Id cannot be empty!";
			return null;
		} else if (pd.getPromoCode() == null) {
			errorMessage = "Code cannot be empty!";
			return null;
		} else if (pd.getPromoDiscount() == null) {
			errorMessage = "Discount cannot be empty!";
			return null;
		} else if (pd.getPromoDiscount() < 15000) {
			errorMessage = "Discount must be at least 15000!";
			return null;
		} else if (pd.getPromoNote() == null) {
			errorMessage = "Note cannot be empty!";
			return null;
		} else {

			Promo promo = pd.insert1();
			if (promo == null) {
				errorMessage = "Insert Failed!";
			}
			return promo;
		}
	}

	public Promo update() {
		Promo pd = getData();

		if (pd.getPromoId() == null) {
			errorMessage = "Id cannot be empty!";
			return null;
		} else if (pd.getPromoCode() == null) {
			errorMessage = "Code cannot be empty!";
			return null;
		} else if (pd.getPromoDiscount() == null) {
			errorMessage = "Discount cannot be empty!";
			return null;
		} else if (pd.getPromoDiscount() < 15000) {
			errorMessage = "Discount must be at least 15000!";
			return null;
		} else if (pd.getPromoNote() == null) {
			errorMessage = "Note cannot be empty!";
			return null;
		} else {

			Promo promo = pd.update1();
			if (promo == null) {
				errorMessage = "Update Failed!";
			}
			return promo;
		}
	}

	public boolean delete() {
		Promo pd = getData();

		if (pd.getPromoId() == null) {
			errorMessage = "Id cannot be empty!";
			return false;
		} else {
			promo = new Promo();
			promo.setPromoId(pd.getPromoId());
			boolean deleted = promo.delete1();

			if (deleted == false) {
				errorMessage = "Delete Failed!";
			}
			return deleted;
		}
	}

	public Promo getOnePromo(Integer promoId) {
		// validasi
		return promo.getOnePromo(promoId);
	}

	public Vector<Model> getAll() {
		// validasi
		return promo.getAll();
	}

	void initializeComponent() {
		menuBar = new JMenuBar();
		menuMore = new JMenu("More");
		logout = new JMenuItem("Logout");

		logout.addActionListener(this);

		menuMore.add(logout);
		menuBar.add(menuMore);
		setJMenuBar(menuBar);
	}

	public void initialize() {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		initializeComponent();

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logout) {
			this.dispose();
			new AuthController();
		}
	}

}
