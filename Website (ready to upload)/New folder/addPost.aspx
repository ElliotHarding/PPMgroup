<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="addPost.aspx.cs" Inherits="_Default" %>

<asp:Content ID="addPostContent" ContentPlaceHolderID="bodyContent" Runat="Server">
    <div id="addPostWrapper">
        
        <!--Subject-->
        <asp:Label ID="Label1" runat="server" Text="Subject"></asp:Label>
        <br />
        <asp:TextBox ID="txtPostSubject" class="txtInput" runat="server"></asp:TextBox>
        
        <!--Space-->
        <br />
        <br />
        <br />
        
        <!--Text-->
        <asp:Label ID="Label2" runat="server" Text="Text"></asp:Label>
        <br />
        <textarea id="txtPostText" name="txtPostText" class="txtInput" cols="20" rows="10"></textarea>

        <asp:Button ID="addPost" runat="server" Text="Add Post" OnClick="AddPost" />
    </div>
</asp:Content>

