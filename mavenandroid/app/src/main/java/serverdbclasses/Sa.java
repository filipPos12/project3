package serverdbclasses;

import java.io.Serializable;

/**
 * Created by filippos on 23/1/2016.
 */
public class Sa implements Serializable {
    private String hash;
    private String devicename;
    private String ip;
    private String macaddr;
    private String osversion;
    private String nmapversion;
    private boolean alive;

    public Sa(String hash, String devicename, String ip, String macaddr,
              String osversion, String nmapversion, boolean alive) {
        super();
        this.hash = hash;
        this.devicename = devicename;
        this.ip = ip;
        this.macaddr = macaddr;
        this.osversion = osversion;
        this.nmapversion = nmapversion;
        this.alive = alive;
    }

    @Override
    public String toString() {
        if (alive) {
            return devicename + ", " + ip + ", mac=" + macaddr + " (alive)";
        } else {
            return devicename + ", " + ip + ", mac=" + macaddr + " (offline)";
        }
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

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Sa() {
        super();
    }
}
