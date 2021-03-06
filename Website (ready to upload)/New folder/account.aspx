<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="account.aspx.cs" Inherits="_Default" %>

<asp:Content ID="registerContent" ContentPlaceHolderID="bodyContent" Runat="Server">

    <div id="registerContent">
        
        <div id="userDiv" >
            <asp:Label CssClass="infoLbl" ID="Welcome" Font-Bold="true" runat="server" Text=""></asp:Label><br />
            
        </div>
        <br />

        <div id="infomationWrapper" style="display: inline-block;">
            <p style="font-size: 25px;">Password:</p>
            <asp:TextBox CssClass="infoLbl" Font-Size="Medium" ID="PasswordTxt" runat="server"></asp:TextBox>
            <br /><br />
            <asp:Button ID="changeInfoButton" runat="server" OnClick="changeInfoButton_Click" Text="Change Infomation" />
        </div>
        
        <br /><br />

        <div runat="server" id="adminDiv" style="display: inline-block;" >
            
            <label class="labels">Ban Username </label><br />
            <asp:TextBox ID="BanTXT" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />
            <asp:Button ID="BanButton" class="BanButton" runat="server" OnClick="BanButton_Click" Text="Ban" Width="120px" />
            <br />
        </div>
        
        <div runat="server" id="superDiv" style="display: inline-block;">
            <label class="labels">Add admin </label><br />
            <asp:TextBox ID="AddTXT" runat="server" CssClass="entry" Height="16px" Width="226px" BackColor="DarkGray"></asp:TextBox>
            <br />
            <asp:Button ID="AddButton" class="AddButton" runat="server" OnClick="AddButton_Click" Text="Add Admin" Width="120px" />
        </div>
    
    </div>

</asp:Content>