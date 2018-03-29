<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="register.aspx.cs" Inherits="_Default" %>

<asp:Content ID="registerContent" ContentPlaceHolderID="bodyContent" Runat="Server">

    <div id="registerContent">

        <div class="signIn">
            <h1>SignIn</h1>
     
            <label class="labels">Username </label><br />
            <asp:TextBox ID="usernameTXT" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />

            <label class="labels">Password </label><br />
            <asp:TextBox ID="passwordTXT" TextMode="Password" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>          
            <br />
            <br />

            <asp:Button ID="signInButton" runat="server" OnClick="signInButton_Click" Text="Sign In" Width="120px" />
        </div>
        
        <div id="registerWrapper">
            <div class="register">
            <h1>Register </h1>

            <label class="labels">Password </label><br />
            <asp:TextBox ID="txtPassword" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />
            

            <label class="labels">First Name</label><br />
            <asp:TextBox ID="txtFirstName" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />

        
            <label class="labels">Repeat Password</label><br />
            <asp:TextBox ID="txtPasswordRepeat" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
        </div>

        <div class="register">
            <label class="labels">Username </label><br />
            <asp:TextBox ID="txtUsername" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />

            <label class="labels">Last Name</label><br />
            <asp:TextBox ID="txtLastName" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />

            <label class="labels">Email</label><br />
            <asp:TextBox ID="txtEmail" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>            
            
        </div>
            <br />
            <asp:Button ID="registerButton" class="registerButton" runat="server" OnClick="registerButton_Click" Text="Register" Width="120px" />
        </div>

        <hr />

        <div id="infomationWrapper">
            <asp:Label CssClass="infoLbl" ID="Name" runat="server" Text="Name: "></asp:Label><br />
            <asp:Label CssClass="infoLbl" ID="Email" runat="server" Text="Email: "></asp:Label><br />
            <asp:Label CssClass="infoLbl" ID="Username" runat="server" Text="Username: "></asp:Label><br />
            <asp:Label CssClass="infoLbl" ID="Password" runat="server" Text="Password: "></asp:Label><br /><br />
            <asp:Button ID="changeInfoButton" runat="server" Text="Change Infomation" />
        </div>

    </div>

</asp:Content>