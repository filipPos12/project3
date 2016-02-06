package runnables;

import shared.ResultQueue;
import shared.TaskQueue;
import jobs.NmapJob;
import jobs.NmapJobResult;

public class OneTimeRunnable implements Runnable {
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				NmapJob job = TaskQueue.getInstance().take();

				Launcher launcher = new Launcher();
				String doc = launcher.exec("nmap " + job.getParameters());

				NmapJobResult e = new NmapJobResult(job.getId(), doc);

				ResultQueue.getInstance().offer(e);
			}
		} catch (InterruptedException e) {
			System.out.println("Problem with the exec of the nmap command");
		}
	}
}
