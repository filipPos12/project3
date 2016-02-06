package shared;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class CommonProperties extends Properties {
	private int THREAD_POOL_SIZE;
	private int T1;
	private int T2;
	private int T3;
	
	private CommonProperties() {
		try {
			FileInputStream f = new FileInputStream("arguments.properties");
			load(f);
			THREAD_POOL_SIZE = Integer.parseInt(getProperty("THREAD_POOL_SIZE").trim());
			T1 = Integer.parseInt(getProperty("T1").trim());
			T2 = Integer.parseInt(getProperty("T2").trim());
			T3 = Integer.parseInt(getProperty("T3").trim());
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
	
	public int getThreadPoolSize() {
		return THREAD_POOL_SIZE;
	}
	
	public int getT1() {
		return T1;
	}
	
	public int getT2() {
		return T2;
	}
	public int getT3(){
		return T3;
	}
}
