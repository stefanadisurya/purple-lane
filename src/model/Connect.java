package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private String USERNAME = "root";
	private String PASSWORD = "";
	private String HOST = "localhost:3306";
	private String DATABASE = "purplelane";
	private String CONNECT_ADDRESS = "jdbc:mysql://" + HOST + "/" + DATABASE;
	
	public Connection con;
	static Connect instance;
	public Statement st;
	
	public Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection(CONNECT_ADDRESS, USERNAME, PASSWORD);
			st = con.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection failed.");
			System.exit(0);
		}
		
		System.out.println("Connected!");
	}
	
	public static Connect getInstance() {
		return instance = instance == null ? new Connect() : instance;
	}
	
	public ResultSet executeQuery(String query) {
		try {
			return st.executeQuery(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void executeUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
