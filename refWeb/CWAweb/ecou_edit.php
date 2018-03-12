<?php
    include('dbconn.php');
    
    $id = $_POST['id'];
    $title = $_POST['title'];
    $desc = $_POST['desc'];
    $qual = $_POST['qual'];
    $entry = $_POST['entry'];
    $start = $_POST['start'];
    $length = $_POST['length'];
    $fee = $_POST['fee'];

    try
    {
       $sql = 
        "UPDATE courses
            SET title='".$title."',
            courDesc = '".$desc."',
            qualDetails= '".$qual."',
            entryRec= '".$entry."',
            startTime= '".$start."',
            courseLength= '".$length."',
            feeDetails= '".$fee."'
            WHERE infoID = ".$id.";"; 
        
        mysql_query($sql);
        echo "cource has been updated";
        echo "<a href='cpanel.php'>Back to control Panel</a>";
    }
    catch (Exeption $e)
    {
        echo "Caught Exeption: ".$e->getMessage();
    }   
?>