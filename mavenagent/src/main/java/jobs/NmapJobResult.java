package jobs;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NmapJobResult {
	int jobid;
	String output;
	
	public NmapJobResult() {
		
	}

	public String getRaw_text() {
		return output;
	}
	public void setRaw_text(String raw_text) {
		this.output = raw_text;
	}
	
	public NmapJobResult(int jobid, String raw_text) {
		this.jobid = jobid;
		this.output = raw_text;
	}
	
	
	public void setJobid(int jobid) {
		this.jobid = jobid;
	}

	public int getJobid() {
		return jobid;
	}
	public String getOutput() {
		return output;
	}
	
}
