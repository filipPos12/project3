package shared;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	private Connection conn = null;
	
	private Database() {
		 try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String connectionString = "jdbc:mysql://" + 
            		CommonProperties.getInstance().getDB_IP() + "/" +
            		CommonProperties.getInstance().getDB_NAME() + "?" +
            		"user=" + CommonProperties.getInstance().getDB_USERNAME() + "&" +
            		"password="+ CommonProperties.getInstance().getDB_PASSWORD();
            conn = DriverManager.getConnection(connectionString);
            
            System.out.println(connectionString);
            System.out.println("Connect successful");
        } catch (Exception ex) {
        	System.out.println("Loading mysql driver failed or database connection failed");
        	System.exit(-1);
        }
	}
	
	public static void init() {
		if (instance == null) {
			instance = new Database();
		}
	}
	
	public static void clean() {
		if (instance != null) {
			try {
				instance.conn.close();
				System.out.println("Disconnect successful");
			} catch (SQLException e) {
			}
			instance = null;
		}
	}
	
	private static Database instance = null;
	
	public static Database getInstance() {
		if (instance == null) {
			instance = new Database();
		}
		return instance;
	}

	public Connection getConnection() {
		return conn;
	}
	
	
}
