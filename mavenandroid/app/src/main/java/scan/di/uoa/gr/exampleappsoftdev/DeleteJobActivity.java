package scan.di.uoa.gr.exampleappsoftdev;

import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import shared.AccountProperties;
import tasks.DeleteJobTask;
import tasks.InsertJobTask;

public class DeleteJobActivity extends ActionBarActivity {
    Button deletejob;

    private Handler progressBarbHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_job);
        deletejob = (Button) findViewById(R.id.delete);

        deletejob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // http://localhost:9998/myresource/addnmapjobmobile?username=bob&password=bob&sahash=16d618bd315c5c31988ec083ee6f9224648eb54a&parameters=bla&seconds=10&loop=true

                String username = AccountProperties.getInstance().getUsername();
                String password = AccountProperties.getInstance().getPassword();
                String id = ((TextView) findViewById(R.id.jid)).getText().toString();

                DeleteJobTask task = new DeleteJobTask(DeleteJobActivity.this, progressBarbHandler);
                task.execute(username, password, id);
            }
        });
    }
}
