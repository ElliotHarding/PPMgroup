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

    protected void registerButton_Click(object sender, EventArgs e)
    {
        //get details
        string username = txtUsername.Text;
        string password = txtPassword.Text;
        string first = txtFirstName.Text;
        string last = txtLastName.Text;
        string repeatPassword = txtPasswordRepeat.Text;
        string email = txtEmail.Text;

        string errorMessage = "";

        List<contact> contacts = getContacts();

        for (int x =0; x < contacts.Count; x++)
        {
            if (contacts[x].username == username)
            {
                errorMessage += "username aleady exists";
            }
        }

        //data validation
        if (username.Length < 1 || username.Length > 20){
            errorMessage += "Username too long/short. ";
        }
        if (password.Length < 1 || password.Length > 20){
            errorMessage += "Password too long/short. ";
        }
        if (password != repeatPassword){
            errorMessage += "Passwords do not match. ";
        }
        if (first.Length < 1 || first.Length > 20)
        {
            errorMessage += "First name too long/short. ";
        }
        if (last.Length < 1 || last.Length > 20)
        {
            errorMessage += "Last name too long/short. ";
        }
        if (email.Length < 1)//todo work on email validation
        {
            errorMessage += "Email not recognised. ";
        }


        if (errorMessage == "")
        {
            //add user
            using (MySqlConnection connection = new MySqlConnection(connectionString))
            {
                try
                {
                    string insertData = "insert into contacts(username,password) values (@username, @password)";
                    MySqlCommand command = new MySqlCommand(insertData, connection);

                    command.Parameters.AddWithValue("@username", username.ToLower());
                    command.Parameters.AddWithValue("@password", password);

                    connection.Open();
                    int result = command.ExecuteNonQuery();

                    signInUser(username);
                }
                catch (Exception ex)
                {
                    string error = "Connection to SQL server failed!Message:" + ex.Message;
                    ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+error+"')", true);
                    string x = ex.Message;
                }
                finally
                {
                    connection.Close();
                }
            }
        }
        else
        {
            //display error message
            ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('"+errorMessage+"')", true);
        }
    }

    protected void signInButton_Click(object sender, EventArgs e)
    {
        //get username & password
        string username = usernameTXT.Text;
        string password = passwordTXT.Text;

        List<contact> contacts = getContacts();

        for (int x = 0; x < contacts.Count; x++)
        {
            if (contacts[x].username == username.ToLower() && contacts[x].password == password)
            {
                //sign in
                signInUser(username);
                ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('Signed In!')", true);
                return;
            }
        }

        ScriptManager.RegisterClientScriptBlock(this, this.GetType(), "alertMessage", "alert('incorrect username & password combination')", true);
    }

    private void signInUser(string username)
    {
        HttpCookie myCookie = new HttpCookie("usernameCookie");
        myCookie.Values.Add("username", username);
        myCookie.Expires = DateTime.Now.AddHours(12);

        //write the cookie to client.
        Response.Cookies.Add(myCookie);


        //change display
        Label signedInLbl = (Label)Master.FindControl("signedInLbl");
        signedInLbl.Text = "Signed in: " + username;
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
