package runnables;

import XmlMessages.RegisterMessageXML;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import jobs.NmapJobResult;
import shared.AgentProperties;
import shared.CommonProperties;
import shared.ResultQueue;

public class RegisterRunnable implements Runnable {
	public boolean registrationSuccessful = false;

	public void run() {
		RegisterMessageXML msg = new RegisterMessageXML();
		msg.setHash(AgentProperties.getInstance().getHash());
		msg.setIp(AgentProperties.getInstance().getIp());
		msg.setMacaddr(AgentProperties.getInstance().getMac());
		msg.setDevicename(AgentProperties.getInstance().getName());
		msg.setNmapversion(AgentProperties.getInstance().getNmv());
		msg.setOsversion(AgentProperties.getInstance().getOsv());

		System.out.println(msg);
		try {
			while (!Thread.currentThread().isInterrupted()) {
				System.out.println("contacting server ... ");
				Client client = Client.create();

				WebResource webResource = client.resource("http://127.0.0.1:9998/myresource/register");

				ClientResponse response = webResource.accept("application/xml").post(ClientResponse.class, msg);

				switch(response.getStatus()) {
					case 202:
						registrationSuccessful = true;
						return;					
					case 200:
						System.out.println("REPONSE == 200: Registration pending ..." + response.getStatus());
						break;
					default:
							System.out.println("REPONSE != 200: Details : " + response.getStatus());
				}

				Thread.sleep(CommonProperties.getInstance().getT3());
			}
		} catch (InterruptedException e) {
			System.out.println("Problem with the exec of the Periodic Thread");
		}

	}
}
