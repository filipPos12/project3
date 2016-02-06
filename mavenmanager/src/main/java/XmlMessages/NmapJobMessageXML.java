package XmlMessages;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import dbclasses.NmapJob;

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
