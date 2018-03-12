<?php
    session_start();
    
    if (!isset($_SESSION['username']) || !isset($_SESSION['password']))
    {
        header("location: loginform.html");
    }
?>
<html>
    <body>
        <h1>choose course information to to edit</h1>
        
        <?php
            include('dbconn.php');
        
            $sql = "SELECT infoID, title FROM courses;";
            $rs = mysql_query($sql);
        
            while ($row = mysql_fetch_array($rs))
            {
                echo "<p>";
                    echo "<a href='ecou_form.php?infoID=".$row['infoID']."'>";
                        echo $row['title'];
                    echo "</a>";
                echo "</p>";
            }
        ?>
        
    </body>
</html>