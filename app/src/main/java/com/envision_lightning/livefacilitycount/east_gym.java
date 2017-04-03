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

public class east_gym extends AppCompatActivity {

    private static TextView eastGymTextView;
    private static TextView updateTextView;

    LiveFacilityCountsOperations mydb;
    Cursor retrieve;
    String extract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_east_gym);

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE); // check if phone is connected to network
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

    public void chooseData(){ // retrieve data from number of participants in database
        retrieve = mydb.retrieveData(1); // 1 is row 1
        eastGymTextView = (TextView) findViewById(R.id.eastGymTextView);

        extract = retrieve.getString(2); // 2 is column 2

        if(extract.equals("CLOSED")){
            eastGymTextView.setText("CLOSED");            }
        else {
            eastGymTextView.setText(extract +" /100");
            }
        }

    public void choseUpdate(){ // retrieve date from last update in database
        retrieve = mydb.retrieveData(1);
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
