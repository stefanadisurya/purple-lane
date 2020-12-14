package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import core.model.Model;

public class Product extends Model {
	
	private Integer productId;
	private String productName;
	private String productAuthor;
	private Integer productPrice;
	private Integer productStock;
	private String tableName;
	
	public Integer getProductId() {
		return productId;
	}

	public boolean setProductId(Integer productId) {
		if (productId == 0)
			return false;
		this.productId = productId;
		return true;
	}

	public String getProductName() {
		return productName;
	}

	public boolean setProductName(String productName) {
		if (productName.equals(""))
			return false;
		this.productName = productName;
		return true;
	}

	public String getProductAuthor() {
		return productAuthor;
	}

	public boolean setProductAuthor(String productAuthor) {
		if (productAuthor.equals(""))
			return false;
		this.productAuthor = productAuthor;
		return true;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public boolean setProductPrice(Integer productPrice) {
		if (productPrice <= 0)
			return false;
		this.productPrice = productPrice;
		return true;
	}

	public Integer getProductStock() {
		return productStock;
	}

	public boolean setProductStock(Integer productStock) {
		if (productStock <= 0)
			return false;
		this.productStock = productStock;
		return true;
	}

	public Product() {
		this.tableName = "products";
	}

	public Product(Integer productId, String productName, String productAuthor, Integer productPrice,
			Integer productStock) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productAuthor = productAuthor;
		this.productPrice = productPrice;
		this.productStock = productStock;
		this.tableName = "products";
	}
	
	public Product createProduct(String productName, String productAuthor, Integer productPrice,
			Integer productStock) {
		String query = String.format("" + "INSERT INTO %s VALUES " + "(null, ?, ?, ?, ?)", tableName);
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setString(1, productName);
			ps.setString(2, productAuthor);
			ps.setInt(3, productPrice);
			ps.setInt(4, productStock);
			ps.executeUpdate();
			return new Product(null, productName, productAuthor, productPrice, productStock);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product updateProduct(Integer productId, String productName, String productAuthor, Integer productPrice,
			Integer productStock) {
		String query = String.format(
				"UPDATE %s SET productName=?, productAuthor=?, productPrice=?, productStock=? WHERE productId=?",
				tableName);
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setString(1, productName);
			ps.setString(2, productAuthor);
			ps.setInt(3, productPrice);
			ps.setInt(4, productStock);
			ps.setInt(5, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product deleteProduct(Integer productId) {
		String query = String.format("DELETE FROM %s WHERE productId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product reduceStock(Integer productStock, Integer productId) {
		this.productStock-=productStock;
		if(this.productStock<0)this.productStock=0;
		String query = String.format("UPDATE %s SET productStock=? WHERE productId=?", tableName);
		PreparedStatement ps = con.prepareStatement(query);

		try {
			ps.setInt(1, this.productStock);
			ps.setInt(2, productId);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Vector<Model> getAll() {
		Vector<Model> data = new Vector<>();

		String query = String.format("SELECT * FROM %s", tableName);
		ResultSet rs = con.executeQuery(query);

		try {
			while (rs.next()) {
				Integer id = rs.getInt("productId");
				String name = rs.getString("productName");
				String author = rs.getString("productAuthor");
				Integer price = rs.getInt("productPrice");
				Integer stock = rs.getInt("productStock");

				Product p = new Product();
				p.setProductId(id);
				p.setProductName(name);
				p.setProductAuthor(author);
				p.setProductPrice(price);
				p.setProductStock(stock);

				data.add(p);
			}
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Product getOneProduct(Integer productId) {
		String query = String.format("SELECT * FROM %s "
				+ "WHERE productId=%d", tableName, productId);
		ResultSet res = this.con.executeQuery(query);
		
		try {
			while(res.next()) {
				Product p = map(res);
				return p;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private Product map(ResultSet res) {
		Integer id;
		try {
			id = res.getInt("productId");
			String name = res.getString("productName");
			String author = res.getString("productAuthor");
			Integer price = res.getInt("productPrice");
			Integer stock = res.getInt("productStock");
			
			return new Product(id, name, author, price, stock);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
