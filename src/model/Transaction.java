package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import core.model.Model;

public class Transaction extends Model{

	private Integer transactionId;
	private Date transactionDate;
	private String paymentType;
	private String cardNumber;
	private String promoCode;
	private Integer userId;
	
	public Transaction(){
		this.tableName = "Transaction";
	}
	
	public Transaction(Integer transactionId, Date transactionDate, String paymentType, String cardNumber,
		String promoCode, Integer userId) {
		super();
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.paymentType = paymentType;
		this.cardNumber = cardNumber;
		this.promoCode = promoCode;
		this.userId = userId;
	}
	
	
	public Transaction create() {
		String query = String.format("INSERT INTO %s VALUES (?,?,?,?,?,?)", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, transactionId);
			ps.setDate(2, transactionDate);
			ps.setString(3, paymentType);
			ps.setString(4, cardNumber);
			ps.setString(5, promoCode);
			ps.setInt(6, userId);
			return new Transaction(transactionId, transactionDate, paymentType, cardNumber, promoCode, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	private Transaction map(ResultSet rs) {
		try {
			Integer transactionId = rs.getInt("transactionId");
			Date transactionDate = rs.getDate("transactionDate");
			String paymentType = rs.getString("paymentType");
			String cardNumber = rs.getString("cardNumber");
			String promoCode = rs.getString("promoCode");
			Integer userId = rs.getInt("userId");
			return new Transaction(transactionId, transactionDate, promoCode, promoCode, promoCode, userId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Transaction getTransactionReport(Integer Month,Integer Year) {
		String query = String.format("SELECT * FROM %s "
				+ "WHERE transactionDate=%s", this.tableName, transactionDate);
		ResultSet res = this.con.executeQuery(query);
		
		try {
			while(res.next()) {
				Transaction t = map(res);
				return t;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	
	public Transaction getTransactionHistory(Integer userId) {
		String query = String.format("SELECT * FROM %s "
				+ "WHERE userId=%d", this.tableName, userId);
		ResultSet res = this.con.executeQuery(query);
		
		try {
			while(res.next()) {
				Transaction t = map(res);
				return t;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	

	public Integer getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Integer transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public Vector<Model> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
