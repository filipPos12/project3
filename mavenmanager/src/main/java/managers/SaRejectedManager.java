package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dbclasses.Sa;
import dbclasses.Sa_rejected;

public class SaRejectedManager extends BaseManager {
	private static final String SQL_QUERY_ALL = "select * from sa_rejected order by hash";
	private static final String SQL_QUERY_INSERT = "insert into sa_rejected(hash) values(?)";
	private static final String SQL_QUERY_DELETE_BY_KEY = "delete from sa_rejected where hash=?";
	private static final String SQL_QUERY_BY_SA_HASH = "select * from sa_rejected where hash=?";

	private static SaRejectedManager instance = null;

	public static void init() {
		if (instance == null) {
			instance = new SaRejectedManager();
		}
	}

	public static SaRejectedManager getInstance() {
		if (instance == null) {
			instance = new SaRejectedManager();
		}
		return instance;
	}

	// ---------------------------------------------------------------------------
	private List<Sa_rejected> executeStatement(PreparedStatement statement) {
		try {
			ResultSet pointer = statement.executeQuery();

			List<Sa_rejected> list = new ArrayList<Sa_rejected>();

			while (pointer.next() != false) {
				String hash = pointer.getString("hash");
	
				Sa_rejected w = new Sa_rejected(hash);
	
				list.add(w);
			}
			
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// ---------------------------- SELECT ------------------------------
	// find one by primary key
	public List<Sa_rejected> find(String key) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_BY_SA_HASH);
			statement.setString(1, key);
			return executeStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// select all
	public List<Sa_rejected> all() {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL);
			return executeStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int add(Sa n) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_INSERT);
			statement.setString(1, n.getHash());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}

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
