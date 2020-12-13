
package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Promo;
import view.ManagePromoMenuView;

public class PromoController extends Controller {

	public static PromoController controller = null;
	private Promo promo;
	private String errorMessage;

	private PromoController() {
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

	public void viewManagePromoMenu() {
		view().showForm();
	}

	private boolean isPromoCodeUnique(String code) {
		Vector<Model> promos = promo.getAll();
		for (Model model : promos) {
			if (((Promo) model).getPromoCode().equals(code))
				return false;
		}
		return true;
	}

	public Promo create(String code, String discount, String note) {

		if (code.isEmpty()) {
			errorMessage = "Promo Code cannot be empty!";
			return null;
		} else if (isPromoCodeUnique(code) == false) {
			errorMessage = "Promo Code Must be unique!";
			return null;
		} else if (note.isEmpty()) {
			errorMessage = "Promo Note cannot be empty!";
			return null;
		} else {
			Integer disc = 0;
			try {
				disc = Integer.parseInt(discount);
			} catch (Exception e) {
				errorMessage = "Promo Discount must be numeric!";
				return null;
			}

			if (disc == 0 || disc == null) {
				errorMessage = "Promo Discount cannot be empty!";
				return null;
			} else if (disc < 15000) {
				errorMessage = "Promo Discount must be at least 15000!";
				return null;
			}

			Promo pd = new Promo();
			pd = pd.create(code, note, disc);
			if (pd == null) {
				errorMessage = "Insert Failed!";
			}
			return pd;
		}
	}

	public Promo update(String id, String code, String discount, String note) {
		Integer ID = 0;
		try {
			ID = Integer.parseInt(id);
		} catch (Exception e) {
			errorMessage = "Choose Promo First!";
			return null;
		}
		if (ID == null || ID == 0) {
			errorMessage = "Promo Id cannot be empty!";
			return null;
		} else if (code.isEmpty()) {
			errorMessage = "Promo Code cannot be empty!";
			return null;
		} else if (isPromoCodeUnique(code) == false) {
			errorMessage = "Promo Code Must be unique!";
			return null;
		} else if (note.isEmpty()) {
			errorMessage = "Promo Note cannot be empty!";
			return null;
		} else {
			Integer disc = 0;
			try {
				disc = Integer.parseInt(discount);
			} catch (Exception e) {
				errorMessage = "Promo Discount must be numeric!";
				return null;
			}

			if (disc == 0 || disc == null) {
				errorMessage = "Promo Discount cannot be empty!";
				return null;
			} else if (disc < 15000) {
				errorMessage = "Promo Discount must be at least 15000!";
				return null;
			}

			Promo pd = new Promo();
			pd = pd.update(ID, code, note, disc);
			if (pd == null) {
				errorMessage = "Update Failed!";
			}
			return pd;
		}

	}

	public boolean delete(String id) {
		Integer ID = 0;
		try {
			ID = Integer.parseInt(id);
		} catch (Exception e) {
			errorMessage = "Choose Promo Code First!";
			return false;
		}
		boolean deleted = promo.delete(ID);

		if (deleted == false) {
			errorMessage = "Delete Failed!";
		}
		return deleted;
	}

	public Promo getOnePromo(String promoId) {
		Promo pm = promo.getOnePromo(promoId);
		if(pm==null) {
			errorMessage="Promo Code Not Valid";
		}
		return pm;
	}

	public Vector<Model> getAll() {
		return promo.getAll();
	}

	@Override
	public View view() {
		return new ManagePromoMenuView();
	}

}
