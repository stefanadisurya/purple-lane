package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import core.model.Model;

public class Role extends Model{
	private Integer roleId;
	private String roleName;
	
	public Role(Integer roleId, String roleName) {
		super();
		this.tableName = "Role";
		this.roleId = roleId;
		this.roleName = roleName;
	}
	
	private Role map(ResultSet rs) {
		try {
			Integer roleId=rs.getInt("roleId");
			String roleName=rs.getString("roleName");
			return new Role(roleId, roleName);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Role getOneRole(Integer roleId) {
		String query = String.format("SELECT * FROM %s WHERE roleId=%d",this.tableName,roleId);
		ResultSet rs = this.con.executeQuery(query);
		try {
			while(rs.next()) {
				Role role = map(rs);
				return role;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Role() {
		super();
	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector<Model> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
