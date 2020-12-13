package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import core.model.Model;

public class TransactionDetail extends Model {

	private Integer transactionId;
	private Integer productId;
	private Integer productQuantity;
	
	
	
	public Integer getTransactionId() {
		return transactionId;
	}


	public TransactionDetail(Integer transactionId, Integer productId, Integer productQuantity) {
		super();
		this.transactionId = transactionId;
		this.productId = productId;
		this.productQuantity = productQuantity;
	}
	
	public TransactionDetail() {
		this.tableName = "detailtransaction"; // Kalau mau ganti ke table masing-masing monggo
	}
	
	
	public TransactionDetail create() {
		String query = String.format("INSERT INTO %s VALUES (?,?,?)", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, transactionId);
			ps.setInt(2, productId);
			ps.setInt(3, productQuantity);
;
			return new TransactionDetail(transactionId, productId, productQuantity);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	private TransactionDetail map(ResultSet rs) {
		try {
			Integer transactionId = rs.getInt("transactionId");
			Integer productId = rs.getInt("productId");
			Integer productQuantity = rs.getInt("productQty"); // Kalau mau ganti ke productQuantity disini ya
			return new TransactionDetail(transactionId, productId, productQuantity);

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

		try {
			Vector<Model> td = new Vector<>();
			while (rs.next()) {
				TransactionDetail transactionDetail = map(rs);
				td.add(transactionDetail);
			}
			return td;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}


	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
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

	public Vector<TransactionDetail> getAllTransactionDetail(Integer transactionId) {
		String query = String.format("SELECT * FROM %s WHERE "
				+ "transactionId=%d", tableName, transactionId);
		ResultSet rs = this.con.executeQuery(query);
		
		try {
			Vector<TransactionDetail> list = new Vector<>();
			while(rs.next()) {
				TransactionDetail td = map(rs);
				list.add(td);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
