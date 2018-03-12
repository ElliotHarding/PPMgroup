<?php 
	$id = $_GET["id"]

	$sql ="SELECT * FROM courses WHERE courseID=".$id.";";
    $rs = mysql_query($sql);
        
    while ($row = mysql_fetch_array($rs))
    {
        
    }
?>
<html>
	<head>
		<title>course</title>
	</head>
	<body>
		
	</body>
</html>