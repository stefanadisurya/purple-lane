
package core.model;

import java.util.Vector;

import connect.Connect;

public abstract class Model {

	protected String tableName;
	protected Connect con = Connect.getConnection();

	public Model() {

	}

	public abstract Vector<Model> getAll();

}
