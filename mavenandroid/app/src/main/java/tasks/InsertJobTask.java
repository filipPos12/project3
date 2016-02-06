package tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import scan.di.uoa.gr.exampleappsoftdev.InsertJobActivity;
import scan.di.uoa.gr.exampleappsoftdev.IntroActivity;
import shared.AccountProperties;
import shared.CommonProperties;

/**
 * Created by filippos on 21/1/2016.
 */
public class InsertJobTask extends AsyncTask<String, Void, Integer> {
    private ProgressDialog progressBar;
    private Thread t;

    private String username;            // username, password, sahash, parameters, seconds, loop
    private String password;
    private String sahash;
    private String parameters;
    private String seconds;
    private String loop;

    private final Activity activity;
    private final Handler progressBarbHandler;
    private String errordetails;
    private String WS_IP = CommonProperties.getInstance().getWS_IP();

    public InsertJobTask(Activity activity, Handler progressBarbHandler) {
        this.activity = activity;
        this.progressBarbHandler = progressBarbHandler;
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
    protected Integer doInBackground(String... params) {
        try {
            // http://localhost:9998/myresource/addnmapjobmobile?username=bob&password=bob&sahash=16d618bd315c5c31988ec083ee6f9224648eb54a&parameters=bla&seconds=10&loop=true

            try {
                username = params[0];
                password = params[1];
                sahash = params[2];
                parameters = params[3];
                seconds = params[4];
                loop = params[5];

                String url = "http://" + WS_IP + "/myresource/addnmapjobmobile?username=" + username + "&password=" + password+ "&sahash="  + sahash+ "&parameters=" + parameters+ "&seconds=" + seconds+ "&loop=" + loop;
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                ResponseEntity<String> s = restTemplate.getForEntity(url, String.class);
                int code = s.getStatusCode().value();

                Thread.sleep(2000);

                Log.d("Myapp", url);

                return code;
            } catch (Exception e) {
                errordetails = e.getMessage();
                return 500;
            }
        } catch (Exception e) {
            Log.e("HttpRequestTask", e.getMessage(), e);
            return 500;
        }
    }


    @Override
    protected void onPostExecute(Integer result) {
        t.interrupt();
        progressBar.dismiss();

        if (result == 200) {
            Toast.makeText(activity.getBaseContext(), "Job was inserted successfully! ", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(activity.getBaseContext(), "Job insert has failed! " + errordetails, Toast.LENGTH_LONG).show();
        }
    }
}
