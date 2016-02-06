
package xpd.mavenmanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import shared.ClientState;
import managers.AdminManager;
import managers.AdminPendingManager;
import managers.NmapJobManager;
import managers.NmapResultManager;
import managers.SaManager;
import managers.SaPendingManager;
import managers.SaRejectedManager;
import dbclasses.Admin;
import dbclasses.NmapJob;
import dbclasses.NmapJobResult;
import dbclasses.Sa;
import XmlMessages.NmapJobMessageXML;
import XmlMessages.NmapJobResultMessageXML;
import XmlMessages.PostJobsMessageXML;
import XmlMessages.RegisterMessageXML;

// The Java class will be hosted at the URI path "/myresource"
@Path("/myresource")
public class MyService {
	public static final HashMap<String, ClientState> agenta = new HashMap<String, ClientState>();
	
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_XML)
    @Path("/register")
    public Response register(RegisterMessageXML message) {
    	if (!SaManager.getInstance().find(message.getHash()).isEmpty()) {
    		return Response.status(202).build();
    	} else if (!SaRejectedManager.getInstance().find(message.getHash()).isEmpty()) {
    		return Response.serverError().build();
    	} else if (SaPendingManager.getInstance().find(message.getHash()).isEmpty()) {
    		Sa x = new Sa(message.getHash(), message.getDevicename(), message.getIp(), message.getMacaddr(), message.getOsversion(), message.getNmapversion(), false);
    		SaPendingManager.getInstance().add(x);
    		return Response.ok().build();
    	} else {
    		return Response.serverError().build();
    	}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/jobs")			
    public NmapJob[] getJobs(@QueryParam("hash") String hash) {
    	if (!agenta.containsKey(hash)) {
    		List<NmapJob> j = NmapJobManager.getInstance().all(hash);
    		NmapJob[] array = j.toArray(new NmapJob[j.size()]);
    		agenta.put(hash, new ClientState());
    		return array;
    	} else {
    		ClientState state = agenta.get(hash);
    		Date lastupdate = state.lastupdate;
    		List<NmapJob> j = NmapJobManager.getInstance().since(hash, lastupdate);
    		NmapJob[] array = j.toArray(new NmapJob[j.size()]);
    		state.lastupdate = new Date();
    		return array;
    	}
    }
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    @Path("/results")
    public Response setResults(NmapJobResult [] data) {
    	for (NmapJobResult n : data) {
    		NmapResultManager.getInstance().add(n);
    	}
    	return Response.ok().build();
    }
    
    // ------------------------------------------------------------------------------
    // -------------------------- services for android ------------------------------
    // ------------------------------------------------------------------------------
    
    @GET    
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/registermobile")    
    // http://127.0.0.1:9998/myresource/registermobile?username=bob&password=bob
    public Response registermobile(@QueryParam(value = "username") String username, @QueryParam(value = "password") String password) {
    	Admin a = new Admin(username,password);
    	
    	int x = AdminPendingManager.getInstance().add(a);
    	if (x > 0) {
    		return Response.ok().build();
    	} else {
    		return Response.serverError().build();
    	}
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/loginmobile")
    // http://localhost:9998/myresource/loginmobile?username=bob&password=bob
    public Response loginmobile(@QueryParam(value = "username") String username, @QueryParam(value = "password") String password) {
    	List<Admin> r = AdminManager.getInstance().find(username, password);
    	if (r == null || r.isEmpty()) {
    		return Response.serverError().build();
    	} else {    		
	    	Admin n = new Admin(username,password);	
	   		int x =AdminManager.getInstance().updateToAlive(n);
	   		return Response.ok().build();
    	}
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/logoutmobile")
    // http://localhost:9998/myresource/loginmobile?username=bob&password=bob
    public Response logoutmobile(@QueryParam(value = "username") String username, @QueryParam(value = "password") String password) {
    	List<Admin> r = AdminManager.getInstance().find(username, password);
    	if (r == null || r.isEmpty()) {
    		return Response.serverError().build();
    	} else {
    		Admin n = new Admin(username,password);
    		int x = AdminManager.getInstance().updateToDead(n);
    		return Response.ok().build();
    	}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sastatusmobile")
    public Sa[] SAStatusMobile(@QueryParam(value = "username") String username, @QueryParam(value = "password") String password) {
    	List<Admin> r = AdminManager.getInstance().find(username, password);
    	if (r == null || r.isEmpty()) {    		
    		return null;
    	} else {
    		if (!r.get(0).isAlive()) {
    			return null;
    		}
    		
    		List<Sa> all = SaManager.getInstance().all();
    		Sa[] array = all.toArray(new Sa[all.size()]);
    		
    		return array;
    	}
    }
    
    
    // http://localhost:9998/myresource/addnmapjobmobile?username=bob&password=bob&sahash=16d618bd315c5c31988ec083ee6f9224648eb54a&parameters=bla&seconds=10&loop=true
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/addnmapjobmobile")
    public Response addNmapJobMobile(
    		@QueryParam(value = "username") String username, 
    		@QueryParam(value = "password") String password,
    		@QueryParam(value = "sahash") String sahash,
    		@QueryParam(value = "parameters") String parameters,
    		@QueryParam(value = "loop") String loop,
    		@QueryParam(value = "seconds") String seconds) {
    	List<Admin> r = AdminManager.getInstance().find(username, password);
    	
    	if (r == null || r.isEmpty()) {
    		return Response.serverError().build();
    	} else {
    		if (!r.get(0).isAlive()) {
    			return Response.serverError().build();
    		} else {
				// insert job
    			try {
    				NmapJob a = new NmapJob();
    				a.setHash(sahash);
    				a.setParameters(parameters);
    				a.setLoop(Boolean.parseBoolean(loop));
    				a.setSeconds(Integer.parseInt(seconds));
    				NmapJobManager.getInstance().add(a);
    				return Response.ok().build();
    			} catch (Exception patates) {
    				return Response.serverError().build();
    			}
    		}
    	}
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/saresultmobile")
    public NmapJobResult[] SAResultMobile(
    		@QueryParam(value = "username") String username, 
    		@QueryParam(value = "password") String password, 
    		@QueryParam(value = "hash") String hash
    		) {
    	List<Admin> r = AdminManager.getInstance().find(username);
    	if (r == null || r.isEmpty()) {
    		return null;
    	} else {
    		if (!r.get(0).isAlive()) {
    			return null;
    		} else {
    			// bres ta results.. gia to sygkekrimeno hash
    			 if (hash != null) {
    				 List <NmapJobResult> some = NmapResultManager.getInstance().some(hash);    			
    				 NmapJobResult[] array = some.toArray(new NmapJobResult[some.size()]);    	    		
    				 return array;
    			 } else {
    				 List <NmapJobResult> all = NmapResultManager.getInstance().all();    			
    				 NmapJobResult[] array = all.toArray(new NmapJobResult[all.size()]);    	    		
    				 return array;
    			 }
    		}
    	}
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/allsaresultmobile")
    public String allSAResultMobile(@QueryParam(value = "username") String username) {
    	List<Admin> r = AdminManager.getInstance().find(username);
    	if (r == null || r.isEmpty()) {
    		return "ERROR: WHO ARE YOU";
    	} else {
    		if (!r.get(0).isAlive()) {
    			return "ERROR: LOGIN FIRST";
    		} else {
    			// bres ola ta results.//all!
    			 List <NmapJobResult> all = NmapResultManager.getInstance().all();
    			
    			// bale ta se enan string buffer
    			 StringBuffer result = new StringBuffer();
    			 
    			 for (NmapJobResult n : all) {
 	    			result.append(n.getOutput() + "\n");
 	    		}
    			 
    			 
    			// epestrepse ta os string ...
    			 return result.toString();
    		}
    	}
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/shutdownsamobile")
    public String shutdownSaMobile(
    		@QueryParam(value = "username") String username,
    		@QueryParam(value = "hash") String hash
    		) {
    	List<Admin> r = AdminManager.getInstance().find(username);
    	if (r == null || r.isEmpty()) {
    		return "ERROR: WHO ARE YOU";
    	} else {
    		return "TODO";
    	}
    }
    
    // http://localhost:9998/myresource/deleteperiodicnmapjobmobile?username=bob&password=bob&id=20
        
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/deleteperiodicnmapjobmobile")
    public Response deletePeriodicNMapJobMobile(
    		@QueryParam(value = "username") String username,
    		@QueryParam(value = "password") String password,
    		@QueryParam(value = "id") String id
    		) {
    	List<Admin> r = AdminManager.getInstance().find(username);
    	if (r == null || r.isEmpty()) {
    		return Response.serverError().build();
    	} else {
    		if (!r.get(0).isAlive()) {
    			return Response.serverError().build();
    		} else {
    			NmapJobManager.getInstance().cancel(Integer.parseInt(id));    			
    		}
    		return Response.ok().build();
    	}
    }
    
}
