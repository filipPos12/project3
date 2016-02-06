package scan.di.uoa.gr.exampleappsoftdev;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import shared.AccountProperties;
//import tasks.SaResultTask;
import tasks.SaResultTask;
import tasks.SaTask;

public class SaResultsActivity extends ActionBarActivity {
    private Button update;
    private Spinner spinner;
    private Handler progressBarbHandler = new Handler();
    private TextView resultTextView;

    private String[] data = new String[] {"Please click update ..."};
    private ArrayList<String> lst = new ArrayList<String>(Arrays.asList(data));
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sa_results);

        update = (Button) findViewById(R.id.ResultButton3);
        spinner = (Spinner) findViewById(R.id.saResultsSpinner);
        resultTextView = (TextView) findViewById(R.id.saResultsTextView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lst);
        spinner.setAdapter(adapter);

        updateSa();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateResult();
            }
        });
    }

    private void updateSa() {
        String username = AccountProperties.getInstance().getUsername();
        String password = AccountProperties.getInstance().getPassword();

        SaTask task = new SaTask(SaResultsActivity.this, progressBarbHandler, adapter);
        task.execute(username, password);
    }

    private void updateResult() {
        String username = AccountProperties.getInstance().getUsername();
        String password = AccountProperties.getInstance().getPassword();
        String hash = null;

        if (spinner.getSelectedItemPosition() > 0) {
            hash = adapter.getItem(spinner.getSelectedItemPosition()).split("\n")[1];
        } else {
            return;
        }

        SaResultTask task = new SaResultTask(SaResultsActivity.this, progressBarbHandler, resultTextView);
        task.execute(username, password, hash);
    }
}
