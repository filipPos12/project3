package scan.di.uoa.gr.exampleappsoftdev;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import shared.AccountProperties;
import tasks.LogoutTask;
import tasks.RegisterTask;

public class IntroActivity extends ActionBarActivity {

    private Button btnViewAgents;
    private Button btnViewAllResults;
    private Button btnSaJobs;
    private Button btnSAResults;
    private Button btnAddJobs;
    private Button btnDeleteJobs;
    private Button btnCloseMenu;
    private Button btnLogout;

    private Handler progressBarbHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        btnViewAgents = (Button) findViewById(R.id.btnViewAgents);
        btnViewAllResults = (Button) findViewById(R.id.btnViewAllResults);
        btnSaJobs = (Button) findViewById(R.id.btnSaJobs);
        btnSAResults = (Button) findViewById(R.id.btnSAResults);
        btnAddJobs = (Button) findViewById(R.id.btnAddJobs);
        btnDeleteJobs = (Button) findViewById(R.id.btnDeleteJobs);
        btnCloseMenu = (Button) findViewById(R.id.btnCloseMenu);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        btnViewAgents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(IntroActivity.this, SaStatusActivity.class));
            }
        });

        btnViewAllResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, AllSaResultsActivity.class));
            }
        });

        btnSaJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, SaJobsActivity.class));
            }
        });

        btnSAResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, SaResultsActivity.class));
            }
        });

        btnAddJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, InsertJobActivity.class));
            }
        });

        btnDeleteJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, DeleteJobActivity.class));
            }
        });

        btnCloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutTask task = new LogoutTask(IntroActivity.this, progressBarbHandler);
                task.execute(AccountProperties.getInstance().getUsername(), AccountProperties.getInstance().getPassword());
            }
        });
    }
}
