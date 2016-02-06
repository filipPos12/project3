package splitter;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import dbclasses.NmapJob;

public class Splitter {
	public NmapJob split(String line) {
		String [] columns = line.split(",");
		for (int i=0;i<columns.length;i++) {
			columns[i] = columns[i].trim();			
		}
		
		try {
			String parameters = columns[0];
			boolean loop = columns[1].equalsIgnoreCase("true");
			int seconds = Integer.parseInt(columns[2]);
			NmapJob object = new NmapJob(parameters, loop, seconds);
			return object;
		} catch (Exception e) {
			System.out.println("Warning: error at line: " + line);
			return null;
		}
	}
}
