package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Products;
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

	public void insert(String productName, String productAuthor, Integer productPrice, Integer productStock) {

		productController.setProductName(productName);
		productController.setProductAuthor(productAuthor);
		productController.setProductPrice(productPrice);
		productController.setProductStock(productStock);

		if (productController.isValid() == true) {
			Products p = productController.createProduct();
			p.create();
		}

	}

	public void update(String productName, String productAuthor, Integer productPrice, Integer productStock,
			Integer productId) {
		productController.setProductName(productName);
		productController.setProductAuthor(productAuthor);
		productController.setProductPrice(productPrice);
		productController.setProductStock(productStock);
		productController.setProductId(productId);

		if (productController.isValid() == true) {
			Products p = productController.createProduct();
			p.update();
		}
	}

	public void delete(Integer productId) {
		Products p = new Products();
		p.setProductId(productId);
		p.delete();
	}

}
