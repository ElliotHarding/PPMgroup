package com.firstexample.elliot.application1;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class databaseHelper extends SQLiteOpenHelper {

    //database properties:
    private static int databaseVersion = 1;
    private static String databaseName = "forums.db";
    SQLiteDatabase db;

    //tables in database:
    private static String contactTableName = "contacts";
    private static String postsTableName = "posts";
    private static String postsInfoTableName = "postInfo";

    //Constructor:
    public databaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    //Initialization
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(
                "CREATE TABLE IF NOT EXISTS " + contactTableName + " (" +
                "username TEXT PRIMARY KEY, password TEXT NOT NULL);"
        );
        database.execSQL(
                "CREATE TABLE IF NOT EXISTS " + postsTableName + " (" +
                "username TEXT," +
                "postString TEXT," +
                "postDate DATE,"  +
                "postSubject TEXT," +
                "postID INT PRIMARY KEY);"

        );
        database.execSQL(
                "CREATE TABLE IF NOT EXISTS " + postsInfoTableName + " (" +
                "replyString TEXT," +
                "replyDate DATE,"  +
                "replyUsername TEXT," +
                "postID INT," +
                "PRIMARY KEY(replyString,postID));"
        );
        this.db = database;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + contactTableName);
        db.execSQL("DROP TABLE IF EXISTS " + postsTableName);
        db.execSQL("DROP TABLE IF EXISTS " + postsInfoTableName);
        this.onCreate(db);
    }

    public void refreshDatabase(){
        //in order to refresh database, all local data is deleted, then retrived from host
        db = this.getWritableDatabase();

        //check that the table exists as may be first time & cannot delete from a non existing table
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + contactTableName + " (" +
                        "username TEXT PRIMARY KEY, password TEXT NOT NULL);"
        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + postsTableName + " (" +
                        "username TEXT," +
                        "postString TEXT," +
                        "postDate DATE,"  +
                        "postSubject TEXT," +
                        "postID INT PRIMARY KEY);"

        );
        db.execSQL(
                "CREATE TABLE IF NOT EXISTS " + postsInfoTableName + " (" +
                        "replyString TEXT," +
                        "replyDate DATE,"  +
                        "replyUsername TEXT," +
                        "postID INT," +
                        "PRIMARY KEY(replyString,postID));"
        );

        //deletion of old data
        db.execSQL("delete from " + contactTableName);
        db.execSQL("delete from " + postsTableName);
        db.execSQL("delete from " + postsInfoTableName);


        //retrival of new data into local database
        new getData().execute();
    }

    //checks if local database has been populated with data
    public boolean checkPopulated(String tableName){
        try {
            db = this.getWritableDatabase();

            String query = "SELECT COUNT(*) FROM " + tableName;
            Cursor cursor = db.rawQuery(query,null);

            boolean b = cursor.moveToFirst();
            String s = cursor.getString(0);

            cursor.close();

            if(Integer.valueOf(s) > 0){
                return true;
            }
            return false;

        }catch (Exception e){
            throw e;
        }
    }

    public void addContact(contact c, boolean upload){
    try {
        //get local database
        db = this.getWritableDatabase();

        //insert contact details into values
        ContentValues values = new ContentValues();
        values.put("username", c.getUsername());
        values.put("password", c.getPassword());

        //insert values into local database, contact table
        db.insert(contactTableName, null, values);

        //check if we push contact to online host
        if (upload) {
            //execte upload thread
            new uploadToDatabaseThread(c).execute();
        }
    }catch (Exception e){
        throw e;
    }
    }

    public void addPost(post p, boolean upload){
    try{
        //get local database
        db = this.getWritableDatabase();

        //insert contact details into values
        ContentValues values = new ContentValues();
        values.put("username", p.getUsername());
        values.put("postString", p.getPostString());
        values.put("postDate", p.getPostDate());
        values.put("postSubject", p.getPostSubject());
        values.put("postID", p.getId());

        //insert values into local database, contact table
        db.insert(postsTableName, null, values);

        //check if we push contact to online host
        if(upload){
            //execte upload thread
            new uploadToDatabaseThread(p).execute();
        }
    }catch (Exception e){
        throw e;
    }
    }

    public void addPostInfo(postInfo pi, boolean upload){
    try{
        //get local database
        db = this.getWritableDatabase();

        //insert contact details into values
        ContentValues values = new ContentValues();
        values.put("postID", pi.getPostID());
        values.put("replyString", pi.getReplyString());
        values.put("replyDate", pi.getReplyDate());
        values.put("replyUsername", pi.getReplyUsername());

        //insert values into local database, contact table
        db.insert(postsInfoTableName, null, values);

        //check if we push contact to online host
        if(upload){
            //execte upload thread
            new uploadToDatabaseThread(pi).execute();
        }
    }catch (Exception e){
        throw e;
    }
    }

    //check if username exists
    public boolean usernameExists(String username){
    try{
        //get local database
        db = this.getReadableDatabase();

        //run sql select
        String query = "SELECT username, password FROM " + contactTableName;
        Cursor cursor = db.rawQuery(query,null);

        //run through select result, checking if username is within results
        //return if so
        if(cursor.moveToFirst()){
            do{
                if(username.equals(cursor.getString(0))){
                    cursor.close();
                   return true;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }catch (Exception e){
        throw e;
    }
    }

    //returns password of given username
    public String findPassword(String username){
    try{
        //get local database
        db = this.getReadableDatabase();

        //preform select on contacts table
        String query = "SELECT username, password FROM " + contactTableName;
        Cursor cursor = db.rawQuery(query,null);

        //return password, by checking through select results
        String usr, pswd;
        pswd = "not found";
        if(cursor.moveToFirst()){
            do{
                usr = cursor.getString(0);
                if(username.equals(usr.toString())){
                    pswd = cursor.getString(1);
                    break;
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return pswd;
    }catch (Exception e){
        throw e;
    }
    }

    public post getPost(int postID){

        try{
        //get local database
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + postsTableName + " WHERE postID =" + postID;
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String postString  = cursor.getString(cursor.getColumnIndex("postString"));
            String postDate = cursor.getString(cursor.getColumnIndex("postDate"));
            String postSubject = cursor.getString(cursor.getColumnIndex("postSubject"));
            cursor.close();
            return new post(username, postString, postDate, postSubject, postID);
        }
        cursor.close();

        }catch (Exception e){
            throw e;
        }

        return null;
    }


    public ArrayList<postInfo> getPostInfo(int postID){
    try{
        //get local database
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + postsInfoTableName + " WHERE postID =" + postID + " ORDER BY replyDate ASC";
        Cursor cursor = db.rawQuery(query,null);

        ArrayList<postInfo> infoList = new ArrayList<postInfo>();

        if(cursor.moveToFirst()) {
            do{
                infoList.add(new postInfo(postID,cursor.getString(cursor.getColumnIndex("replyString")),cursor.getString(cursor.getColumnIndex("replyDate")),cursor.getString(cursor.getColumnIndex("replyUsername"))));
            }while (cursor.moveToNext());
        }else{
            infoList.add(new postInfo(0,"","",""));
        }
        cursor.close();
        
        return infoList;
    }catch (Exception e){
        throw e;
    }
    }

    //get data
    InputStream isrTable1, isrTable2, isrTable3;
    String result1, result2, result3;

    public ArrayList<post> getAllPosts() {
        ArrayList<post> allPosts = new ArrayList<post>();

        //get local database
        db = this.getReadableDatabase();

        String query = "SELECT * FROM " + postsTableName + " ORDER BY postDate DESC";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()) {
            do{
                allPosts.add(new post(cursor.getString(cursor.getColumnIndex("username")),cursor.getString(cursor.getColumnIndex("postString")),cursor.getString(cursor.getColumnIndex("postDate")),cursor.getString(cursor.getColumnIndex("postSubject")),Integer.parseInt(cursor.getString(cursor.getColumnIndex("postID")))));
            }while (cursor.moveToNext());
        }else{
            allPosts.add(new post("s","s","s","",0));
        }
        cursor.close();

        return allPosts;
    }

    /*
      |-----------------------------------------------------------
      |  NAME    : getData
      |  Purpose : THREAD TO FETCH DATA FROM HOST WEBSITE
      |
      |  Steps   : 1. connect
      |            2. convert response
      |            3. add to database
      |
      |-----------------------------------------------------------
    */
    private class getData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... params) {
            result1 = "";
            result2 = "";
            result3 = "";
            isrTable1 = null;
            isrTable2 = null;
            isrTable3 = null;


            // STEP 1. connect and retrieve entity isr
            try {
                HttpClient httpclient;
                HttpPost httppost;

                //retrieve contacts table
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://helpforums.gearhostpreview.com//PullContacts.php");
                isrTable1 =  httpclient.execute(httppost).getEntity().getContent();

                //retrieve posts table
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://helpforums.gearhostpreview.com//PullPosts.php");
                isrTable2 =  httpclient.execute(httppost).getEntity().getContent();

                //retrieve postInfo table
                httpclient = new DefaultHttpClient();
                httppost = new HttpPost("http://helpforums.gearhostpreview.com//PullPostInfo.php");
                isrTable3 =  httpclient.execute(httppost).getEntity().getContent();


            } catch (Exception e) {
                Log.e("gettingData: ", "Error in http connection " + e.toString());
            }


            //STEP 2. convert response to string
            try {
                StringBuilder sb;
                String line;

                //convert posts table
                sb = new StringBuilder();
                line = null;
                while ((line = new BufferedReader(new InputStreamReader(isrTable2, "iso-8859-1"), 8).readLine()) != null) {
                    sb.append(line + "\n");
                }
                isrTable2.close();
                result2 = sb.toString();

                //convert contacts table
                sb = new StringBuilder();
                line = null;
                while ((line = new BufferedReader(new InputStreamReader(isrTable1, "iso-8859-1"), 8).readLine()) != null) {
                    sb.append(line + "\n");
                }
                isrTable1.close();
                result1 = sb.toString();

                //convert postInfo table
                sb = new StringBuilder();
                line = null;
                while ((line = new BufferedReader(new InputStreamReader(isrTable3, "iso-8859-1"), 8).readLine()) != null) {
                    sb.append(line + "\n");
                }
                isrTable3.close();
                result3 = sb.toString();


            } catch (Exception e) {
                Log.e("log_tag", "Error  converting result " + e.toString());
            }

            //STEP 3. add to database
            try {

                //add contacts table to local
                JSONArray jArray = new JSONArray(result1);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    contact c = new contact(json.getString("username"),json.getString("password"));
                    addContact(c,false);
                }

                //add posts table to local
                jArray = new JSONArray(result2);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    post p = new post(json.getString("username"),json.getString("postString"),json.getString("postDate"),json.getString("postSubject"), Integer.parseInt(json.getString("postID")));
                    addPost(p,false);
                    postInfo pi =  new postInfo(Integer.parseInt(json.getString("postID")),json.getString("postString"),json.getString("postDate"),json.getString("username"));
                    addPostInfo(pi,false);
                }

                //add postInfo table to local
                jArray = new JSONArray(result3);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json = jArray.getJSONObject(i);
                    postInfo pi = new postInfo(Integer.parseInt(json.getString("postID")),json.getString("replyString"),json.getString("replyDate"),json.getString("replyUsername"));
                    addPostInfo(pi,false);
                }


            } catch (Exception e) {
                Log.e("log_tag", "Error Parsing Data " + e.toString());
            }
            return "Executed";
        }
    }






    /*
      |-----------------------------------------------------------
      |  NAME    : uploadToDatabaseThread
      |  Purpose : thread to upload data to host
      |
      |  options : 0. defualt, nothing
      |            1. upload contact
      |            2. upload post
      |-----------------------------------------------------------
    */
    class uploadToDatabaseThread extends AsyncTask<String, Void,Long > {
        private Exception exception;

        int option = 0;
        contact uploadContact;
        post uploadPost;
        postInfo uploadPostInfo;

        public uploadToDatabaseThread(contact uploadContact_) {
            option = 1;
            uploadContact = uploadContact_;
        }

        public uploadToDatabaseThread(post uploadPost_) {
            option = 2;
            uploadPost = uploadPost_;
        }

        public uploadToDatabaseThread(postInfo uploadPostInfo_){
            option = 3;
            uploadPostInfo = uploadPostInfo_;
        }

        protected Long doInBackground(String... urls){

            //OPTION 1.
            //UPLOAD CONTACT
            if (option == 1){

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://helpforums.gearhostpreview.com//PushContacts.php");

                try {
                    // Add contact infomation
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
                    nameValuePairs.add(new BasicNameValuePair("username", uploadContact.getUsername()));
                    nameValuePairs.add(new BasicNameValuePair("password", uploadContact.getPassword()));
                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    Log.i("dbHelper", "Uploaded");
                } catch (Exception e) {
                    Log.i("databaseHelper", "upload error");
                }
            }


            //OPTION 2.
            //UPLOAD POST
            else if(option == 2){

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://helpforums.gearhostpreview.com//PushPosts.php");

                try{
                    //add post infomation
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                    nameValuePairs.add(new BasicNameValuePair("username",uploadPost.getUsername()));
                    nameValuePairs.add(new BasicNameValuePair("postString",uploadPost.getPostString()));
                    nameValuePairs.add(new BasicNameValuePair("postDate",uploadPost.getPostDate()));
                    nameValuePairs.add(new BasicNameValuePair("postSubject",uploadPost.getPostSubject()));
                    nameValuePairs.add(new BasicNameValuePair("postID", Integer.toString(uploadPost.getId())));

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    Log.i("dbHelper", "Uploaded");

                }catch (Exception e) {
                    Log.i("databaseHelper", "upload error");
                }
            }

            else if(option == 3){
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://helpforums.gearhostpreview.com//PushPostInfo.php");

                try{
                    //add post infomation
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

                    nameValuePairs.add(new BasicNameValuePair("postID", Integer.toString(uploadPostInfo.getPostID())));
                    nameValuePairs.add(new BasicNameValuePair("replyString",uploadPostInfo.getReplyString()));
                    nameValuePairs.add(new BasicNameValuePair("replyDate",uploadPostInfo.getReplyDate()));
                    nameValuePairs.add(new BasicNameValuePair("replyUsername",uploadPostInfo.getReplyUsername()));

                    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                    // Execute HTTP Post Request
                    HttpResponse response = httpclient.execute(httppost);
                    Log.i("dbHelper", "Uploaded");

                }catch (Exception e) {
                    Log.i("databaseHelper", "upload error");
                }
            }

            //OPTION 0.
            //OTHER, later programming
            else{
                Log.i("databaseHelper", "upload error");
            }


            return null;
        }
    }
}

