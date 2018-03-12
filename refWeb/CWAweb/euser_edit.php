<?php
    include('dbconn.php');
    
    $id = $_POST['username'];
    $UL = $_POST['userLevel'];
    

    try
    {
       $sql = 
        "UPDATE user
            SET username='".$id."',
            userlevel = '".$UL."'
            WHERE username = '".$id."';"; 
        
        mysql_query($sql);
        echo "user has been updated";
        echo "<a href='cpanel.php'>Back to control Panel</a>";
    }
    catch (Exeption $e)
    {
        echo "Caught Exeption: ".$e->getMessage();
    }   
?>