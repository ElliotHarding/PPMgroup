package com.firstexample.elliot.application1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class signUpActivity extends AppCompatActivity {

    databaseHelper helper = new databaseHelper(this);

    static final String preferences = "localPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //try refresh db
        try{
            helper.refreshDatabase();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Error refreshing database! Try reloading page.", Toast.LENGTH_LONG).show();
        }

        Button signUpButton = findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean result = helper.checkPopulated("contacts");
                if(!result){
                    Toast.makeText(getApplicationContext(), "No access to username database, check network and try again", Toast.LENGTH_LONG).show();
                }else{

                    //check login & password, make sure is valid login
                    TextView username = findViewById(R.id.nameInputText);
                    TextView password = findViewById(R.id.passwordInputText);
                    String errorReason = "";
                    boolean validUserOrPass = true;


                    //check username
                    if(username.getText().toString().length() > 2 && username.getText().toString().length() < 20){
                        if(helper.usernameExists(username.getText().toString().toLowerCase())){
                            errorReason += "Username already in use. ";
                            validUserOrPass = false;
                        }
                    }else{
                        errorReason += "Username too big/small. ";
                        validUserOrPass = false;
                    }

                    if(password.getText().toString().length() < 2 || password.getText().toString().length() > 20){
                        errorReason += "Password is too big/small";
                        validUserOrPass = false;
                    }

                    if(!validUserOrPass){
                        Toast.makeText(getApplicationContext(), errorReason, Toast.LENGTH_LONG).show();
                    }else{
                        //Sign up user
                        contact user = new contact(username.getText().toString().toLowerCase(), password.getText().toString());
                        helper.addContact(user,true);

                        //notify user
                        Toast.makeText(getApplicationContext(), "Signed Up & Signed In!", Toast.LENGTH_LONG).show();

                        //sign in user
                        SharedPreferences.Editor editor = getSharedPreferences(preferences, MODE_PRIVATE).edit();
                        editor.putString("login", username.getText().toString().toLowerCase());
                        editor.commit();

                        //return back to home page
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        startActivity(intent);
                    }

                }


            }
        });

        (findViewById(R.id.homeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
