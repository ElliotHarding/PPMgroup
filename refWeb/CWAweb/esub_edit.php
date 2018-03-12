<?php
    include('dbconn.php');
    
    $id = $_POST['id'];
    $title = $_POST['drinkName'];
    $category = $_POST['drinkCategory'];
    $desc = $_POST['description'];

    try
    {
       $sql = 
        "UPDATE drink
            SET drinkName='".$title."',
            drinkCate = '".$category."',
            description = '".$desc."' 
            WHERE drinkID = ".$id.";"; 
        
        mysql_query($sql);
        echo "Drink has been updated";
        echo "<a href='cpanel.php'>Back to control Panel</a>";
    }
    catch (Exeption $e)
    {
        echo "Caught Exeption: ".$e->getMessage();
    }   
?>