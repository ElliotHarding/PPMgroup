<%@ Page Language="C#" AutoEventWireup="true" CodeFile="logInPage.aspx.cs" Inherits="logInPage" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title> Log In</title>
    <link rel ="stylesheet" href ="Stylesheets/style.css" /> 
</head>
<body>
    <form id="form1" runat="server">
        <div>
                   
            <img id ="banner" src ="Images/headerImage2.jpg" width ="1700" height="300" />
                <div id ="navbar">
                    <ul>
                        <li><a href="homePage.aspx">Home Page </a></li>
                        <li><a class="active" href="register.aspx">Profile </a></li>
                        <li><a href="forumPage.aspx">Forums  </a></li>
                        <li><a href="linksPage.aspx">Links Page </a></li>
                        <li><a href="aboutUs.aspx">About us   </a></li>
                    </ul>
                </div>
                
             
        </div>
        
        <div class="reg" style="font-size: x-large">
            <h1 id="LogIn">Log In </h1>
            Username
            <asp:TextBox ID="txtUserName" runat="server" BackColor="DarkGray" CssClass="entry" Height="16px" Width="226px"></asp:TextBox>
            <br />
            <label class="labels">
            Password
            </label>
            <asp:TextBox ID="txtPword" runat="server" BackColor="DarkGray" CssClass="entry" Height="16px" Width="226px"></asp:TextBox>
            <br />
            <asp:Button ID="Button2" runat="server" Text="Log In" />
        </div>
        <div id="Footer">
            <p>© 2018 helpForum ALL RIGHTS RESERVED </p>

        </div>

    </form>
</body>
</html>
