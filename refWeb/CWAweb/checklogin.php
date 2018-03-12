<?php
    include('dbconn.php');
    
    $usr = $_POST["username"];
    $salt = "hjhdf7675BHJg2kd4f8sg3856HFsa";
    $pw = $_POST["password"].$salt.$usr;
    $pw = sha1($pw);
    
 
    //sql injection shield
    $usr = mysql_real_escape_string($usr);
    $pw = mysql_real_escape_string($pw);

    $stmt = "SELECT * FROM user WHERE username='$usr' AND pw='$pw';";
    $result = mysql_query($stmt);

    $rows = mysql_num_rows($result);
    if ($rows == 1)
    {
        $ul = "SELECT userlevel FROM user WHERE username='$usr';";
        
        session_start();
        
        $_SESSION['username'] = $usr;
        $_SESSION['password'] = $pw;
        $_SESSION['userlevel'] = $ul;
        
        header('location: cpanel.php');
    }
    else
    {
        echo "Username and Password do not exist";
    }
?>