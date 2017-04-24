package com.timothyking.uptime;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    public void onCancel(View view) {
        finish();
    }

    public void onSave(View view) {
        EditText editDays = (EditText) findViewById(R.id.numDays);
        String alertDays = editDays.getText().toString();

        // Check for valid days
        if (alertDays.isEmpty()) {
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
        } else if (Float.valueOf(alertDays) > 999) {
            Toast.makeText(this, "Days must be less than 1,000", Toast.LENGTH_SHORT).show();
        } else {
            SharedPreferences sharedPreferences = this.getSharedPreferences("com.timothyking.uptime", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString("alertday", alertDays).apply();
            Log.i("AlertDays", alertDays);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText txtDays = (EditText) findViewById(R.id.numDays);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.timothyking.uptime", Context.MODE_PRIVATE);
        String alertDay = sharedPreferences.getString("alertday", "");

        // If alertDay pref is empty, create a default of 7
        if (alertDay == "") {
            txtDays.setText("7");

        } else {
            txtDays.setText(alertDay);
        }
    }
}
