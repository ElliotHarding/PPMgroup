﻿using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;

public class Post
{
    public string username { get; set; }
    public string postString { get; set; }
    public string postDate { get; set; }
    public string postSubject { get; set; }
    public int postID { get; set; }

    public Post(string username, string postString, string postDate, string postSubject, int postID)
    {
        this.username = username;
        this.postString = postString;
        this.postDate = postDate;
        this.postSubject = postSubject;
        this.postID = postID;
    }
}

public class contact
{
    public string username;
    public string password;

    public contact(string username, string password)
    {
        this.username = username;
        this.password = password;
    }
}


public class PostInfo
{
    public string replyString;
    public string replyDate;
    public string replyUsername;
    public int postID;

    public PostInfo(string replyString, string replyDate, string replyUsername, int postID)
    {
        this.replyString = replyString;
        this.replyDate = replyDate;
        this.replyUsername = replyUsername;
        this.postID = postID;
    }
}


public class DatabaseHelper
{    
    private const string connectionString = @"Data Source=den1.mysql2.gear.host; Database=forumsdb; User =forumsdb; Password='Forums_9';";

    public void addPost(Post postToAdd)
    {       
        MySqlConnection con = new MySqlConnection(connectionString + "Convert Zero Datetime = True;");
        con.Open();
        MySqlCommand cmd = new MySqlCommand("INSERT INTO posts(`username`, `postString`, `postDate`, `postSubject`, `postID`) VALUES (@username,@postString,@postDate,@postSubject,UUID());", con);

        cmd.Parameters.AddWithValue("@username", postToAdd.username);
        cmd.Parameters.AddWithValue("@postString", postToAdd.postString);
        cmd.Parameters.AddWithValue("@postDate", DateTime.Now);
        cmd.Parameters.AddWithValue("@postSubject", postToAdd.postSubject);
        cmd.ExecuteNonQuery();

        con.Close();
    }

    public List<Post> allPosts()
    {
        List<Post> returnPosts = new List<Post>();
        MySqlConnection con = new MySqlConnection(connectionString + "Convert Zero Datetime = True;");
        con.Open();

        MySqlCommand cmd = new MySqlCommand("SELECT * FROM posts ORDER BY postDate DESC", con);
        MySqlDataReader reader = cmd.ExecuteReader();


        while (reader.Read())
        {
            Post returnPost = new Post(
            reader.GetString("username"),
            reader.GetString("postString"),
            reader.GetString("postDate"),
            reader.GetString("postSubject"),
            Int32.Parse(reader.GetString("postID"))
            );

            returnPosts.Add(returnPost);
        }

        reader.Close();
        con.Close();
        return returnPosts;
    }

    public DataTable allPostsDT()
    {
        MySqlConnection con = new MySqlConnection(connectionString + "Convert Zero Datetime = True;");
        con.Open();

        MySqlCommand cmd = new MySqlCommand("SELECT * FROM posts ORDER BY postDate DESC");

        using (MySqlDataAdapter sda = new MySqlDataAdapter())
        {
            cmd.Connection = con;
            sda.SelectCommand = cmd;
            using (DataTable dt = new DataTable())
            {
                sda.Fill(dt);

                return dt;
            }
        }
    }

    public DataTable allPostsInfoDT(string id)
    {
        MySqlConnection con = new MySqlConnection(connectionString + "Convert Zero Datetime = True;");
        con.Open();

        MySqlCommand cmd = new MySqlCommand("SELECT * FROM postInfo WHERE postID = " + id + " ORDER BY replyDate DESC;");

        using (MySqlDataAdapter sda = new MySqlDataAdapter())
        {
            cmd.Connection = con;
            sda.SelectCommand = cmd;
            using (DataTable dt = new DataTable())
            {
                sda.Fill(dt);
                con.Close();
                return dt;
            }
        }
    }

    public Post getPost(string id)
    {
        MySqlConnection conn = new MySqlConnection(connectionString + "Convert Zero Datetime = True;");
        conn.Open();

        MySqlCommand command = new MySqlCommand("SELECT * FROM posts WHERE postID = " + id + ";", conn);
        MySqlDataReader reader = command.ExecuteReader();

        Post returnPost = new Post("error","error","000","error",999999999);
        returnPost.postID = int.Parse(id);

        if (reader.Read())
        {
            returnPost.postString = reader.GetString("postString");
            returnPost.username = reader.GetString("username");
            returnPost.postDate = reader.GetString("postDate");
            returnPost.postSubject = reader.GetString("postSubject");
        }

        reader.Close();
        conn.Close();

        return returnPost;
    }

    public void addPostInfo(PostInfo postInfo)
    {
        MySqlConnection con = new MySqlConnection(connectionString);
        con.Open();
        MySqlCommand cmd = new MySqlCommand("INSERT INTO postInfo(`replyString`, `replyDate`, `replyUsername`, `postID`) VALUES (@replyString,@replyDate,@replyUsername,@postID);", con);

        cmd.Parameters.AddWithValue("@replyString", postInfo.replyString);
        cmd.Parameters.AddWithValue("@replyDate", DateTime.Now);
        cmd.Parameters.AddWithValue("@replyUsername", postInfo.replyUsername);
        cmd.Parameters.AddWithValue("@postID", postInfo.postID.ToString());
        cmd.ExecuteNonQuery();

        con.Close();
    }

    public List<contact> getContacts()
    {
        List<contact> contacts = new List<contact>();
        try
        {
            MySqlConnection con = new MySqlConnection(connectionString);
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM contacts", con);

            con.Open();

            MySqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                contacts.Add(new contact(reader.GetString("username"), reader.GetString("password")));
            }
        }
        catch (Exception message)
        {
            
        }

        return contacts;
    }
}