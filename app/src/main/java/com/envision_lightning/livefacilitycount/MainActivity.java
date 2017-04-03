package com.envision_lightning.livefacilitycount;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This application uses some snippets of code found from open source. The developer gives thanks to the individuals that provided help through open source websites such as Stack Overflow.
 */

public class MainActivity extends AppCompatActivity {

    // global variables
    private static Button button;
    private static Button button2;
    private static Button button3;
    private static Button button5;
    private static Button button6;
    private static Button button7;
    private static Button button8;
    private static Button button9;
    private static Button button10;

    LiveFacilityCountsOperations mydb  = new LiveFacilityCountsOperations(this);
    String[] str1;
    String[] str;
    String[] str2;
    private Document doc;

    SharedPreferences pref = null;
    private static final String PREF_NAME = "live-facility-counts";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        findNetwork(); // find if phone is connected to network

        final SwipeRefreshLayout swipe = (SwipeRefreshLayout) findViewById(R.id.main_layout);
        swipe.setColorSchemeResources(android.R.color.holo_blue_dark,android.R.color.holo_red_light, android.R.color.holo_orange_light);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh(){
                swipe.setRefreshing(true);
                Log.d("Swipe", "Updating content");
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipe.setRefreshing(false);
                        mydb.open();
                        mydb.deleteAll();

                        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
                        jsoupAsyncTask.execute();
                        Toast.makeText(MainActivity.this,
                                "Live Facility Counts Updated", Toast.LENGTH_SHORT).show();
                    }
                }, 3000);
            }

        });
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            String htmlUrl = "https://docs.google.com/spreadsheets/d/1o1lQ6FFqr6RALPZ6I48cuWDd1clrPOlks8f3sWKx-9s/pubhtml?gid=217361177&amp;single=true";
            try {
                doc = Jsoup.connect(htmlUrl).timeout(0).get();
                Elements elements = doc.select("td.s10"); // selects # of participants
                Elements elements2 = doc.select("td.s8"); // selects first # of participant
                Elements elements3 = doc.select("td.s9"); // selects the last check-in

                // convert to text
                String foo = elements.text();
                String foo2 = elements2.text();
                String foo3 = elements3.text();

                //split the string sentence into array of strings
                str1 = foo.split(" ");
                str = foo2.split(" ");
                str2 = foo3.replace(",", " ").replace(":", " ").split(" "); // replace the colons with empty string

                // insert data into data table
                mydb.insertData("East Gym", str[0].toString(), str2[0] + ", " + str2[2] + " " + str2[3] + ", " + str2[5] + " at  " + str2[7] + ":" + str2[8].toString() + " " + str2[10].toString());
                mydb.insertData("Pool", str1[0].toString(), str2[11] + ", " + str2[13] + " " + str2[14] + ", " + str2[16] + " at  " + str2[18].toString() + ":" + str2[19].toString() + " " + str2[21].toString());
                mydb.insertData("Martial Arts Room", str1[1].toString(), str2[22] + ", " + str2[24] + " " + str2[25] + ", " + str2[27] + " at " + str2[29].toString() + ":" + str2[30].toString() + " " + str2[32].toString());
                mydb.insertData("Activities Room", str1[2].toString(), str2[33] + ", " + str2[35] + " " + str2[36] + ", " + str2[38] + " at " + str2[40] + ":" + str2[41].toString() + " " + str2[43].toString());
                mydb.insertData("Dance Studio", str1[3].toString(), str2[44] + ", " + str2[46] + " " + str2[47] + ", " + str2[49] + " at " + str2[51] + ":" + str2[52].toString() + " " + str2[54].toString());
                mydb.insertData("Racquetball Courts", str1[4].toString(), str2[55] + ", " + str2[57] + " " + str2[58] + ", " + str2[60] + " at " + str2[62] + ":" + str2[63].toString() + " " + str2[65].toString());
                mydb.insertData("Multi-Purpose Room", str1[5].toString(), str2[66] + ", " + str2[68] + " " + str2[69] + ", " + str2[71] + " at " + str2[73] + ":" + str2[74].toString() + " " + str2[76].toString());
                mydb.insertData("Wellness 1st Floor", str1[6].toString(), str2[77] + ", " + str2[79] + " " + str2[80] + ", " + str2[82] + " at " + str2[84] + ":" + str2[85].toString() + " " + str2[87].toString());
                mydb.insertData("Wellness 2nd Floor", str1[7].toString(), str2[88] + ", " + str2[90] + " " + str2[91] + ", " + str2[93] + " at " + str2[95] + ":" + str2[96].toString() + " " + str2[98].toString());
            } catch (IOException ex) {
                // TODO Auto-generated catch block
                Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE, null, ex);
            }

            Log.d("Swipe finish", "Content updated");
            return null;
        }
    }


    // when button is click activity will change to respective activities
    private void onClickButtonListener(){

        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button5 = (Button) findViewById(R.id.button5);
        button6 = (Button) findViewById(R.id.button6);
        button7 = (Button) findViewById(R.id.button7);
        button8 = (Button) findViewById(R.id.button8);
        button9 = (Button) findViewById(R.id.button9);
        button10 = (Button) findViewById(R.id.button10);

        button.setOnClickListener(
            new View.OnClickListener(){
                public void onClick(View v){
                    Intent intent = new Intent(MainActivity.this,wellness_second_floor.class);
                    startActivity(intent);
                    }
                }
        );

        button2.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,wellness_first_floor.class);
                        startActivity(intent);
                    }
                }
        );

        button3.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,east_gym.class);
                        startActivity(intent);
                    }
                }
        );

        button5.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,multi_purpose_room.class);
                        startActivity(intent);
                    }
                }
        );

        button6.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,danceStudio.class);
                        startActivity(intent);
                    }
                }
        );

        button7.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,martial_arts_room.class);
                        startActivity(intent);
                    }
                }
        );

        button8.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,racquetball_courts.class);
                        startActivity(intent);
                    }
                }
        );

        button9.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this, pool.class);
                      startActivity(intent);
                    }
                }
        );

        button10.setOnClickListener(
                new View.OnClickListener(){
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this,activities_room.class);
                        startActivity(intent);
                    }
                }
        );

    }

    public void findNetwork(){
        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if(info == null){
            Toast.makeText(this, "Internet connection is offline. Database not updated. Connect to the internet and restart the app", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch(item.getItemId()){
            case R.id.share: // share with friends through social media
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, "");
                try {
                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
                    break;
                } catch(ActivityNotFoundException e){Toast.makeText(MainActivity.this, "There are no social media apps installed.", Toast.LENGTH_SHORT).show();
                  }

            case R.id.rate: // rate the app
                Uri uri = Uri.parse("market://details?id=" + getPackageName());
                Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                // To count with Play market backstack, After pressing back button,
                // to taken back to our application, we need to add following flags to intent.
                goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                        Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                        Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                try {
                    startActivity(goToMarket);
                    break;
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }

            case R.id.about:
                Intent newIntent = new Intent(MainActivity.this, about.class );
                startActivity(newIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        onClickButtonListener();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume(){
        super.onResume();
        // Checks if app is opened for first time. If it is then display toast and then make boolean to false
        if (pref.getBoolean("firstrun", true)) {
            Toast.makeText(MainActivity.this,
                    "Swipe down to update Live Facility Counts", Toast.LENGTH_LONG).show();
            pref.edit().putBoolean("firstrun", false).commit();
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
    }


    public void onDestroy(){
        super.onDestroy();
        mydb.close();
    }
}
