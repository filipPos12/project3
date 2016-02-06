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


public class AdminPendingManager extends BaseManager  {	
	private static final String SQL_QUERY_ALL = "select * from admin_pending order by username";
	private static final String SQL_QUERY_INSERT = "insert into admin_pending(username,password) values(?,?)";
	private static final String SQL_QUERY_DELETE_BY_KEY = "delete from admin_pending where username=?";
	private static final String SQL_QUERY_BY_ADMIN_USERNAME = "select * from admin_pending where username = ?";


	private static AdminPendingManager instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new AdminPendingManager();
		}
	}

	public static AdminPendingManager getInstance() {
		if (instance == null) {
			instance = new AdminPendingManager();
		}
		return instance; 
	}
	
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
}