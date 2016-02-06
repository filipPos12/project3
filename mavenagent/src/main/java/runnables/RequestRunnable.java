package runnables;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;

import XmlMessages.NmapJobMessageXML;
import XmlMessages.NmapJob;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;

import shared.AgentProperties;
import shared.CommonProperties;
import shared.TaskQueue;
import threads.ThreadManager;

public class RequestRunnable implements Runnable {
	String hash = AgentProperties.getInstance().getHash();
	
	public void run() {
		Client client = Client.create();
		WebResource webResource = client.resource("http://127.0.0.1:9998/myresource/jobs");
		
		try {
			while (true) {
				ClientResponse response = webResource.queryParam("hash", hash).accept("application/xml").get(ClientResponse.class);
			
				XmlMessages.NmapJob[] array = response.getEntity(XmlMessages.NmapJob[].class);
				
				
				System.out.println(array.length);
				
				if (response.getStatus() == 200) {
					for (int i = 0; i < array.length; i++) {
						XmlMessages.NmapJob jreceived = array[i];
						jobs.NmapJob j = new jobs.NmapJob(jreceived.getId(), jreceived.getParameters(), jreceived.isLoop(), jreceived.getSeconds());

						if (j != null) {
							if (j.getParameters().startsWith("STOP:")) {
								ThreadManager.getInstance().stopPeriodicTimeWorker(j.getId());
							} else if (j.isLoop()) {
								ThreadManager.getInstance().startPeriodicTimeWorker(j);
							} else {
								System.out.println("insert job id: " + j.getId());
								TaskQueue.getInstance().put(j);
							}
						}
					}
					System.out.println("===================");
				} else {
					System.out.println("REPONSE != 200");
				}

				Thread.sleep(CommonProperties.getInstance().getT1());
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Reading nmpajob file error");
		}

	}
}
