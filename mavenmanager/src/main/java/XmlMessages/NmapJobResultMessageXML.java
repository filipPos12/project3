package XmlMessages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class NmapJobResultMessageXML {
	int id;
	String result;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
	
}
