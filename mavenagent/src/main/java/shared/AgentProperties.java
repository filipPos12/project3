package shared;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.NetworkInterface;
import java.net.SocketException;

import math.Hash;
import runnables.Launcher;


// http://stackoverflow.com/questions/245916/best-way-to-extract-mac-address-from-ifconfigs-output

public class AgentProperties {
	private String name = "";
	private String ip  = "";
	private String mac = "";
	private String osv = "";
	private String nmv = "";
	private String hash = "";	
	
	private AgentProperties() {
		Launcher launcher = new Launcher();
		 
		//device name:
		try {
			name = InetAddress.getLocalHost().getHostName();
			osv = System.getProperty("os.name");
			nmv = launcher.exec("nmap -version | grep version | cut -f3 -d\" \"");
			mac = launcher.exec("ifconfig eth0 | grep -o -E '([[:xdigit:]]{1,2}:){5}[[:xdigit:]]{1,2}'");
			ip = launcher.exec("ifconfig eth0 | grep inet | grep -v inet6 | awk '{print $2}'");
			hash = Hash.sha1(name+ip+mac+osv+nmv+hash);
		} catch (UnknownHostException ex) {
		    System.out.println("Hostname can not be resolved");
		}
	}
	
	private static AgentProperties instance = null;
	
	public static void init() {
		if (instance == null) {
			instance = new AgentProperties();
		}
	}
	
	public static AgentProperties getInstance() {
		if (instance == null) {
			instance = new AgentProperties();
		}
		return instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOsv() {
		return osv;
	}

	public void setOsv(String osv) {
		this.osv = osv;
	}

	public String getNmv() {
		return nmv;
	}

	public void setNmv(String nmv) {
		this.nmv = nmv;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public static void setInstance(AgentProperties instance) {
		AgentProperties.instance = instance;
	}
	
	
}
