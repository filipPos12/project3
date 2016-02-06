package runnables;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import jobs.NmapJob;

public class Splitter {
	public NmapJob split(String line) {
		String [] columns = line.split(",");
		for (int i=0;i<columns.length;i++) {
			columns[i] = columns[i].trim();			
		}
		
		
		try {
			int id = Integer.parseInt(columns[0]);
			String parameters = columns[1];
			boolean loop = columns[2].equalsIgnoreCase("true");
			int seconds = Integer.parseInt(columns[3]);
			NmapJob object = new NmapJob(id, parameters, loop, seconds);
			return object;
		} catch (Exception e) {
			System.out.println("Warning: error at line: " + line);
			return null;
		}
	}
}
