package controller;

import java.util.Vector;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Product;
import view.ProductView;

public class AdminController extends Controller {

	private ProductController productController;
	private static AdminController controller;

	private AdminController() {
		productController = ProductController.getInstance();
	}

	public static AdminController getInstance() {
		return controller = (controller == null) ? new AdminController() : controller;
	}

	@Override
	public View view() {
		return new ProductView();
	}

	@Override
	public Vector<Model> getAll() {
		return productController.getAll();
	}

	public void create(String productName, String productAuthor, Integer productPrice, Integer productStock) {

		productController.setProductName(productName);
		productController.setProductAuthor(productAuthor);
		productController.setProductPrice(productPrice);
		productController.setProductStock(productStock);

		if (productController.isValid() == true) {
			Product p = productController.createProduct();
		}

	}

	public void update(Integer productId, String productName, String productAuthor, Integer productPrice, Integer productStock) {
		productController.setProductId(productId);
		productController.setProductName(productName);
		productController.setProductAuthor(productAuthor);
		productController.setProductPrice(productPrice);
		productController.setProductStock(productStock);

		if (productController.isValid() == true) {
			Product p = productController.updateProduct();
		}
	}

	public void delete(Integer productId) {
		productController.setProductId(productId);
		
		if(productController.isValid() == true) {
			Product p = productController.deleteProduct();
		}
	}

}
