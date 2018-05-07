using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using MySql.Data.MySqlClient;

public partial class _Default : System.Web.UI.Page
{    
    string connectionString = @"Data Source=den1.mysql2.gear.host; Database=forumsdb; Convert Zero Datetime=True; User =forumsdb; Password='Forums_9'";
    DatabaseHelper db = new DatabaseHelper();

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //gets all posts and displays them in repeaters            
            try{                
                forumPostRepeater.DataSource = db.allPostsDT();
                forumPostRepeater.DataBind();
            }
            catch (Exception message)
            {
                string errorMessage = "Connection to data failed!Message: " + message.Message;
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorMessage+"')", true);
            }
        }
    }

    protected void postClicked(Object sender, EventArgs e)
    {
        //get id of post clicked
        LinkButton callingButton = (LinkButton)sender;
        string id = callingButton.CommandArgument.ToString();

        Response.Redirect("post.aspx?id=" + id);
    }

    protected void gotoAddPost(Object sender, EventArgs e)
    {
        Response.Redirect("addPost.aspx");
    }

    private string getUsername() {

        HttpCookie myCookie = Request.Cookies["usernameCookie"];
        if (myCookie == null)
        {
            return "";
        }

        if (!string.IsNullOrEmpty(myCookie.Values["username"]))
        {
            return myCookie.Values["username"].ToString();
        }
        
        return "";
    }


    protected void viewPostsButton_Click(object sender, EventArgs e)
    {
        List<Post> allPosts = db.allPosts();
        List<Post> displayPosts = new List<Post>();
        for (int x = 0; x < allPosts.Count(); x++)
        {
            if (allPosts[x].username == getUsername())
            {
                displayPosts.Add(allPosts[x]);
            }
        }

        forumPostRepeater.DataSource = displayPosts;
        forumPostRepeater.DataBind();
    }
}




