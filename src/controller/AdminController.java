package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
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

	public void create(String productName, String productAuthor, String productPrice, String productStock) {
		productController.setProductName(productName);
		productController.setProductAuthor(productAuthor);
		productController.setProductPrice(productPrice);
		productController.setProductStock(productStock);

		if (productController.isValid() == true) {
			productController.createProduct();
			JOptionPane.showMessageDialog(null, "Insert Product Success!");
		} else {
			productController.setValid(true);
		}

	}

	public void update(String productId, String productName, String productAuthor, String productPrice, String productStock) {
		productController.setId(productId);
		productController.setProductName(productName);
		productController.setProductAuthor(productAuthor);
		productController.setProductPrice(productPrice);
		productController.setProductStock(productStock);

		if (productController.isValid() == true) {
			productController.updateProduct();
			JOptionPane.showMessageDialog(null, "Update Product Success!");
		} else {
			productController.setValid(true);
		}
	}

	public void delete(String productId) {
		productController.setId(productId);
		
		if(productController.isValid() == true) {
			productController.deleteProduct();
			JOptionPane.showMessageDialog(null, "Delete Product Success!");
		} else {
			productController.setValid(true);
		}
	}
	
	public void searchProduct(String productId) {
		productController.setId(productId);
		
		if(productController.isValid() == true) {
			productController.searchProduct();
		} else {
			productController.setValid(true);
		}
	}
	
	public void reduceStock(Integer productStock, Integer productId) {
		productController.setProductId(productId);
		productController.reduceStock(productStock);
		JOptionPane.showMessageDialog(null, "Reduct Product Stock Success!");
	}

}
