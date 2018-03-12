<?php
    session_start();
    
    if (!isset($_SESSION['username']) || !isset($_SESSION['password']))
    {
        header("location: loginform.html");
    }
?>
<html>
    <body>
        <h1>choose drink to edit</h1>
        
        <?php
            include('dbconn.php');
        
            $sql = "SELECT drinkID, drinkName FROM drink;";
            $rs = mysql_query($sql);
        
            while ($row = mysql_fetch_array($rs))
            {
                echo "<p>";
                    echo "<a href='editform.php?drinkID=".$row['drinkID']."'>";
                        echo $row['drinkName'];
                    echo "</a>";
                echo "</p>";
            }
        ?>
        
    </body>
</html>