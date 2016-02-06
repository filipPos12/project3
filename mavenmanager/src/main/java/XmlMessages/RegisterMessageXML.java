package XmlMessages;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RegisterMessageXML {
	private String hash;
	private String devicename;
	private String ip;
	private String macaddr;
	private String osversion;
	private String nmapversion;
	
	public RegisterMessageXML() {
		super();
	}
	public String getHash() {
		return hash;
	}
	public void setHash(String hash) {
		this.hash = hash;
	}
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMacaddr() {
		return macaddr;
	}
	public void setMacaddr(String macaddr) {
		this.macaddr = macaddr;
	}
	public String getOsversion() {
		return osversion;
	}
	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}
	public String getNmapversion() {
		return nmapversion;
	}
	public void setNmapversion(String nmapversion) {
		this.nmapversion = nmapversion;
	}
}
