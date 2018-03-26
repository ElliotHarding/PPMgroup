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
                MySqlConnection conn = new MySqlConnection(connectionString + "Convert Zero Datetime = True;");
                conn.Open();

                MySqlCommand command = new MySqlCommand("SELECT * FROM posts WHERE postID = " + id + ";", conn);
                MySqlDataReader reader = command.ExecuteReader();

                string postString = "error";
                string postUsername = "error";
                string postDate = "error";

                if (reader.Read())
                {
                    postString = reader.GetString("postString");
                    postUsername = reader.GetString("username");
                    postDate = reader.GetString("postDate");                                       
                }
                reader.Close();

                //todo order by date...
                command = new MySqlCommand("SELECT * FROM postInfo WHERE postID = " + id + " ORDER BY replyDate DESC;");
                    
                using (MySqlDataAdapter sda = new MySqlDataAdapter())
                {
                    command.Connection = conn;
                    sda.SelectCommand = command;
                    using (DataTable dt = new DataTable())
                    {
                        sda.Fill(dt);

                        //add post inital string
                        DataRow dr = dt.NewRow();
                        dr["replyString"] = postString;
                        dr["replyUsername"] = postUsername;
                        dr["replyDate"] = postDate;
                        dt.Rows.Add(dr);

                        //fill repeater with data
                        postRepeater.DataSource = dt;
                        postRepeater.DataBind();
                    }
                }
                conn.Close();  
            }
            catch (Exception message)
            {
                string errorMessage = "Connection to data failed!Message: " + message.Message;
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorMessage+"')", true);
            }
        }
    }

    private List<postInfo> getAllPostInfoFromId(string id)
    {
        List<postInfo> allPostInfo = new List<postInfo>();

        try
        {
            MySqlConnection con = new MySqlConnection(connectionString);
            con.Open();

            MySqlCommand cmd = new MySqlCommand("SELECT * FROM postInfo WHERE postID = " + id + ";", con);
            MySqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                allPostInfo.Add(new postInfo(reader.GetString("replyString"),
                               reader.GetString("replyDate"),
                               reader.GetString("replyUsername"),
                              int.Parse(reader.GetString("postID"))));
            }
        }
        catch (Exception message)
        {
            string errorMessage = "Connection to data failed! Message: " + message.Message;
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorMessage+"')", true);
        }

        return allPostInfo;
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
                    MySqlConnection con = new MySqlConnection(connectionString);
                    con.Open();
                    MySqlCommand cmd = new MySqlCommand("INSERT INTO postInfo(`replyString`, `replyDate`, `replyUsername`, `postID`) VALUES (@replyString,@replyDate,@replyUsername,@postID);", con);

                    cmd.Parameters.AddWithValue("@replyString", text);
                    cmd.Parameters.AddWithValue("@replyDate", DateTime.Now);
                    cmd.Parameters.AddWithValue("@replyUsername", myCookie.Values["username"].ToString());
                    cmd.Parameters.AddWithValue("@postID", id);
                    cmd.ExecuteNonQuery();

                    con.Close();

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