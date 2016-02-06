package managers;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ListModel;

import dbclasses.NmapJob;

public class NmapJobManager extends BaseManager {
	private static final String SQL_QUERY_ALL = "select * from nmapjob order by id";
	private static final String SQL_QUERY_INSERT = "insert into nmapjob(`parameters`,`loop`, `seconds`,`sa_hash`) values(?, ?, ?,?)";
	private static final String SQL_QUERY_UPDATE_BY_KEY = "update nmapjob set parameters=?,`loop`=?, seconds=?, createdate=now() where sa_hash=? and id=?";
	private static final String SQL_QUERY_DELETE_ALL = "delete from nmapjob";
	private static final String SQL_QUERY_DELETE_BY_KEY = "delete from nmapjob where id=?";
	private static final String SQL_QUERY_ALL_BY_KEY = "select * from nmapjob where id=?";
	private static final String SQL_QUERY_ALL_BY_SA_HASH = "select * from nmapjob where sa_hash=?";
	private static final String SQL_QUERY_ALL_SINCE = "select * from nmapjob where sa_hash=? and createdate>now() order by id";
	private static final String SQL_QUERY_ALL_PERIODIC = "select * from nmapjob where `loop`=true and not `parameters` like 'STOP%' order by id ";
	
	private static NmapJobManager instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new NmapJobManager();
		}
	}

	public static NmapJobManager getInstance() {
		if (instance == null) {
			instance = new NmapJobManager();
		}
		return instance;
	}
	
	// ---------------------------------------------------------------------------
	private List<NmapJob> executeStatement(PreparedStatement statement) {
		try {
			ResultSet pointer = statement.executeQuery();
			
			List<NmapJob> list = new ArrayList<NmapJob>();
			
			while (pointer.next() != false) {
				int id = pointer.getInt("id");
				String parameters = pointer.getString("parameters");
				boolean loop = pointer.getBoolean("loop");
				int seconds = pointer.getInt("seconds");
				String hash = pointer.getString("sa_hash");
				
				NmapJob newjob = new NmapJob(id, parameters, loop, seconds, hash);
				list.add(newjob);
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	
	
	
	// ---------------------------- SELECT ------------------------------
	// find one by primary key
	public List<NmapJob> find(int key) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL_BY_KEY);
			statement.setInt(1, key);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}
			
	// select all
	public List<NmapJob> all() {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}
	// select all
	public List<NmapJob> periodic() {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL_PERIODIC);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}

	// select all
	public List<NmapJob> all(String sa_hash) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL_BY_SA_HASH);
			statement.setString(1, sa_hash);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}

	// insert one
	public int add(NmapJob n) {
		try {
			// parameters,loop, seconds,username,sa_hash
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_INSERT);
			statement.setString(1, n.getParameters());
			statement.setBoolean(2, n.isLoop());
			statement.setInt(3, n.getSeconds());
			statement.setString(4, n.getHash());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}
	
	// update one
	public int update(NmapJob n) {
		try {
			// parameters,loop, seconds,username,sa_hash
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_UPDATE_BY_KEY);
			statement.setString(1, n.getParameters());
			statement.setBoolean(2, n.isLoop());
			statement.setInt(3, n.getSeconds());
			statement.setString(4, n.getHash());
			statement.setInt(5, n.getId());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}
	// delete one
	
	public int remove(int key) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_DELETE_ALL);
			statement.setInt(1, key);
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}

	// delete all
	public int removeAll() {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_DELETE_ALL);
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			return -1;
		}
	}

	public List<NmapJob> since(String sa_hash, Date lastupdate) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL_SINCE);
			statement.setString(1, sa_hash);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}

	public void cancel(int id) {
		try {
			NmapJob oldjob = find(id).get(0);
			oldjob.setParameters("STOP:" +oldjob.getParameters());
			update(oldjob);			
		} catch(Exception e) {
			System.out.println("Warning:") ;
			e.printStackTrace();
		}
		
	}
}
	
