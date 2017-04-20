package com.timothyking.uptime;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private String alertDay;

    public void onRefreshClick (View view) {
        setUptime();
    }

    public void onExitClick (View view) {
        this.finishAffinity();
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

        // Log.i("Hours", df.format(hours));
        // Log.i("Days", df.format(days));

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

        // If alertDay pref is empty, create a default of 7
        if (alertDay == "") {
            sharedPreferences.edit().putString("alertday", "7").apply();
            Log.i("New alertday", "7");

        } else {
            Log.i("Existing alertday", alertDay);
        }

        setUptime();
    }
}
