package tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import scan.di.uoa.gr.exampleappsoftdev.IntroActivity;
import scan.di.uoa.gr.exampleappsoftdev.SaStatusActivity;
import serverdbclasses.Sa;
import serverdbclasses.SaList;
import shared.AccountProperties;
import shared.CommonProperties;

/**
 * Created by filippos on 23/1/2016.
 */
public class SaTask  extends AsyncTask<String, Void, Sa[]> {
    private ProgressDialog progressBar;
    private Thread t;
    private String username;
    private String password;
    private final Activity activity;
    private final Handler progressBarbHandler;
    private String errordetails;
    private String WS_IP = CommonProperties.getInstance().getWS_IP();
    private final ArrayAdapter<String> adapter;

    public SaTask(Activity activity, Handler progressBarbHandler, ArrayAdapter<String> adapter) {
        this.activity = activity;
        this.progressBarbHandler = progressBarbHandler;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        progressBar = new ProgressDialog(activity);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.setTitle("Please wait while contacting server ...");
        progressBar.show();

        t = new Thread(new Runnable() {
            int progressBarStatus = 0;

            public void run() {
                try {
                    while (true) {
                        Thread.sleep(100);

                        progressBarbHandler.post(new Runnable() {
                            public void run() {
                                progressBar.setProgress(progressBarStatus);
                            }
                        });

                        progressBarStatus = (progressBarStatus + 5) % 100;
                    }
                } catch (InterruptedException e) {
                }
            }
        }
        );

        t.start();
    }

    @Override
    protected Sa[] doInBackground(String... params) {
        try {
            try {
                username = params[0];
                password = params[1];
                String url = "http://" + WS_IP + "/myresource/sastatusmobile?username=" + username + "&password=" + password;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<SaList> all = restTemplate.getForEntity(url, SaList.class);

                // Sa[] array = all.getBody().getSa().toArray(new Sa[all.getBody().getSa().size()]);
                Sa[] array = all.getBody().getSa();
                return array;
            } catch (Exception e) {
                errordetails = e.getMessage();
                e.printStackTrace();
                return null;
            }
        } catch (Exception e) {
            Log.e("HttpRequestTask", e.getMessage(), e);
            return null;
        }
    }


    @Override
    protected void onPostExecute(Sa[] result) {
        t.interrupt();
        progressBar.dismiss();

        if (result != null && adapter != null) {
            adapter.clear();
            adapter.add("Choose one from below: ");
            for (Sa s : result) {
                adapter.add(s.getDevicename() + "\n" +s.getHash());
            }
            Toast.makeText(activity.getBaseContext(), "SaTask success! ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity.getBaseContext(), "SaTask has failed! " + errordetails, Toast.LENGTH_LONG).show();
        }
    }
}
