package runnables;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import jobs.NmapJob;
import jobs.NmapJobResult;
import shared.CommonProperties;
import shared.ResultQueue;
import shared.TaskQueue;

public class SenderRunnable implements Runnable {
	
	
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				int x =ResultQueue.getInstance().size();		// 5
				NmapJobResult [] r = new NmapJobResult[x];
				
				System.out.println("---SENDING----");
				for (int i=0;i<x;i++) {
					r[i] = ResultQueue.getInstance().remove();
					System.out.println("job id: " + r[i].getJobid() + ": <" + r[i].getOutput().substring(0, 1000) + ">");
//					System.out.println(r.getJobid() + ": <" + r.getOutput() + ">");
				}
				
				
				System.out.println("contacting server ... ");
				Client client = Client.create();
				WebResource webResource = client.resource("http://127.0.0.1:9998/myresource/results");

				ClientResponse response = webResource.post(ClientResponse.class, r);

				if (response.getStatus() == 200) {
					System.out.println("REPONSE == 200: Details : Results were committed!");
				} else {
					System.out.println("REPONSE != 200: Details : " + response.getStatus());
				}
				System.out.println("-------");
				
				Thread.sleep(CommonProperties.getInstance().getT2());
			}
		} catch (InterruptedException e) {
			System.out.println("Problem with ResultQueue");
		}
	}
}