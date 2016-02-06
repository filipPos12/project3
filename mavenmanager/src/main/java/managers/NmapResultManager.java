package managers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import dbclasses.NmapJob;
import dbclasses.NmapJobResult;
import dbclasses.Sa;

public class NmapResultManager extends BaseManager {
	private static final String SQL_QUERY_ALL = "select * from nmapjobsresult";
	private static final String SQL_QUERY_SOME = "select nmapjobsresult.* from nmapjobsresult, nmapjob where nmapjobsresult.nmapjobs_id = nmapjob.id and nmapjob.sa_hash = ? order by timeofinsert desc";
	private static final String SQL_QUERY_SELECT = "select * from nmapjobsresult";
	private static final String SQL_QUERY_INSERT = "insert into nmapjobsresult (nmapjobs_id,nmapjobsresults, timeofinsert) values(?, ?, CURRENT_TIMESTAMP)";
	
	
	private static NmapResultManager instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new NmapResultManager();
		}
	}

	public static NmapResultManager getInstance() {
		if (instance == null) {
			instance = new NmapResultManager();
		}
		return instance;
	}
	
	// ---------------------------------------------------------------------------
	private List<NmapJobResult> executeStatement(PreparedStatement statement) {
		try {
			ResultSet pointer = statement.executeQuery();
			
			List<NmapJobResult> list = new ArrayList<NmapJobResult>();
			
			while (pointer.next() != false) {
				int id = pointer.getInt("id");
				String results = pointer.getString("nmapjobsresults");
				
				NmapJobResult newresult = new NmapJobResult(id, results);
				list.add(newresult);
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public int add(NmapJobResult n) {
		try {
			// parameters,loop, seconds,username,sa_hash
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_INSERT);
			statement.setInt(1, n.getJobid());
			statement.setString(2, n.getRaw_text());
			return executeUpdateStatement(statement);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public List<NmapJobResult> all() {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_ALL);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}
	
	public List<NmapJobResult> some(String hash) {
		try {
			PreparedStatement statement = connection.prepareStatement(SQL_QUERY_SOME);
			statement.setString(1,  hash);
			return executeStatement(statement);
		} catch (SQLException e) {
			return null;
		}
	}

	public List<NmapJobResult> all(Sa sa) {
		if (sa == null) {
			return all();
		} else {
			return some(sa.getHash());
		}
	}
}
