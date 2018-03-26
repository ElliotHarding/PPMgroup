package com.firstexample.elliot.application1;

public class post {

    private int id;
    private String username;
    private String postString;
    private String postDate;
    private String postSubject;

    public int getId() {return id;}
    public String getUsername() {return username;}
    public String getPostString() {return postString;}
    public String getPostDate() {return postDate;}
    public String getPostSubject() {return postSubject;}

    post(String username, String postString, String postDate, String postSubject, int id){
        this.username = username;
        this.postString = postString;
        this.postDate = postDate;
        this.postSubject = postSubject;
        this.id = id;
    }


}
