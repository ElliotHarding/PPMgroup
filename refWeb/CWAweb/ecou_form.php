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
            
            $id = $_GET['infoID'];
            if ($id != null || $id != 0)
            {
                try
                {
                    $sql = "SELECT * FROM courses WHERE =infoID=".$id.";";
                    $rs = mysql_query($sql);
                    $row = mysql_fetch_array($rs);
                    
                    $id = $row[0];
                    $title = $row[1];
                    $desc = $row[2];
                    $qual = $row[3];
                    $entry = $row[4];
                    $start = $row[5];
                    $length = $row[6];
                    $fee = $row[7];
                    
                }
                catch (Exeption $e)
                {
                    echo "Caught Exeption: ".$e->getMessage();
                }
            }
        ?>
        <form enctype="multipart/form-data" name="add" method="post" action="ecou_edit.php">
            <input type="hidden" value="<?php echo $id; ?>" name="id" />
            
            <div>
                <label for="title">title:</label>
                <input name="title" type="text" value="<?php echo $title; ?>" />
            </div>
            <div>
                <label for="desc">cource description:</label>
                <input name="desc" type="text" value="<?php echo $desc; ?>" />
            </div>
            <div>
                <label for="qual">qualification details:</label>
                <input name="qual" type="text" value="<?php echo $qual; ?>" />
            </div>
            <div>
                <label for="entry">entry requirements:</label>
                <input name="entry" type="text" value="<?php echo $entry; ?>" />
            </div>
            <div>
                <label for="length">course length:</label>
                <input name="length" type="text" value="<?php echo $length; ?>" />
            </div>
            <div>
                <label for="start">start date:</label>
                <input name="start" type="text" value="<?php echo $start; ?>" />
            </div>
            <div>
                <label for="fee">fee details:</label>
                <input name="fee" type="text" value="<?php echo $fee; ?>" />
            </div>
            <input type="submit" value="submit" />
        </form>
    </body>
</html>