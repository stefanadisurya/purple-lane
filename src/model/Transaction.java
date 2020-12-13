package model;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import core.model.Model;

public class Transaction extends Model {

	private Integer transactionId;
	private Date transactionDate;
	private String paymentType;
	private String cardNumber;
	private String promoCode = "";
	private Integer userId;

	public Transaction() {
		this.tableName = "Transaction";
	}

	public Transaction(Integer transactionId, Date transactionDate, String paymentType, String cardNumber,
			String promoCode, Integer userId) {
		super();
		this.tableName = "Transaction";
		this.transactionId = transactionId;
		this.transactionDate = transactionDate;
		this.paymentType = paymentType;
		this.cardNumber = cardNumber;
		this.promoCode = promoCode;
		this.userId = userId;
	}

	public Transaction usePromoCode(String promoCode) {
		this.promoCode = promoCode;
		return new Transaction(transactionId, transactionDate, paymentType, cardNumber, promoCode, userId);
	}

	public Transaction create() {
		if (this.promoCode.isEmpty())
			this.promoCode = "No Promo Code";
		String query = String.format("INSERT INTO %s VALUES (Null,?,?,?,?,?)", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setDate(1, transactionDate);
			ps.setString(2, paymentType);
			ps.setString(3, cardNumber);
			ps.setString(4, promoCode);
			ps.setInt(5, userId);
			ps.executeUpdate();
			transactionId = getTransactionId(transactionDate, paymentType, cardNumber, userId);
			return new Transaction(transactionId, transactionDate, paymentType, cardNumber, promoCode, userId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Integer getTransactionId(Date transactionDate2, String paymentType2, String cardNumber2, Integer userId2) {
		String query = String.format(
				"SELECT * FROM %s " + "WHERE transactionDate='" + transactionDate2
						+ "' AND paymentType LIKE '%s' AND cardNumber=%s AND userId=%d ORDER BY transactionId DESC",
				this.tableName, paymentType2, cardNumber2, userId2);
		ResultSet res = this.con.executeQuery(query);

		try {
			while (res.next()) {
				Integer transactionId = res.getInt("transactionId");
				return transactionId;
			}
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
			return new Transaction(transactionId, transactionDate, paymentType, cardNumber, promoCode, userId);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Vector<Transaction> getTransactionReport(Integer Month, Integer Year) {
		String query = String.format("SELECT * FROM %s " + "WHERE transactionDate=%s", this.tableName, transactionDate);
		ResultSet res = this.con.executeQuery(query);

		try {
			Vector<Transaction> temp = new Vector<>();
			while (res.next()) {
				Transaction t = map(res);
				temp.add(t);
				return temp;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public Vector<Transaction> getTransactionHistory(Integer userId) {
		String query = String.format("SELECT * FROM %s " + "WHERE userId=%d", this.tableName, userId);
		ResultSet res = this.con.executeQuery(query);

		try {
			Vector<Transaction> list = new Vector<>();
			while (res.next()) {
				Transaction t = map(res);
				list.add(t);
				return list;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Integer getTransactionId() {
		return transactionId;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public Integer getUserId() {
		return userId;
	}

	@Override
	public Vector<Model> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
