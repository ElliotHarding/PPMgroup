package com.firstexample.elliot.application1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class accountSettingsActivity extends AppCompatActivity {

    databaseHelper helper = new databaseHelper(this);

    static final String preferences = "localPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //try refresh db
        try{
            helper.refreshDatabase();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error refreshing database! Try reloading page.", Toast.LENGTH_LONG).show();
        }

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean result = helper.checkPopulated("contacts");
                if(!result){
                    Toast.makeText(getApplicationContext(), "No access to username database, check network and try again", Toast.LENGTH_LONG).show();
                }else{

                    TextView username = findViewById(R.id.nameInputText);
                    TextView password = findViewById(R.id.passwordInputText);
                    String errorReason = "";
                    boolean validUser = true;

                    //check username exists
                    if(!(helper.usernameExists(username.getText().toString().toLowerCase()))){
                        errorReason = "Username does not exist. ";
                        validUser = false;
                    }

                    //check username to password
                    if(validUser && !(password.getText().toString().equals(helper.findPassword(username.getText().toString())))){
                        errorReason += "Incorrect password";
                        validUser = false;
                    }

                    if(!validUser){
                        Toast.makeText(getApplicationContext(), errorReason, Toast.LENGTH_LONG).show();
                    }else {
                        //log user in
                        SharedPreferences.Editor editor = getSharedPreferences(preferences, MODE_PRIVATE).edit();
                        editor.putString("login", username.getText().toString().toLowerCase());
                        editor.commit();

                        //notify user
                        Toast.makeText(getApplicationContext(), "Singed In!", Toast.LENGTH_LONG).show();

                        //return to home
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);

                    }
                }
            }
        });

        Button signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //log user out
                SharedPreferences.Editor editor = getSharedPreferences(preferences, MODE_PRIVATE).edit();
                editor.putString("login", null);
                editor.commit();

                //notify user
                Toast.makeText(getApplicationContext(), "Singed Out!", Toast.LENGTH_LONG).show();
            }
        });

        Button signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), signUpActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.homeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        (findViewById(R.id.accountOptionsButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToUrl("http://helpforums.gearhostpreview.com/register.aspx");
            }
        });


    }
    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }
}
