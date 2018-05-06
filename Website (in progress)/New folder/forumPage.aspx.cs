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

    protected void Page_Load(object sender, EventArgs e)
    {
        if (!IsPostBack)
        {
            //gets all posts and displays them in repeaters            
            try{                
                forumPostRepeater.DataSource = new DatabaseHelper().allPostsDT();
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

    private List<post> getAllPosts(string username)
    {
        List<post> allPosts = new List<post>();

        try
        {
            MySqlConnection con = new MySqlConnection(connectionString);
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM posts WHERE username =" + username, con);

            con.Open();

            MySqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                allPosts.Add(new post(reader.GetString("username"),
                reader.GetString("postString"),
                reader.GetString("postDate"),
                reader.GetString("postSubject"),
                int.Parse(reader.GetString("postID"))));
            }
        }
        catch (Exception message)
        {
            string errorMessage = "Connection to data failed!Message:\n" + message.Message;
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert(errorMessage)", true);
        }

        return allPosts;
    }

}


class post
{
    public string username;
    public string postString;
    public string postDate;
    public string postSubject;
    public int postID;

    public post(string username, string postString, string postDate, string postSubject, int postID)
    {
        this.username = username;
        this.postString = postString;
        this.postDate = postDate;
        this.postSubject = postSubject;
        this.postID = postID;
    }
}


