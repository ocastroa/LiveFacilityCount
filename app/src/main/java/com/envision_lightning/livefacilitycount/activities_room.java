package com.envision_lightning.livefacilitycount;

import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Date;

public class activities_room extends AppCompatActivity {

    private static TextView activitiesRoomTextView;
    private static TextView updateTextView;

    LiveFacilityCountsOperations mydb;
    Cursor retrieve;
    String extract;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activities_room);

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

    public void chooseData(){
        retrieve = mydb.retrieveData(4);
        activitiesRoomTextView = (TextView) findViewById(R.id.activitiesRoomTextView);

        extract = retrieve.getString(2);

        if(extract.equals("CLOSED")){
            activitiesRoomTextView.setText("CLOSED");
            }

        else {
            activitiesRoomTextView.setText(extract + " /40");
        }
    }

    public void choseUpdate(){
        retrieve = mydb.retrieveData(4);
        updateTextView = (TextView) findViewById(R.id.updateTextView);

        extract = retrieve.getString(3);

        updateTextView.setText(extract);

        retrieve.close();
    }

    @Override
    protected void onStop() {
        mydb.close();
        super.onStop();
    }

}