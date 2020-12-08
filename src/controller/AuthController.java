package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import view.AuthView;

public class AuthController extends Controller {
	
	private static AuthController controller;
	
	public static AuthController getInstance() {
		return controller = (controller == null) ? new AuthController() : controller;
	}

	@Override
	public View view() {
		return new AuthView();
	}

	@Override
	public Vector<Model> getAll() {
		return null;
	}
}
