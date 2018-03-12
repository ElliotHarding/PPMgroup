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
            
            $id = $_GET['drinkID'];
            if ($id != null || $id != 0)
            {
                try
                {
                    $sql = "SELECT * FROM drink WHERE drinkID=".$id.";";
                    $rs = mysql_query($sql);
                    $row = mysql_fetch_array($rs);
                    
                    $id = $row[0];
                    $drinkName = $row[1];
                    $category = $row[2];
                    $desc = $row[3];
                    $imgpath = $row[4];
                    
                }
                catch (Exeption $e)
                {
                    echo "Caught Exeption: ".$e->getMessage();
                }
            }
        ?>
        <form enctype="multipart/form-data" name="add" method="post" action="edit_drink.php">
            <input type="hidden" value="<?php echo $id; ?>" name="id" />
            
            <div>
                <label for="drinkName">Drink Name:</label>
                <input name="drinkName" type="text" value="<?php echo $drinkName; ?>" />
            </div>
            <div>
                <label for="drinkCategory">Drink Category:</label>
                <input name="drinkCategory" type="text" value="<?php echo $category; ?>" />
            </div>
            <div>
                <label for="description">Description:</label>
                <input name="description" type="text" value="<?php echo $desc; ?>" />
            </div>
            <input type="submit" value="submit" />
        </form>
    </body>
</html>