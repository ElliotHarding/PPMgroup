using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

public partial class MasterPage : System.Web.UI.MasterPage
{
    const string notSigedInString = "Not Signed In!";

    protected void Page_Load(object sender, EventArgs e)
    {
        //Assuming user comes back after several hours. several < 12.
        //Read the cookie from Request.
        HttpCookie myCookie = Request.Cookies["usernameCookie"];
        if (myCookie == null)
        {
            signedInLbl.Text = notSigedInString;
            return;
        }

        string signedIn = null;
        if (!string.IsNullOrEmpty(myCookie.Values["username"]))
        {
            signedIn = myCookie.Values["username"].ToString();
            signedInLbl.Text = "Signed in: " + signedIn;
        }
        else
        {
            signedInLbl.Text = notSigedInString;
        }
    }

    protected void signOut(object sender, EventArgs e)
    {
        //create cookie
        HttpCookie myCookie = new HttpCookie("usernameCookie");
        myCookie.Values.Add("username", null);
        myCookie.Expires = DateTime.Now.AddHours(12);

        //write the cookie to client.
        Response.Cookies.Add(myCookie);


        //change display
        signedInLbl.Text = notSigedInString;
    }

    protected void gotoAccount(object sender, EventArgs e)
    {
        Response.Redirect("register.aspx");
    }
}
