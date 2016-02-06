package runnables;

import jobs.NmapJob;
import jobs.NmapJobResult;
import shared.CommonProperties;
import shared.ResultQueue;
import shared.TaskQueue;

public class PeriodicRunnable implements Runnable {//periodika nimata
	NmapJob job ;
	
	public PeriodicRunnable(NmapJob job) {
		this.job = job;
	}
	
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Launcher launcher = new Launcher();
				String doc = launcher.exec("nmap " + job.getParameters());
				
				NmapJobResult e = new NmapJobResult(job.getId(), doc);
				
				ResultQueue.getInstance().offer(e);
				
				Thread.sleep(job.getMilliSeconds());
			}
		} catch (InterruptedException e) {
			System.out.println("Problem with the exec of the Periodic Thread");
			
		}

	}
}

