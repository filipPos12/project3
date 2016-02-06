package shared;

import java.util.concurrent.LinkedBlockingQueue;

import jobs.NmapJob;

public class TaskQueue extends LinkedBlockingQueue<NmapJob> {
	private TaskQueue() {
		
	}
	
	public static void init() {
		if (instance == null) {
			instance = new TaskQueue();
		}
	}
	
	private static TaskQueue instance = null;
	
	public static TaskQueue getInstance() {
		if (instance == null) {
			instance  = new TaskQueue(); 
		}
		return instance;
	}
}
