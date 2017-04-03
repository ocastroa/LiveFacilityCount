package com.envision_lightning.livefacilitycount;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by ocastroa on 7/11/16.
 */
public class about extends AppCompatActivity {

    private static TextView aboutInfo;
    private static TextView licenseInfoTwo;
    private static TextView setLicenseTwo;
    private static TextView app_icon;
    private static TextView app_icon_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.license);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.80), (int)(height * .47)); // sets pop up window within certain x & y dimensions

        aboutInfo = (TextView) findViewById(R.id.aboutInfo);
        aboutInfo.setText("Live Facility Counts extracts data from the OPERS website when the app is first opened. In order to update all the values after the app has been opened, pull down the Main Menu and wait a few seconds. All values will then be updated. Please note that the database is cached, so DO NOT clear cache!");

        setLicenseTwo = (TextView) findViewById(R.id.setLicenseTwo);
        setLicenseTwo.setText(" jsoup 1.9.2");

        licenseInfoTwo = (TextView) findViewById(R.id.licenseInfoTwo);
        licenseInfoTwo.setText("Released by Jonathan Hedley under the MIT license.");

        app_icon = (TextView) findViewById(R.id.app_icon);
        app_icon.setText(" App Icon");

        app_icon_two = (TextView) findViewById(R.id.app_icon_two);
        app_icon_two.setClickable(true);
        app_icon_two.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "<a href='https://icons8.com/'> icons8.com </a>";
        app_icon_two.setText(Html.fromHtml(text));
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
}
