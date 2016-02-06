package tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import scan.di.uoa.gr.exampleappsoftdev.AllSaResultsActivity;
import scan.di.uoa.gr.exampleappsoftdev.SaStatusActivity;
import serverdbclasses.Result;
import serverdbclasses.ResultList;
import serverdbclasses.Sa;
import serverdbclasses.SaList;
import serverdbclasses.Result;
import shared.CommonProperties;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import scan.di.uoa.gr.exampleappsoftdev.SaResultsActivity;
import scan.di.uoa.gr.exampleappsoftdev.SaStatusActivity;
import serverdbclasses.Sa;
import serverdbclasses.SaList;
import shared.CommonProperties;

/**
 * Created by filippos on 25/1/2016.
 */
public class SaResultTask extends AsyncTask<String, Void, Result[]> {
    private ProgressDialog progressBar;
    private Thread t;
    private String username;
    private String password;
    private String hash;
    private final Activity activity;
    private final Handler progressBarbHandler;
    private String errordetails;
    private String WS_IP = CommonProperties.getInstance().getWS_IP();
    private TextView adapter;

    public SaResultTask(Activity activity, Handler progressBarbHandler, TextView adapter) {
        this.activity = activity;
        this.progressBarbHandler = progressBarbHandler;
        this.adapter=adapter;
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
    protected Result[] doInBackground(String... params) {
        try {
            try {
                username = params[0];
                password = params[1];
                if (params.length > 2) {
                    hash = params[2];
                } else {
                    hash = null;
                }
                String url;
                if (hash != null) {
                    url = "http://" + WS_IP + "/myresource/saresultmobile?username=" + username + "&password=" + password + "&hash=" + hash;
                } else {
                    url = "http://" + WS_IP + "/myresource/saresultmobile?username=" + username + "&password=" + password;
                }

                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ResponseEntity<ResultList> some = restTemplate.getForEntity(url, ResultList.class);

                try {
                    Result[] array = some.getBody().getNmapJobResult();
                    return array;
                } catch (Exception e) {
                    return null;
                }
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
    protected void onPostExecute(Result[] results) {
        t.interrupt();
        progressBar.dismiss();

        String text = "";
        if (results != null) {
            for (Result s : results) {
                text += s.getJobid() + "\n" + s.getRaw_text();
            }

            adapter.setText(text);;
            Toast.makeText(activity.getBaseContext(), "Task success! ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity.getBaseContext(), "Task has failed! " + errordetails, Toast.LENGTH_LONG).show();
        }
    }
}