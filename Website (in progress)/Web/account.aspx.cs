using MySql.Data.MySqlClient;
using System;
using System.Collections.Generic;
using System.Data;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class _Default : System.Web.UI.Page
{
    string connectionString = @"Data Source=den1.mysql2.gear.host; Database=forumsdb; Convert Zero Datetime=True; User =forumsdb; Password='Forums_9'";

    protected void Page_Load(object sender, EventArgs e)
    {
        HttpCookie myCookie = Request.Cookies["usernameCookie"];
        if (myCookie == null)
        {
            Response.Redirect("register.aspx");
            return;
        }
        string signedIn = null;
        String username = myCookie.Values["username"].ToString();
        if (!string.IsNullOrEmpty(myCookie.Values["username"]))
        {
            //String username = myCookie.Values["username"].ToString();
            //info.Text = username;
        }
        else
        {
            Response.Redirect("register.aspx");
        }
            
        adminDiv.Visible = false;
        superDiv.Visible = false;
        
        Welcome.Text = "Welcome "+ username+" to the help forums!";   
            
        List<admin> admins = getAdmins();

        for (int x = 0; x < admins.Count; x++)
        {
            
            if (admins[x].username == username.ToLower())
            {
                //sign in
                    if(admins[x].level == "1")
                    {
                        superDiv.Visible = true;
                    }
                adminDiv.Visible = true;
                return;
            }
        }
    }

    private List<admin> getAdmins()
    {
        List<admin> admins= new List<admin>();
        try
        {
            MySqlConnection con = new MySqlConnection(connectionString);
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM admin",con);

            con.Open();

            MySqlDataReader reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                admins.Add(new admin(reader.GetString("Username"), reader.GetString("AdminLevel")));
            }
        }
        catch (Exception message)
        {
            string errorMessage = "Connection to data failed!Message:\n" + message.Message;
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert(errorMessage)", true);
        }

        return admins;
    }

     private List<contact> getContacts()
    {
        List<contact> contacts = new List<contact>();
        try
        {
            MySqlConnection con = new MySqlConnection(connectionString);
            MySqlCommand cmd = new MySqlCommand("SELECT * FROM contacts",con);

            con.Open();

            MySqlDataReader reader = cmd.ExecuteReader();

            while (reader.Read())
            {
                contacts.Add(new contact(reader.GetString("username"), reader.GetString("password")));
            }
        }
        catch (Exception message)
        {
            string errorMessage = "Connection to data failed!Message:\n" + message.Message;
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert(errorMessage)", true);
        }

        return contacts;
    }


    protected void BanButton_Click(object sender, EventArgs e)
    {
        string username = BanTXT.Text;
        
        List<contact> contacts = getContacts();

        for (int x = 0; x < contacts.Count; x++)
        {
            if (contacts[x].username == username.ToLower())
            {
                //sign in
                using (MySqlConnection connection = new MySqlConnection(connectionString))
                {
                    try
                    {
                        string insertData = "DELETE FROM contacts WHERE username= @username;";
                        MySqlCommand command = new MySqlCommand(insertData, connection);

                        command.Parameters.AddWithValue("@username", username.ToLower());
                        

                        connection.Open();
                        
                        int result = command.ExecuteNonQuery();

                    }
                    catch (Exception ex)
                    {
                        /*string error = "Connection to SQL server failed!Message:" + ex.Message;
                        ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+error+"')", true);
                        string e = ex.Message;*/
                    }
                    finally
                    {
                        connection.Close();
                    }
                }
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "User Banned", true);
                return;
            }
        }

        ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('user doesn't exist')", true);
    }

//}


    protected void AddButton_Click(object sender, EventArgs e)
        {
            string username = AddTXT.Text;

            List<contact> contacts = getContacts();

            for (int x = 0; x < contacts.Count; x++)
            {
                if (contacts[x].username == username.ToLower())
                {
                    //sign in
                    using (MySqlConnection connection = new MySqlConnection(connectionString))
                    {
                        try
                        {
                            string insertData = "insert into admin(Username,AdminLevel) values (@username, 0)";
                            MySqlCommand command = new MySqlCommand(insertData, connection);

                            command.Parameters.AddWithValue("@username", username.ToLower());


                            connection.Open();

                            int result = command.ExecuteNonQuery();

                        }
                        catch (Exception ex)
                        {
                            /*string error = "Connection to SQL server failed!Message:" + ex.Message;
                            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+error+"')", true);
                            string e = ex.Message;*/
                        }
                        finally
                        {
                            connection.Close();
                        }
                    }
                    ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "admin added", true);
                    return;
                }
            }

            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('user doesn't exist')", true);
        }

    }
//}
class admin
{
    public string username;
    public string level;

    public admin(string username, string level)
    {
        this.username = username;
        this.level = level;
    }
}

class contact
{
    public string username;
    public string password;

    public contact(string username, string password)
    {
        this.username = username;
        this.password = password;
    }
}
