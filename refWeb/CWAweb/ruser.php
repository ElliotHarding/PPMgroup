<?php
    include('dbconn.php');

    $usr = $_POST['username'];
    $salt = "hjhdf7675BHJg2kd4f8sg3856HFsa";
    $pw = $_POST['password'].$salt.$usr;
    $pw = sha1($pw);
    $ad = $_POST['admin'];
    

    try
    {
        $sql = "INSERT INTO user VALUES('$usr', '$pw', '$ad')";
        mysql_query($sql);
        
        echo "<h1>Record Inserted</h1>";
    }
    catch (Exeption $e)
    {
        echo "Caught Exeption: ".$e->getMessage();
    }
?>