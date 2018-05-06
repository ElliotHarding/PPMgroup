package com.firstexample.elliot.application1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Calendar;

import okhttp3.OkHttpClient;


//PASSWORD FOR KEYSTORE --> Forums_9

public class MainActivity extends AppCompatActivity {
    static String TAG = "MainActivity";

    static final String preferences = "localPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate: created!");


        SharedPreferences prefs = getSharedPreferences(preferences, MODE_PRIVATE);
        String restoredText = prefs.getString("login", null);
        TextView loginNameTV = findViewById(R.id.LoginName);
        if (restoredText != null) {
            loginNameTV.setText("Logged In As: " + restoredText);
        }else{
            loginNameTV.setText("Not Logged In!");
        }

        View.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (view.getId()) {

                    case R.id.accountSettingsButton:
                        intent = new Intent(view.getContext(), accountSettingsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.settingsButton:
                        intent = new Intent(view.getContext(), settingsActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.contactButton:
                        intent = new Intent(view.getContext(), contactActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.forumsButton:
                        intent = new Intent(view.getContext(), forumsActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        };

        (findViewById(R.id.settingsButton)).setOnClickListener(onClick);
        (findViewById(R.id.forumsButton)).setOnClickListener(onClick);
        (findViewById(R.id.contactButton)).setOnClickListener(onClick);
        (findViewById(R.id.accountSettingsButton)).setOnClickListener(onClick);

    }

}
