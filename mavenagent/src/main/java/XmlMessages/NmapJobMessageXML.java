package XmlMessages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NmapJobMessageXML {
	NmapJob[] jobs;

	
	public NmapJobMessageXML() {
		
	}
	
	public NmapJob[] getJobs() {
		return jobs;
	}

	public void setJobs(NmapJob[] jobs) {
		this.jobs = jobs;
	}
}
