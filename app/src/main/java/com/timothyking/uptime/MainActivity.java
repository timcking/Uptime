package com.timothyking.uptime;

import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

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

        // 7 days = 168 hours
        if (hours >= 168) {
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

        setUptime();

    }
}
