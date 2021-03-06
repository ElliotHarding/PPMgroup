package com.firstexample.elliot.application1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

public class addPostActivity extends AppCompatActivity {

    databaseHelper helper = new databaseHelper(this);

    static final String preferences = "localPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        //try refresh db
        try{
            helper.refreshDatabase();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error refreshing database! Try reloading page.", Toast.LENGTH_LONG).show();
        }

        (findViewById(R.id.homeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), forumsActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.addButton)).setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getApplicationContext(), "You must be logged in to add posts!", Toast.LENGTH_LONG).show();
                }else{

                    //check if info is filled out correctly
                    EditText subjectName = findViewById(R.id.inputSubject);
                    EditText content = findViewById(R.id.inputContent);

                    String errorString = "";

                    if (subjectName.getText().length() < 1 || subjectName.getText().length() > 45){
                        errorString += "Subject name is too long/short. ";
                    }

                    if(content.getText().length() < 1 || content.getText().length() > 200){
                        errorString += "Content is too long/short. ";
                    }

                    if(errorString != ""){
                        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_LONG).show();
                    }else{

                        //get current date
                        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date dNow = new Date();
                        String date = sdfDate.format(dNow);


                        //generate UUID()
                        int generatedIndex = 99999998;
                        while(true){
                            Random rand = new Random();
                            generatedIndex = rand.nextInt(2147483647) + 10;

                            if(helper.getPost(generatedIndex) == null){
                               break;
                            }
                        }


                        //create post
                        post p = new post(uName, content.getText().toString(), date, subjectName.getText().toString(), generatedIndex);

                        try{
                            helper.addPost(p,true);
                            Intent intent = new Intent(view.getContext(), forumsActivity.class);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(), "Failed to add post!", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });

    }
}
