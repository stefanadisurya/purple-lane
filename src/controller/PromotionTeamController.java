package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import view.ManagePromoMenuView;

public class PromotionTeamController extends Controller {

	private static PromotionTeamController controller;

	private PromotionTeamController() {
		super();
	}

	public static PromotionTeamController getInstance() {
		return controller = (controller == null) ? new PromotionTeamController() : controller;
	}

	@Override
	public View view() {
		return new ManagePromoMenuView();
	}

	@Override
	public Vector<Model> getAll() {
		return null;
	}

}
