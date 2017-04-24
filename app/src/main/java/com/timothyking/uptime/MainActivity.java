package com.timothyking.uptime;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    public static final String DEFAULT_DAYS = "7";

    private String alertDay;
    public void onRefreshClick (View view) {
        setUptime();
    }
    public void onExitClick (View view) {
        this.finishAffinity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return false;
        }
    }

    public void setUptime() {
        float secs = SystemClock.elapsedRealtime()/1000;
        float hours = (secs/60)/60;
        float days = hours/24;

        DecimalFormat df = new DecimalFormat("#####.##");
        TextView warnField = (TextView) findViewById(R.id.txtWarning);

        if (hours >= Float.valueOf(alertDay)*24) {
            warnField.setText("A restart is recommended.");
            warnField.setVisibility(View.VISIBLE);
        } else {
            warnField.setVisibility(View.GONE);
        }

        TextView hoursField = (TextView)findViewById(R.id.txtHours);
        hoursField.setText(df.format(hours) + " Hours");

        TextView dayField = (TextView)findViewById(R.id.txtDays);
        dayField.setText(df.format(days) + " Days");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.timothyking.uptime", Context.MODE_PRIVATE);
        alertDay = sharedPreferences.getString("alertday", "");

        // If alertDay pref is empty, create a default
        if (alertDay == "") {
            sharedPreferences.edit().putString("alertday", DEFAULT_DAYS).apply();
            alertDay = DEFAULT_DAYS;
            Log.i("New alertday", DEFAULT_DAYS);

        } else {
            Log.i("Existing alertday", alertDay);
        }

        setUptime();
    }
}
