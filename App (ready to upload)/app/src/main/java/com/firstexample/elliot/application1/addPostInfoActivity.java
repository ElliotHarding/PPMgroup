package com.firstexample.elliot.application1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class addPostInfoActivity extends AppCompatActivity {

    databaseHelper helper = new databaseHelper(this);

    static final String preferences = "localPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post_info);

        //try refresh db
        try{
            helper.refreshDatabase();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error refreshing database! Try reloading page.", Toast.LENGTH_LONG).show();
        }

        (findViewById(R.id.returnHomeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), forumsActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.addButtonInfo)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get users name
                String uName = "default";
                SharedPreferences prefs = getSharedPreferences(preferences, MODE_PRIVATE);
                String restoredText = prefs.getString("login", null);
                if (restoredText != null) {
                    uName = restoredText;
                }

                if(Objects.equals("default",uName)){
                    Toast.makeText(getApplicationContext(), "You must be logged in to reply to posts!", Toast.LENGTH_LONG).show();
                }else{
                    EditText content = findViewById(R.id.inputReply);

                    if(content.getText().length() < 1){
                        Toast.makeText(getApplicationContext(), "Content is too short.", Toast.LENGTH_LONG).show();
                    }else{
                        //get current date
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dNow = new Date();
                        String date = sdfDate.format(dNow);

                        int postIdToAddTo = Integer.parseInt(getIntent().getStringExtra("postIdToAddTo"));
                        postInfo pi = new postInfo(postIdToAddTo,content.getText().toString(),date,uName);

                        try{
                            helper.addPostInfo(pi,true);
                            Intent intent = new Intent(view.getContext(), ForumPostsActivity.class);
                            intent.putExtra("postNumber",Integer.toString(postIdToAddTo));
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Failed to add reply!", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });
    }
}
