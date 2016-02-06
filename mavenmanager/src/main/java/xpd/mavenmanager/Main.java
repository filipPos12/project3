
package xpd.mavenmanager;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

import dbclasses.Sa;
import guiclasses.IntroWindow;
import guiclasses.SARequests;
import guiclasses.WelcomeWindow;
import managers.NmapJobManager;
import managers.SaManager;
import managers.SaPendingManager;
import managers.SaRejectedManager;

import org.glassfish.grizzly.http.server.HttpServer;

import shared.CommonProperties;
import shared.Database;

import javax.ws.rs.core.UriBuilder;

import java.awt.EventQueue;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;


//Στατιστικά για την κλήση του nmap:
//- scanning
//- verbose level
//- debugging level
//- trace όλα τα hops που μπορεί να έχει
//
//Για τον host:
//- status
//- address , type
//- hostnames
//- ports
//	1) extraports
//	2) port με όλες τις πληροφορίες για το service που τρέχει σε αυτή την πόρτα
//- os 
//	1) osclass
//	2) osmatch
//- uptime
//- tcpsequence
//- ipidsequence
//- tcptssequence

public class Main {

    private static int getPort(int defaultPort) {
        //grab port from environment, otherwise fall back to default port 9998
        String httpPort = System.getProperty("jersey.test.port");
        if (null != httpPort) {
            try {
                return Integer.parseInt(httpPort);
            } catch (NumberFormatException e) {
            }
        }
        return defaultPort;
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://0.0.0.0/").port(getPort(9998)).build();
    }

    public static final URI BASE_URI = getBaseURI();
    
    protected static HttpServer startServer() throws IOException {
        ResourceConfig resourceConfig = new PackagesResourceConfig("xpd.mavenmanager");

        System.out.println("Starting grizzly2...");
        return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
    }
    
    public static void main(String[] args) throws IOException {
    	CommonProperties.init();
		Database.init();
		SaManager.init();
		SaPendingManager.init();
		SaRejectedManager.init();
		NmapJobManager.init();
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				WelcomeWindow.init();
//				SARequests.init();
//				IntroWindow.init();
			}
		});
		
        // Grizzly 2 initialization
        final HttpServer httpServer = startServer();
        
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				httpServer.stop();
		        Database.clean();			
			}
		});
		
		new Scanner(System.in).nextLine();
    }
   }    



