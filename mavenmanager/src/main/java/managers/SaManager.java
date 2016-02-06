package managers;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;

import dbclasses.NmapJob;
import dbclasses.Sa;

public class SaManager extends BaseManager {
	private static final String SQL_QUERY_ALL = "select * from sa order by devicename";
	private static final String SQL_QUERY_INSERT = "insert into sa(hash,devicename,ip,macaddr,osversion,nmapversion,alive) values(?,?,?,?,?,?,?)";
	private static final String SQL_QUERY_DELETE_BY_KEY = "delete from sa where hash=?";
	private static final String SQL_QUERY_BY_SA_HASH = "select * from sa where hash=?";

	private static SaManager instance = null;

	public static void init() {
		if (instance == null) {
			instance = new SaManager();
		}
	}

	public static SaManager getInstance() {
		if (instance == null) {
			instance = new SaManager();
		}
		return instance;
	}

	// ---------------------------------------------------------------------------
	private List<Sa> executeStatement(PreparedStatement statement) {
		try {
			ResultSet pointer = statement.executeQuery();

			List<Sa> list = new ArrayList<Sa>();
			
			while (pointer.next() != false) {
				String hash = pointer.getString("hash");
				String devicename = pointer.getString("devicename");
				String ip = pointer.getString("ip");
				String macaddr = pointer.getString("macaddr");
				String osversion = pointer.getString("osversion");
				String nmapversion = pointer.getString("nmapversion");
				boolean alive = pointer.getBoolean("alive");
	
				Sa w = new Sa(hash, devicename, ip, macaddr, osversion,
						nmapversion, alive);
	
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
	public List<Sa> find(String key) {
		try {
			PreparedStatement statement = connection
					.prepareStatement(SQL_QUERY_BY_SA_HASH);
			statement.setString(1, key);
			return executeStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// select all
	public List<Sa> all() {
		try {
			PreparedStatement statement = connection
					.prepareStatement(SQL_QUERY_ALL);
			return executeStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int add(Sa n) {
		try {
			// parameters,loop, seconds,username,sa_hash
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_INSERT);
			statement.setString(1, n.getHash());
			statement.setString(2, n.getDevicename());
			statement.setString(3, n.getIp());
			statement.setString(4, n.getMacaddr());
			statement.setString(5, n.getOsversion());
			statement.setString(6, n.getNmapversion());
			statement.setBoolean(7, n.isAlive());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
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