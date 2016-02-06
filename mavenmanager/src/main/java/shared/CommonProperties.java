package shared;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonProperties extends Properties {
	private String DB_USERNAME;
	private String DB_PASSWORD;
	private String DB_IP;
	private String DB_NAME;
	
	private CommonProperties() {
		try {
			FileInputStream f = new FileInputStream("arguments.properties");
			load(f);
			DB_USERNAME = getProperty("DB_USERNAME").trim();
			DB_PASSWORD = getProperty("DB_PASSWORD").trim();
			DB_IP = getProperty("DB_IP").trim();
			DB_NAME = getProperty("DB_NAME").trim();
			
			f.close();
					
		} catch (IOException ex) {
			System.out.println("An critical error has occured while loading arguments.properties: ");
			ex.printStackTrace();
			System.exit(-1);
		}
	}
	
	private static CommonProperties instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new CommonProperties();
		}
	}
	
	public static CommonProperties getInstance() {
		if (instance == null) {
			instance = new CommonProperties();
		}
		return instance;
	}

	public String getDB_USERNAME() {
		return DB_USERNAME;
	}

	public String getDB_PASSWORD() {
		return DB_PASSWORD;
	}

	public String getDB_IP() {
		return DB_IP;
	}

	public String getDB_NAME() {
		return DB_NAME;
	}
	
	
}
