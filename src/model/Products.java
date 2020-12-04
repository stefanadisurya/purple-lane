package model;

import java.util.Vector;

public class Products {
	
	public int productId;
	public String productName;
	public String productAuthor;
	public int productPrice;
	public int productStock;
	
	public Vector<Object> toObjects() {
		Vector<Object> ret = new Vector<Object>();
		
		ret.add(productId);
		ret.add(productName);
		ret.add(productAuthor);
		ret.add(productPrice);
		ret.add(productStock);
		
		return ret;
	}
	
	public Products(int productId, String productName, String productAuthor, int productPrice, int productStock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productAuthor = productAuthor;
		this.productPrice = productPrice;
		this.productStock = productStock;
	}
	

}
