<?php
$servername = "den1.mysql2.gear.host";
$usernamee = "forumsdb";
$password = "Forums_9";
$dbname = "forumsdb";

// Create connection
$con = new mysqli($servername, $usernamee, $password, $dbname);
// Check connection
if ($con->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}


$sql = "SELECT * FROM posts";
$result = $con->query($sql);

while($row = mysqli_fetch_assoc($result))
  {
	$output[]=$row;
  }
print(json_encode($output));
mysqli_close($con);
?>