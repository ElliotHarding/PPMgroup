<?php
    session_start();
    
    if (!isset($_SESSION['username']) || !isset($_SESSION['password']))
    {
        header("location: loginform.html");
    }
?>
<html>
    <head>
    
    </head>
    <body>
        <form name="newDrink" method="post" action="add_drink.php">
            cource title:   <input type="text" name="title"><br>
            cource desc:   <input type="text" name="courDesc"><br>
            qual Details:   <input type="text" name="qualDetails"><br>
            entry rec:   <input type="text" name="entryRec"><br>
            start time:   <input type="text" name="startTime"><br>
            cource length:   <input type="text" name="courseLength"><br>
            fee details:   <input type="text" name="feeDetails"><br>
            <input type="submit">
        </form>
    </body>
</html>