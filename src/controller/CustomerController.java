package controller;

import java.util.Vector;
import core.controller.Controller;
import core.model.Model;
import core.view.View;
import view.CustomerHomeView;

public class CustomerController extends Controller {
	
	private static CustomerController controller;
	private ProductController productController;
	
	public static CustomerController getInstance() {
		return controller = (controller == null) ? new CustomerController() : controller;
	}
	
	public CustomerController() {
		productController = new ProductController();
	}

	@Override
	public View view() {
		return new CustomerHomeView();
	}

	@Override
	public Vector<Model> getAll() {
		return productController.getAll();
	}

}
