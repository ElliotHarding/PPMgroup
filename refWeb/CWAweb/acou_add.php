<?php
    include('dbconn.php');

    $title = $_POST['title'];       
    $desc = $_POST['courDesc'];
    $qual = $_POST['qualDetails'];
    $entry = $_POST['entryRec'];
    $start = $_POST['startTime'];
    $length = $_POST['courseLength'];
    $fee = $_POST['feeDetails'];

    try
    {
        $sql = "INSERT INTO courses VALUES(null, '$title', '$desc', '$qual', '$entry', '$start', '$length', '$fee')";
        mysql_query($sql);
        
        echo "<h1>Record Inserted</h1>";
    }
    catch (Exeption $e)
    {
        echo "Caught Exeption: ".$e->getMessage();
    }
?>