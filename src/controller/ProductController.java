
package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Product;
import view.ProductView;

public class ProductController extends Controller {

	private Product product;
	private static ProductController controller;
	private boolean valid;
	
	public void setValid(boolean valid) {
		this.valid = valid;
	}

	private ProductController() {
		product = new Product();
		valid = true;
	}
	
	public static ProductController getInstance() {
		return controller = (controller == null) ? new ProductController() : controller;
	}
	
	@Override
	public View view() {
		return new ProductView();
	}

	public void setProductId(Integer productId) {
		if (this.product.setProductId(productId) == false) {
			JOptionPane.showMessageDialog(null, "ProductId Not Detected!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}

	public void setProductName(String productName) {
		if (this.product.setProductName(productName) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Name column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}

	public void setProductAuthor(String productAuthor) {
		if (this.product.setProductAuthor(productAuthor) == false) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Author column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		}
	}

	public void setProductPrice(String productPrice) {
		if(productPrice.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Price column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		} else {
			Integer temp = 0;
			try {
				temp = Integer.parseInt(productPrice);
				
				if(temp <= 0) {
					JOptionPane.showMessageDialog(null, "Product Price must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
					valid = false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Product Price must be numeric!", "Warning!", JOptionPane.WARNING_MESSAGE);
				valid = false;
			}
			
			product.setProductPrice(temp);
		}
	}

	public void setProductStock(String productStock) {
		if(productStock.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please fill the Product Stock column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			valid = false;
		} else {
			Integer temp = 0;
			try {
				temp = Integer.parseInt(productStock);
				
				if(temp <= 0) {
					JOptionPane.showMessageDialog(null, "Product Stock must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
					valid = false;
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Product Stock must be numeric!", "Warning!", JOptionPane.WARNING_MESSAGE);
				valid = false;
			}
			
			product.setProductStock(temp);
		}
	}

	public boolean isValid() {
		return valid;
	}
	
	public Product newProduct() {
		return product;
	}

	public Product createProduct() {
		newProduct();
		return product.createProduct(product.getProductName(), product.getProductAuthor(), product.getProductPrice(), product.getProductStock());
	}
	
	public Product updateProduct() {
		newProduct();
		return product.updateProduct(product.getProductId(), product.getProductName(), product.getProductAuthor(), product.getProductPrice(), product.getProductStock());
	}
	
	public Product deleteProduct() {
		newProduct();
		return product.deleteProduct(product.getProductId());
	}
	
	public Product reduceStock(Integer productQuantity) {
		newProduct();
		return product.reduceStock(productQuantity, product.getProductId());
	}

	public Vector<Model> getAll() {
		return product.getAll();
	}
	
	public Product getOneProduct(Integer productId) {
		return product.getOneProduct(productId);
	}

}
