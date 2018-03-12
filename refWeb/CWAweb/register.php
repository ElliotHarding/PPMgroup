<?php
    session_start();
    include('dbconn.php');
    if (!isset($_SESSION['username']) || !isset($_SESSION['password']))
    {
        header("location: loginform.html");
    }
    
?>
<html>
    <head>
    
    </head>
    <body>
        <form name="newUser" method="post" action="ruser.php">
            user:   <input type="text" name="username"><br>
            pass:   <input type="text" name="password"><br>
            admin 0-1:  <input type="number" min="0" max="1" name="admin"></td><br>
            <input type="submit">
        </form>
    </body>
</html>