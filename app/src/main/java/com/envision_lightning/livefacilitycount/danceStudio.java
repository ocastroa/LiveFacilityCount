package com.envision_lightning.livefacilitycount;

import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class danceStudio extends AppCompatActivity {

    private static TextView danceStudioTextView;
    private static TextView updateTextView;

    LiveFacilityCountsOperations mydb;
    Cursor retrieve;
    String extract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dance_studio);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if(info == null){
            Toast.makeText(this, "Internet Connection is Offline ", Toast.LENGTH_SHORT).show();
        }

        mydb = new LiveFacilityCountsOperations(this);

        String date = new SimpleDateFormat("MMM dd, yyyy").format(new Date());
        TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
        dateTextView.setText(date);

        chooseData();

        choseUpdate();
    }

    public void chooseData() {
      retrieve = mydb.retrieveData(5);
        danceStudioTextView = (TextView) findViewById(R.id.danceStudioTextView);

        extract = retrieve.getString(2);

        if (extract.equals("CLOSED")) {
            danceStudioTextView.setText("CLOSED");
            }
        else {
            danceStudioTextView.setText(extract + " /40");
            }
    }

    public void choseUpdate() {
        retrieve = mydb.retrieveData(5);
        updateTextView = (TextView) findViewById(R.id.updateTextView);

        extract = retrieve.getString(3);

        updateTextView.setText(extract);

        retrieve.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
            switch (item.getItemId()) {
////            // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    Intent intent = NavUtils.getParentActivityIntent(this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    NavUtils.navigateUpTo(this, intent);
                    return true;
            }
            return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        mydb.close();
        super.onStop();
    }
}
