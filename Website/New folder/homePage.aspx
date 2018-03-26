<%@ Page Title="" Language="C#" MasterPageFile="~/MasterPage.master" AutoEventWireup="true" CodeFile="homePage.aspx.cs" Inherits="_Default" %>

<asp:Content ID="homeContent" ContentPlaceHolderID="bodyContent" Runat="Server">
    <img id ="forumImage" src ="Images/helpForum.jpg"  />
           
             <div class ="boxes"> 

                       <div class="box"> 
                           <h1 style ="text-align:center;"> Profile </h1>
                           <p> Here you can view and edit your profile, including: profile image, description and other information about yourself. </p>
                       </div>

                       <div class="box">
                           <h1 style ="text-align:center;"> Forums </h1>
                           <p> Our forum is for offering or seeking support and advice. Feel free to offer your successes (whether big or small) or engage in casual chit chat with other members. All your conversing needs are held here.</p>
                       </div>

                       <div class="box" >
                           <h1 style ="text-align:center;"> Links Page </h1>
                           <p> The links page is here to provide you with any extra information you may need, either about conditions themselves or about local services you can access to get professional help. </p>
                       </div>

                       <div class="box" > 
                           <h1 style ="text-align:center;"> About Us </h1>
                           <p>  This page will introduce you to who we (the developers) are and why we made this website and app.</p>
                       </div>
              </div>
</asp:Content>

