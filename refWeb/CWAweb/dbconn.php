<?php
	$con = mysql_connect("192.168.12.20","abarber","password");
	if (!$con)
	{
		die('Could not connect: ' .mysql_error());
	}
	else 
	{
		//echo "Connected! i hate you ...";
		mysql_select_db("abarber", $con);
	}
?>