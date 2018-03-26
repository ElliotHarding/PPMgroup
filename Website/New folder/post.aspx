<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="post.aspx.cs" Inherits="_Default" %>

<asp:Content ID="postContent" ContentPlaceHolderID="bodyContent" Runat="Server">
    <div id="postWrapper">
        <asp:Repeater ID="postRepeater" runat="server">
            <ItemTemplate>

                <div class="post" runat="server" OnClick="postClicked">
                    <asp:Label class="postLabel" style="float: left;  text-align:left;"  runat="server" Text='<%# Eval("replyString")%>'></asp:Label>
                    <asp:Label class="postLabel" style="float: right; text-align:right;" runat="server" Text='<%# Eval("replyUsername")%>'></asp:Label>
                    <br />
                    <asp:Label class="postLabel" style="float: left;  text-align:left;" runat="server" Text='<%# Eval("replyDate")%>'></asp:Label>
                </div>

                <br />
                <br />
            </ItemTemplate>
        </asp:Repeater>
    </div>

    <div id="postReplyWrapper">
        <textarea class="txtInput" name="postReplyTxt" cols="20" rows="4"></textarea>
        <br />
        <asp:Button ID="postReplyButton" runat="server" Text="Post reply" OnClick="postReplyButton_Click"/>
    </div>
</asp:Content>

