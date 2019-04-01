<?php
include("include/config.php");
if(isset($_GET['value'])) {
$query = mysqli_query($con, "

WHERE tbl_restourant.id='" . $_GET['value'] . "' ");
    while ($row = mysqli_fetch_assoc($query)) {
        $emparray[] =
            array(
                "id" => $row['id'],
                "food_type" => $row['foods']
            );
    }
    $json['Foodtype'] = $emparray;
    echo json_encode($json);
}
else
{
    $qu=mysqli_query($con,"select * from tbl_restourant");
    while($r=mysqli_fetch_assoc($qu))
    {
    $query = mysqli_query($con, "

WHERE tbl_restourant.id='" . $r['id'] . "' ");
    while ($row = mysqli_fetch_assoc($query))
    {
        $emparray[] =
            array(
                "id" => $row['id'],
                "foodtype" => $row['foods']
            );
    }
    $json['Foodtype'] = $emparray;
}
    echo json_encode($json);
}
?>