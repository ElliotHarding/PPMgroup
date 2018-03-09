<%@ Page Language="C#" AutoEventWireup="true" CodeFile="Default.aspx.cs" Inherits="_Default" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>Home Page </title>
    <link rel ="stylesheet" href ="homePage.css" /> 
</head>
<body>
    <form id="form1" runat="server">
        <div>

           
                
            <img id ="banner" src ="headerImage2.jpg" width ="1700" height="300" />
                <div id ="navbar"> 
                    <ul>
                    <li> <a class ="active"> Home Page </a> </li>
                    <li> <a> Profile </a> </li>
                    <li> <a> Forums  </a> </li>
                    <li> <a> Links Page </a> </li>
                    <li> <a> About us   </a></li>
                    </ul>
                </div>
                
             

            
            
              <img id ="forumImage" src ="helpForum.jpg"  />
           
             <div class ="boxes"> 

                       <div id ="box1" > 
                           <h1 style ="text-align:center;"> Profile </h1>
                           <p> Here you can view and edit your profile, including: profile image, description and other information about yourself. </p>
                       </div>

                       <div id ="box2">
                           <h1 style ="text-align:center;"> Forums </h1>
                           <p> Our forum is for offering or seeking support and advice. Feel free to offer your successes (whether big or small) or engage in casual chit chat with other members. All your conversing needs are held here.</p>
                       </div>

                       <div id ="box3" >
                           <h1 style ="text-align:center;"> Links Page </h1>
                           <p> The links page is here to provide you with any extra information you may need, either about conditions themselves or about local services you can access to get professional help. </p>
                       </div>

                       <div id ="box4" > 
                           <h1 style ="text-align:center;"> About Us </h1>
                           <p>  This page will introduce you to who we (the developers) are and why we made this website and app.</p>
                       </div>


              </div>

<div id = "Footer">
<p>© 2018 helpForum ALL RIGHTS RESERVED </p>

</div>

</div>
         
   
    </form>
</body>
</html>
