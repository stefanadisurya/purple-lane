package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import view.ManagerHomeView;
import view.ProductView;

public class ManagerController extends Controller {

	private ProductController productController;
	private static ManagerController controller;
	private boolean valid;

	private ManagerController() {
		productController = ProductController.getInstance();
	}

	public static ManagerController getInstance() {
		return controller = (controller == null) ? new ManagerController() : controller;
	}

	@Override
	public View view() {
		return new ManagerHomeView();
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

	public void update(Integer productId, String productName, String productAuthor, String productPrice, String productStock) {
		productController.setProductId(productId);
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

	public void delete(Integer productId) {
		productController.setProductId(productId);
		
		if(productController.isValid() == true) {
			productController.deleteProduct();
			JOptionPane.showMessageDialog(null, "Delete Product Success!");
		} else {
			productController.setValid(true);
		}
	}
	
	public void searchProduct(String productName) {
		productController.setProductName(productName);
		
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
