package com.firstexample.elliot.application1;

/**
 * Created by Elliot on 14/12/2017.
 */

public class contact {
    private String username;
    private String password;

    contact(String username, String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername(){return username;}

    public String getPassword(){return password;}
}
