package managers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import shared.Database;

public class BaseManager {
	protected final Connection connection;
		
	public BaseManager() {
		connection = Database.getInstance().getConnection();
	}
	
	protected int executeUpdateStatement(PreparedStatement statement) {
		try {
			int x = statement.executeUpdate();
			return x;
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
}
