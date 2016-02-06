package shared;

import java.util.concurrent.ConcurrentLinkedQueue;

import jobs.NmapJobResult;

public class ResultQueue extends ConcurrentLinkedQueue<NmapJobResult>  {
	private	 ResultQueue() {
		
	}
	
	private static ResultQueue instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new ResultQueue();
		}
	}
	
	public static ResultQueue getInstance() {
		if (instance == null) {
			instance  = new ResultQueue(); 
		}
		return instance;
	}
}
