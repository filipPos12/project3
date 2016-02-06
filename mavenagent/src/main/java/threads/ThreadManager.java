package threads;

import java.util.ArrayList;
import java.util.HashMap;

import jobs.NmapJob;
import math.Hash;

import org.omg.CORBA.Request;

import runnables.OneTimeRunnable;
import runnables.PeriodicRunnable;
import runnables.RegisterRunnable;
import runnables.RequestRunnable;
import runnables.SenderRunnable;
import shared.CommonProperties;

public class ThreadManager {
	private boolean power = true;	
	
	private ThreadManager() {
		
	}
	
	private static ThreadManager instance = null;
	
	private ArrayList<Thread> runningThreads = new ArrayList<Thread>();	
	private HashMap<String, Thread> periodicThreads = new HashMap<String, Thread> ();
	
	public static ThreadManager getInstance() {
		if (instance == null) {
			instance = new ThreadManager();
		}
		return instance;
	}
	
	// -------------------------------------------------------------
	private void startRequester() {
		RequestRunnable rr = new RequestRunnable();
		Thread t = new Thread(rr);
		t.start();
		runningThreads.add(t);
	}
	
	private void startSender() {
		SenderRunnable rr = new SenderRunnable();
		Thread t = new Thread(rr);
		t.start();
		runningThreads.add(t);
	}
	
	private void startOneTimeWorker() {
		OneTimeRunnable rr = new OneTimeRunnable();
		Thread t = new Thread(rr);
		t.start();
		runningThreads.add(t);
	}
	
	public void startPeriodicTimeWorker(NmapJob job) {
		PeriodicRunnable rr = new PeriodicRunnable(job);
		Thread t = new Thread(rr);
		t.start();
		runningThreads.add(t);
		periodicThreads.put(job.getId()+"", t);
	}
	
	public void stopPeriodicTimeWorker(int jobid) {
		String id = jobid+"";
		Thread t = periodicThreads.get(id);
		if (t != null) {
			t.interrupt();
		}
		periodicThreads.remove(id);
	}
	
	// ------------------------------------------------------
	
	
	public void begin() {
		startRegister();
		
		startRequester();
		startSender();
		for (int i=0;i<CommonProperties.getInstance().getThreadPoolSize();i++) {
			startOneTimeWorker();
		}
	}
	
	private void startRegister() {
		RegisterRunnable rr = new RegisterRunnable();
		Thread t = new Thread(rr);
		t.start();
		try {
			System.out.println("Waiting for server data ...");
			System.out.println("press ctrl+c to cancel ...");
			t.join();
			
			if (rr.registrationSuccessful) {
				System.out.println("You have registered successfully.");
				return;
			} else {
				System.out.println("Your registration was not accepted by the administration.");
				System.out.println("Please try again later or contact admin");
				System.exit(0);
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void end() {
		int threads = runningThreads.size();
		for (int i=0;i<threads;i++) {
			runningThreads.get(i).interrupt();			
		}
		waitAllThreads();
	}
	
	public void waitAllThreads() {
		int threads = runningThreads.size();
		for (int i=0;i<threads;i++) {
			try {
				runningThreads.get(i).join();
			} catch (InterruptedException e) {
			}
		}
	}
}

