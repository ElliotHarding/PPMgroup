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
$password = $_POST['password'];

$sql = "INSERT INTO contacts(username,password)
VALUES ('{$username}','{$password}')";

if ($conn->query($sql) === TRUE) {
    echo "New record created successfully";
} else {
    echo "Error: " . $sql . "<br>" . $conn->error;
}
$conn->close();
?> 