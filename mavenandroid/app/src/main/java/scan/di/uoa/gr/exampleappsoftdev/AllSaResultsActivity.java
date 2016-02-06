package scan.di.uoa.gr.exampleappsoftdev;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import shared.AccountProperties;
import tasks.SaResultTask;
import tasks.SaTask;

public class AllSaResultsActivity extends ActionBarActivity {
    private Button UpdateAllResultsButton;
    private Handler progressBarbHandler = new Handler();
    private TextView allResultsTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_sa_results);

        UpdateAllResultsButton = (Button) findViewById(R.id.UpdateAllResultsButton);
        allResultsTextview = (TextView) findViewById(R.id.allResultsTextview);

        UpdateAllResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update();
            }
        });
    }

    private void update() {
        String username = AccountProperties.getInstance().getUsername();
        String password = AccountProperties.getInstance().getPassword();

        SaResultTask task = new SaResultTask(AllSaResultsActivity.this, progressBarbHandler, allResultsTextview);
        task.execute(username, password);
    }
}
