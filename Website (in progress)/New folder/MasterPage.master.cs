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
            signOutBtn.Visible = false;
            return;
        }

        string signedIn = null;
        if (!string.IsNullOrEmpty(myCookie.Values["username"]))
        {
            signedIn = myCookie.Values["username"].ToString();
            signedInLbl.Text = "Signed in: " + signedIn;
            signOutBtn.Visible = true;
        }
        else
        {
            signedInLbl.Text = notSigedInString;
            signOutBtn.Visible = false;
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
        signOutBtn.Visible = false;
        Response.Redirect("register.aspx");
    }

    protected void gotoAccount(object sender, EventArgs e)
    {
        HttpCookie myCookie = Request.Cookies["usernameCookie"];
        if (myCookie == null)
        {
            Response.Redirect("register.aspx");
            return;
        }
        if (!string.IsNullOrEmpty(myCookie.Values["username"]))
        {
            Response.Redirect("account.aspx");
            signOutBtn.Visible = true;
        }
        else
        {
            Response.Redirect("register.aspx");
        }
        
    }
}
