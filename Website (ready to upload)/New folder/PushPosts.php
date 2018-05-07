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


$username=$_POST['username'];
$postString = $_POST['postString'];
$postDate = $_POST['postDate'];
$postSubject = $_POST['postSubject'];
$postID = $_POST['postID'];

$sql = "INSERT INTO posts(username,postString,postDate,postSubject,postID)
VALUES ('{$username}','{$postString}','{$postDate}','{$postSubject}','{$postID}')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?> 