<html>
    <head>
        <title>cource page</title>
    </head>
    <body>
        <h1>cource page</h1>
        
        <?php
            include('dbconn.php');
        
            $sql ="SELECT * FROM courses ;";
            $rs = mysql_query($sql);
        
            while ($row = mysql_fetch_array($rs))
            {
                echo "<div style='border: 1px solid black;'>";
                    echo "<h2>".$row['title']."</h2>";
                    echo "<p>".$row['courDesc']."</p>";
        
                    
                echo "</div>";  
            }
        ?>
    </body>

</html>