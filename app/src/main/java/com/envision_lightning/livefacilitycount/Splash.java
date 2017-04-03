package com.envision_lightning.livefacilitycount;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by ocastroa on 7/1/16.
 */
public class Splash extends AppCompatActivity {

    String[] str1;
    String[] str;
    String[] str2;
    LiveFacilityCountsOperations mydb = new LiveFacilityCountsOperations(this); // use

    private Document doc;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mydb.open();

        ConnectivityManager cManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo info = cManager.getActiveNetworkInfo();
        if(info == null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            mydb.deleteAll();
            JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
            jsoupAsyncTask.execute();
        }
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        protected Void doInBackground(Void... params) {
            String htmlUrl = "https://docs.google.com/spreadsheets/d/1o1lQ6FFqr6RALPZ6I48cuWDd1clrPOlks8f3sWKx-9s/pubhtml?gid=217361177&amp;single=true";
            try {
                for (int i = 0; i < 2; i++) { // set timer to 2 seconds
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.interrupted();
                    }
                }

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
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mydb.close();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
