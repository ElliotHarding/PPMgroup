using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MySql.Data.MySqlClient;
using MySql.Data.Types;

public partial class _Default : System.Web.UI.Page
{
    string connectionString = @"Data Source=den1.mysql2.gear.host; Database=forumsdb; User =forumsdb; Password='Forums_9';";
    string id;

    protected void Page_Load(object sender, EventArgs e)
    {
        try
        {
            id = Request.QueryString["id"];
            loadPage(id);
        }
        catch (Exception message)
        {
            string errorMessage = "Error!: " + message.Message;
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('" + errorMessage + "')", true);
        }
    }

    private void loadPage(string id)
    {
        if (!IsPostBack)
        {
            //gets all posts and displays them in repeaters            
            try
            {
                DataTable dt = new DataTable();
                DatabaseHelper db = new DatabaseHelper();

                //add all postInfo data from db to dt
                dt = db.allPostsInfoDT(id);

                //add post inital string
                Post initialPost = db.getPost(id);
                DataRow dr = dt.NewRow();
                dr["replyString"] = initialPost.postString;
                dr["replyUsername"] = initialPost.username;
                dr["replyDate"] = initialPost.postDate;
                dt.Rows.Add(dr);

                //fill repeater with data
                postRepeater.DataSource = dt;
                postRepeater.DataBind();
            }
            catch (Exception message)
            {
                string errorMessage = "Connection to data failed!Message: " + message.Message;
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorMessage+"')", true);
            }
        }
    }    

    protected void postReplyButton_Click(object sender, EventArgs e)
    {
        string text = Request.Form["postReplyTxt"];

        if (text.Length < 1 || text.Length > 200)
        {
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('Reply too long/short')", true);
        }
        else
        {
            string errorString = "";

            //check if signed in
            HttpCookie myCookie = Request.Cookies["usernameCookie"];
            if (myCookie == null)
            {
                errorString += "Not signed in! ";
            }
            else if (myCookie.Values["username"] == null)
            {
                errorString += "Not signed in! ";
            }
            
            if (errorString != "")
            {
                //display error string
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('" + errorString + "')", true);
            }
            else
            {
                try
                {
                    //add reply to mySql server
                    new DatabaseHelper().addPostInfo(new PostInfo(text,"0", myCookie.Values["username"].ToString(),int.Parse(id)));

                    //redirect to homepage
                    Response.Redirect("post.aspx?id=" + id);
                }
                catch (Exception message)
                {
                    string errorMessage = "Connection to data failed! Message: " + message.Message;
                    ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('" + errorMessage + "')", true);
                }
            }
        }

    }
}


class postInfo
{
    public string replyString;
    public string replyDate;
    public string replyUsername;
    public int postID;

    public postInfo(string replyString, string replyDate, string replyUsername, int postID)
    {
        this.replyString = replyString;
        this.replyDate = replyDate;
        this.replyUsername = replyUsername;
        this.postID = postID;
    }
}