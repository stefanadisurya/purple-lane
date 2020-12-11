
package core.model;

import java.util.Vector;

import connect.Connect;

public abstract class Model {

	protected String tableName;
	protected Connect con = Connect.getConnection();

	public Model() {

	}
	
	//Albert [karna masing" model beda Paramaeter]
//	public abstract void insert();
//
//	public abstract void update();
//
//	public abstract void delete();

	public abstract Vector<Model> getAll();

}
