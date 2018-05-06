<?php
$servername = "den1.mysql2.gear.host";
$usernamee = "forumsdb";
$password = "Forums_9";
$dbname = "forumsdb";

// Create connection
$conn = new mysqli($servername, $usernamee, $password, $dbname);
// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


$postID=$_POST['postID'];
$replyString = $_POST['replyString'];
$replyDate = $_POST['replyDate'];
$replyUsername = $_POST['replyUsername'];

$sql = "INSERT INTO postInfo(postID,replyString,replyDate,replyUsername)
VALUES ('{$postID}','{$replyString}',now(),'{$replyUsername}')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?> 