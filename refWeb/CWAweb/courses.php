<html>
    <head>
        <title>courses</title>
    </head>
    <body>
        <h1>courses</h1>
        
        <?php
            include('dbconn.php');
        
            $sql ="SELECT * FROM courses /*WHERE drinkCate='Ale'*/;";
            $rs = mysql_query($sql);
        
            while ($row = mysql_fetch_array($rs))
            {
                echo "<div style='border: 1px solid black;'>";
                    echo "<h2>".$row['courTitle']."</h2>";
					echo "<form action='course.php' method='get'> ";
					echo "		<input type='hidden' value=" .$row['courseID']." name='id'>";
					echo " 		<input type='submit' value='submit' />";
					echo "</form>";
                echo "</div>";  
            }
        ?>
    </body>

</html>