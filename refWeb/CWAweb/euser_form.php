<?php
    session_start();
    
    if (!isset($_SESSION['username']) || !isset($_SESSION['password']))
    {
        header("location: loginform.html");
    }
?>
<html>
    <head>
    </head>
    <body>
        <?php
            include('dbconn.php');
            
            $id = $_GET['username'];
            if ($id != null || $id != 0)
            {
                try
                {
                    $sql = "SELECT * FROM user WHERE username='".$id."';";
                    $rs = mysql_query($sql);
                    $row = mysql_fetch_array($rs);
                    
                    $UN = $row[0];
                    
                    $UL = $row[2];
                    
                    
                }
                catch (Exeption $e)
                {
                    echo "Caught Exeption: ".$e->getMessage();
                }
            }
        ?>
        <form enctype="multipart/form-data" name="add" method="post" action="euser_edit.php">
            <input type="hidden" value="<?php echo $id; ?>" name="id" />
            
            <div>
                <label for="username">username:</label>
                <input name="username" type="text" value="<?php echo $UN; ?>" />
            </div>
          
            <div>
                <label for="userlevel">userlevel:</label>
				<input type="number" min="0" max="1" name="userLevel" value="<?php echo $UL; ?>"></td>
            </div>
            <input type="submit" value="submit" />
        </form>
    </body>
</html>