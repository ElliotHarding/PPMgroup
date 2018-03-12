<?php
    session_start();
    
    if (!isset($_SESSION['username']) || !isset($_SESSION['password']))
    {
        header("location: loginform.html");
    }
	$UL = $_SESSION['userlevel'];
	
    
?>
<html>
    <body>
        <h1>controll panle</h1>
        <a href="logout.php">nope</a><br>
        <a href="addform.php">nope</a><br>
        <a href="euser_menu.php">e user</a><br>
        <a href="deletemenu.php">nope</a><br><br><br><br><br><br>
        <a href="register.php">click here to register a user</a><br>
        
		<?php
			if($UL = '1')
			{
				echo "hello";
			}
		?>
    </body>
</html>