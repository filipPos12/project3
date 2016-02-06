package runnables;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Launcher {
//	public String exec(String command) {
//		try {
//			String doc = "", line = "";
//			
//			Process p = Runtime.getRuntime().exec(command);
//			
//			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
//			
//			while ((line = input.readLine()) != null) {
//				doc = doc + line;
//			}
//			input.close();
//			return doc;
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.exit(0);
//			return null;
//		}
//	}
	
	public String exec(String command) {
		String[] cmd = { "/bin/sh", "-c", command };
		
		try {
			String doc = "", line = "";
			
			Process p = Runtime.getRuntime().exec(cmd);
			
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			while ((line = input.readLine()) != null) {
				doc = doc + line;
			}
			input.close();
			return doc;
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
			return "";
		}
	}
}
