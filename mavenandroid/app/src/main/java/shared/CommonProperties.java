package shared;

import android.app.Activity;
import android.content.res.Resources;

import java.io.IOException;
import java.util.Properties;

import scan.di.uoa.gr.exampleappsoftdev.R;


public class CommonProperties {
    // private String WS_IP ="192.168.1.234:9998"; // ethernet
    private String WS_IP ="192.168.43.123:9998";  // wifi


    private CommonProperties() {
        // WS_IP = getResources().getString(R.string.wsip);

        // WS_IP = Resources.getSystem().getString(R.string.wsip);
    }

    private static CommonProperties instance = null;

    public static void init() {
        if (instance == null) {
            instance = new CommonProperties();
        }
    }

    public static CommonProperties getInstance() {
        if (instance == null) {
            instance = new CommonProperties();
        }
        return instance;
    }

    public String getWS_IP() {
        return WS_IP;
    }

    public void setWS_IP(String WS_IP) {
        this.WS_IP = WS_IP;
    }
}
