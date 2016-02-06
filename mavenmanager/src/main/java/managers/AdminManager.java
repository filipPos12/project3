package managers;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import dbclasses.Admin;
import dbclasses.NmapJob;

public class AdminManager extends BaseManager {
	private static final String SQL_QUERY_ALL = "select * from admin order by username";
	private static final String SQL_QUERY_INSERT = "insert into admin(username,password) values(?,?)";
	private static final String SQL_QUERY_DELETE_BY_KEY = "delete from admin where username=?";
	private static final String SQL_QUERY_BY_ADMIN_USERNAME= "select * from admin where username=?";
	private static final String SQL_QUERY_BY_ADMIN_USERNAME_AND_PASSWORD= "select * from admin where username=?" + " and password=?";
	private static final String SQL_QUERY_UPDATE_BY_KEY = "update admin set `alive`=? where username=?";
	
	private static AdminManager instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new AdminManager();
		}
	}

	public static AdminManager getInstance() {
		if (instance == null) {
			instance = new AdminManager();
		}
		return instance;
	}
	
	// ---------------------------------------------------------------------------
	private List<Admin> executeStatement(PreparedStatement statement) {
		try {
			ResultSet pointer = statement.executeQuery();
			
			List<Admin> list = new ArrayList<Admin>();
			
			while (pointer.next() != false) {
				String password= pointer.getString("password");
				String username = pointer.getString("username");
				
				Admin w = new Admin( username , password);
				list.add(w);
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	
	
	// ---------------------------- SELECT ------------------------------
	// find one by primary key
	public List<Admin> find(String key) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ADMIN_USERNAME);
			statement.setString(1, key);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public List<Admin> find(String username, String password) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_ADMIN_USERNAME_AND_PASSWORD);
			statement.setString(1, username);
			statement.setString(2, password);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}
	
	
			
	// select all
	public List<Admin> all() {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}
	// insert one
	public int add(Admin n) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_INSERT);
			statement.setString(1, n.getUsername());
			statement.setString(2, n.getPassword());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}
	// delete one
	
	public int remove(String key) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_DELETE_BY_KEY);
			statement.setString(1, key);
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}

//update one
	public int updateToAlive(Admin a) {
		try {
			// alive
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_UPDATE_BY_KEY);	
			statement.setBoolean(1, true);
			statement.setString(2, a.getUsername());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}



	public int updateToDead(Admin a) {
		try {
			// dead
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_UPDATE_BY_KEY);	
			statement.setBoolean(1, false);
			statement.setString(2, a.getUsername());
			return executeUpdateStatement(statement);
			
		} catch (SQLException e) {
			return -1;
		
	}
		
		
	}
}

