package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.model.Model;
import model.Products;

public class ProductController {
	
	private Products products;
	private boolean valid;
	
	public ProductController() {
		products = new Products();
		valid = true;
	}
	
	public void setProductId(Integer productId) {
		if(this.products.setProductId(productId) == false) {
			JOptionPane.showMessageDialog(null, "ProductId Not Detected!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}

	public void setProductName(String productName) {
		if(this.products.setProductName(productName) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Name column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}

	public void setProductAuthor(String productAuthor) {
		if(this.products.setProductAuthor(productAuthor) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Author column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}
	
	public void setProductPrice(Integer productPrice) {
		if(productPrice.equals(null)) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Price column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
		if(this.products.setProductPrice(productPrice) == false) {
			JOptionPane.showMessageDialog(null, "Price must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}

	public void setProductStock(Integer productStock) {
		if(productStock.equals(null)) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Stock column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
		if(this.products.setProductStock(productStock) == false) {
			JOptionPane.showMessageDialog(null, "Stock must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}
	
	public boolean isValid() {
		return valid;
	}
	
	public Products getProduct() {
		return products;
	}
	
	public Vector<Model> getAll() {
		return products.getAll();
	}
	
}
