using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class _Default : System.Web.UI.Page
{
    string connectionString = @"Data Source=den1.mysql2.gear.host; Database=forumsdb; Convert Zero Datetime=True; User =forumsdb; Password='Forums_9'";

    protected void Page_Load(object sender, EventArgs e)
    {

    }

    protected void AddPost(object sender, EventArgs e)
    {

        string subject = txtPostSubject.Text;
        string text = Request.Form["txtPostText"];

        string errorString = "";

        if (subject.Length < 1 || text.Length < 1 || subject.Length > 45 || text.Length > 200)
        {
            errorString += "Subject/text length is too long/short. ";
        }

        //check if signed in
        HttpCookie myCookie = Request.Cookies["usernameCookie"];
        if (myCookie == null)
        {
            errorString += "Not signed in! ";
        }

        if (string.IsNullOrEmpty(myCookie.Values["username"]))
        {
            errorString += "Not signed in! ";
        }

        if (errorString != "")
        {
            //display error string
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorString+"')", true);
            return;
        }


        //add post
        try
        {           
            //add post to mySql server
            new DatabaseHelper().addPost(new Post(myCookie.Values["username"].ToString(), text, "0", subject, 999999999));

            //redirect to homepage
            Response.Redirect("forumPage.aspx");
        }
        catch (Exception message)
        {
            string errorMessage = "Connection to data failed!Message:\n" + message.Message;
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorMessage+"')", true);
        }
    }
}