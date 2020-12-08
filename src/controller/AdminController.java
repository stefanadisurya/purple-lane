package controller;

import java.util.Vector;

import javax.swing.JOptionPane;

import core.controller.Controller;
import core.model.Model;
import core.view.View;
import model.Products;
import view.ProductView;

public class AdminController extends Controller {
	
	private Products product;
	private static AdminController controller;
	
	public AdminController() {
		product = new Products();
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
		return product.getAll();
	}
	
	public void insert(String productName, String productAuthor, Integer productPrice, Integer productStock) {
		Products p = new Products();
		p.setProductName(productName);
		p.setProductAuthor(productAuthor);
		p.setProductPrice(productPrice);
		p.setProductStock(productStock);
		
		if(!productName.equals("")) {
			if(!productAuthor.equals("")) {
				if(productPrice != null) {
					try {
						Integer.parseInt(productPrice.toString());
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Price must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
					if(productStock != null) {
						try {
							Integer.parseInt(productStock.toString());
						} catch (Exception e) {
							JOptionPane.showMessageDialog(null, "Stock must be number!", "Warning!", JOptionPane.WARNING_MESSAGE);
						}
						if(!productPrice.toString().equals("0")) {
							if(!productStock.toString().equals("0")) {
								p.insert();
							} else {
								JOptionPane.showMessageDialog(null, "Stock must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "Price must be more than 0", "Warning!", JOptionPane.WARNING_MESSAGE);
						}
					} else {
						JOptionPane.showMessageDialog(null, "Please fill the Product Stock column!", "Warning!", JOptionPane.WARNING_MESSAGE);
					}
				} else  {
					JOptionPane.showMessageDialog(null, "Please fill the Product Price column!", "Warning!", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please fill the Product Author column!", "Warning!", JOptionPane.WARNING_MESSAGE);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please fill the Product Name column!", "Warning!", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	public void update(String productName, String productAuthor, Integer productPrice, Integer productStock, Integer productId) {
		Products p = new Products();
		p.setProductName(productName);
		p.setProductAuthor(productAuthor);
		p.setProductPrice(productPrice);
		p.setProductStock(productStock);
		p.setProductId(productId);
		p.update();
	}
	
	public void delete(Integer productId) {
		Products p = new Products();
		p.setProductId(productId);
		p.delete();
	}

}
