package tasks;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import shared.CommonProperties;

public class RegisterTask extends AsyncTask<String, Void, Integer> {
        private ProgressDialog progressBar;
        private Thread t;
        private String username;
        private String password;
        private final Activity activity;
        private final Handler progressBarbHandler;
        private String errordetails;
        private String WS_IP = CommonProperties.getInstance().getWS_IP();

        public RegisterTask(Activity activity, Handler progressBarbHandler) {
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
        protected Integer doInBackground (String...params){
            try {
                try {
                    username = params[0];
                    password = params[1];
                    String url = "http://" + WS_IP + "/myresource/registermobile?username=" + username + "&password=" + password;
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                    ResponseEntity<String> s = restTemplate.getForEntity(url, String.class);
                    int code = s.getStatusCode().value();

                    Thread.sleep(2000);

                    Log.d("Myapp", "username = " + username + "  password= " + password + " response text=" + s);

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
        protected void onPostExecute (Integer result){
            t.interrupt();
            progressBar.dismiss();

            if (result == 200 ) {
                Toast.makeText(activity.getBaseContext(), "You have registered successfully! ", Toast.LENGTH_LONG).show();
            } else {
                Log.d("MyApp", "Register Code is: " + result);
                Toast.makeText(activity.getBaseContext(), "Registration has failed! " + errordetails, Toast.LENGTH_LONG).show();
            }
        }
}
