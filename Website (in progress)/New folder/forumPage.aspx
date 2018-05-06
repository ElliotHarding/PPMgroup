<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="forumPage.aspx.cs" Inherits="_Default" %>

<asp:Content ID="forumPage" ContentPlaceHolderID="bodyContent" Runat="Server">
    <br />
    <asp:Button ID="addPostButton" runat="server" Text="Add Post" OnClick="gotoAddPost" />
    <asp:Button ID="viewPostsButton" runat="server" Text="View Your Posts" OnClick="gotoAddPost"/>
    <br />
    <br />
    <asp:Label ID="postsLbl" runat="server" Text="Posts:"></asp:Label>
    <div id="forumWrapper">
        <asp:Repeater ID="forumPostRepeater" runat="server">
            <ItemTemplate>

                <asp:LinkButton class="forumPost" runat="server" OnClick="postClicked" CommandArgument='<%#Eval("postID")%>'>
                    <asp:Label class="forumPostLabel" style="float: left;  text-align:left;"  runat="server" Text='<%# Eval("postSubject")%>'></asp:Label>
                    <asp:Label class="forumPostLabel" style="float: right; text-align:right;" runat="server" Text='<%# Eval("username")%>'></asp:Label>
                    <br />
                    <asp:Label class="forumPostLabel" style="float: left;  text-align:left;" runat="server" Text='<%# Eval("postDate")%>'></asp:Label>
                </asp:LinkButton>

                <br />
                <br />
            </ItemTemplate>
        </asp:Repeater>
    </div>
</asp:Content>