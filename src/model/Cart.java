package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import core.model.Model;

public class Cart extends Model{
	private Integer userId;
	private Integer productId;
	private Integer productQuantity;
	
	
	public Cart() {
		super();
		this.tableName = "cart";
	}

	public Cart(Integer userId, Integer productId, Integer productQuantity) {
		super();
		this.tableName = "cart";
		this.userId = userId;
		this.productId = productId;
		this.productQuantity = productQuantity;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}
	
	public Cart create() {
		String query = String.format("INSERT INTO %s VALUES (?,?,?)", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, userId);
			ps.setInt(2, productId);
			ps.setInt(3, productQuantity);
			ps.executeUpdate();
			return new Cart(userId, productId, productQuantity);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Cart update() {
		String query = String.format(
				"UPDATE %s " + "SET productQty = ?" + "WHERE userId = ? AND productId = ?",
				this.tableName);

		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, productQuantity);
			ps.setInt(2, userId);
			ps.setInt(3, productId);
			ps.executeUpdate();
			return new Cart(userId, productId, productQuantity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean delete() {
		String query = String.format("DELETE FROM %s WHERE userId = ? AND productId = ? ", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, userId);
			ps.setInt(2, productId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}
	
	private Cart map(ResultSet rs) {
		try {

			Integer userId = rs.getInt("userId");
			Integer productId = rs.getInt("productId");
			Integer productQuantity = rs.getInt("productQty");
			return new Cart(userId, productId, productQuantity);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Vector<Model> getAll() {
		String query = "SELECT * FROM " + this.tableName;
		ResultSet rs = this.con.executeQuery(query);
		if(rs==null)return null;
		System.out.println("berhasil");
		try {
			Vector<Model> carts = new Vector<>();
			while (rs.next()) {
				Cart cart = map(rs);
				carts.add(cart);
			}
			return carts;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
