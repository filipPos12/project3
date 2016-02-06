package XmlMessages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NmapJob {
	private int id;
	private String parameters;
	private boolean loop;
	private int seconds;
	private String hash;
	
	public NmapJob() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParameters() {
		return parameters;
	}
	public void setParameters(String parameters) {
		this.parameters = parameters.trim();
	}
	public boolean isLoop() {
		return loop;
	}
	public void setLoop(boolean loop) {
		this.loop = loop;
	}
	public int getSeconds() {
		return seconds;
	}
	public void setSeconds(int seconds) {
		this.seconds = seconds;
	}
	
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	
	public long getMilliSeconds() {
		return seconds*1000;
	}
	
	public NmapJob(int id, String parameters, boolean loop, int seconds, String hash) {
		super();
		this.id = id;
		this.parameters = parameters;
		this.loop = loop;
		this.seconds = seconds;
		this.hash = hash;
	}
}
