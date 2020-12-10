package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import core.model.Model;

public class Promo extends Model {

	private Integer promoId;
	private String promoCode;
	private Integer promoDiscount;
	private String promoNote;

	public Promo() {
		this.tableName = "Promo";
	}

	public Promo(Integer promoId, String promoCode, Integer promoDiscount, String promoNote) {
		super();
		this.tableName = "Promo";
		this.promoId = promoId;
		this.promoCode = promoCode;
		this.promoDiscount = promoDiscount;
		this.promoNote = promoNote;
	}

	public Promo getOnePromo(Integer promoId) {
		String query = String.format("SELECT * FROM %s WHERE promoId=%d", this.tableName, promoId);
		ResultSet rs = this.con.executeQuery(query);
		try {
			while (rs.next()) {
				Promo promo = map(rs);
				return promo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private Promo map(ResultSet rs) {
		try {
			Integer promoId = rs.getInt("promoId");
			String promoCode = rs.getString("promoCode");
			Integer promoDiscount = rs.getInt("promoDiscount");
			String promoNote = rs.getString("promoNote");
			return new Promo(promoId, promoCode, promoDiscount, promoNote);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Promo insert1() {
		String query = String.format("INSERT INTO %s VALUES (?,?,?,?)", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, promoId);
			ps.setString(2, promoCode);
			ps.setInt(3, promoDiscount);
			ps.setString(4, promoNote);
			return new Promo(promoId, promoCode, promoDiscount, promoNote);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Promo update1() {
		String query = String.format(
				"UPDATE %s " + "SET promoCode = ?, promoDiscount = ?, promoNote = ? " + "WHERE promoId = ?",
				this.tableName);

		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setString(1, promoCode);
			ps.setInt(2, promoDiscount);
			ps.setString(3, promoNote);
			ps.setInt(4, promoId);
			return new Promo(promoId, promoCode, promoDiscount, promoNote);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean delete1() {
		String query = String.format("DELETE FROM %s WHERE promoId = ?", this.tableName);
		PreparedStatement ps = this.con.prepareStatement(query);

		try {
			ps.setInt(1, promoId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	@Override
	public Vector<Model> getAll() {
		String query = "SELECT * FROM " + this.tableName;
		ResultSet rs = this.con.executeQuery(query);

		try {
			Vector<Model> promos = new Vector<>();
			while (rs.next()) {
				Promo promo = map(rs);
				promos.add(promo);
			}
			return promos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public Integer getPromoId() {
		return promoId;
	}

	public void setPromoId(Integer promoId) {
		this.promoId = promoId;
	}

	public String getPromoCode() {
		return promoCode;
	}

	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}

	public Integer getPromoDiscount() {
		return promoDiscount;
	}

	public void setPromoDiscount(Integer promoDiscount) {
		this.promoDiscount = promoDiscount;
	}

	public String getPromoNote() {
		return promoNote;
	}

	public void setPromoNote(String promoNote) {
		this.promoNote = promoNote;
	}

	@Override
	public void insert() {

	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}
}
