<?php
include'include/config.php';
include'include/global.php';
if(isset($_GET['login_with']) && $_GET['login_with'] == "google")
{
    if (isset($_GET['name']) && isset($_GET['email']) && isset($_GET['image']))
    {
        $username = $_GET['name'];
        $email = $_GET['email'];
        $image = $_GET['image'];
        $user = mysqli_query($con, "select * from tbl_mobileuser WHERE email='$email' ");
        $sel = mysqli_fetch_array($user);
        if ($sel)
        {
            if($sel['login_with'] == "")
            {
                $image = IMAGE_PATH.$sel['image'];
            }
            else
            {
                $image = $sel['image'];
            }
            $array[] = array("id" => $sel['id'], "name" => $sel['username'], "email" => $sel['email'],"image"=>$image);
            echo json_encode($array);
        }
        else
        {
            $query = mysqli_query($con, "insert into tbl_mobileuser (username,email,login_with,image) VALUES ('$username','$email','google','$image')");
            if ($query)
            {
                $qury = mysqli_query($con, " select * from tbl_mobileuser WHERE  email='$email' ");
                $res = mysqli_fetch_assoc($qury);
                if($res['login_with'] == "")
                {
                    $image = IMAGE_PATH.$res['image'];
                }
                else
                {
                    $image = $res['image'];
                }
                $array[] = array("id" => $res['id'], "name" => $res['username'], "email" => $res['email'] ,"image"=>$image);
                echo json_encode($array);
            }
        }
    }
    else
    {
        $arr[] = array("id" => "Required all field !! ");
        echo json_encode($arr);
    }
}
elseif(isset($_GET['login_with']) && $_GET['login_with'] == "facebook"){
    if (isset($_GET['name']) && isset($_GET['email']) && isset($_GET['image']))
    {
        $username = $_GET['name'];
        $email = $_GET['email'];
        $image = $_GET['image'];
        $user = mysqli_query($con, "select * from tbl_mobileuser WHERE email='$email' ");
        $sel = mysqli_fetch_array($user);
        if ($sel)
        {
            if($sel['login_with'] == "")
            {
                $image = IMAGE_PATH.$sel['image'];
            }
            else
            {
                $image = $sel['image'];
            }
            $array[] = array("id" => $sel['id'], "name" => $sel['username'], "email" => $sel['email'],"image"=>$image);
            echo json_encode($array);
        }
        else
        {
            $query = mysqli_query($con, "insert into tbl_mobileuser (username,email,login_with,image) VALUES ('$username','$email','facebook','$image')");
            if ($query)
            {
                $qury = mysqli_query($con, " select * from tbl_mobileuser WHERE  email='$email' ");
                $res = mysqli_fetch_assoc($qury);
                $array[] = array("id" => $res['id'], "name" => $res['username'], "email" => $res['email'] ,"image"=>$res['image']);
                echo json_encode($array);
            }
        }
    }
    else
    {
        $arr[] = array("id" => "Required all field !! ");
        echo json_encode($arr);
    }
}
else
{
    if (isset($_POST['name']) && (isset($_POST['email'])) && isset($_POST['password']) && isset($_FILES['file']['name']) && $_FILES['file']['name'] != "")
    {
        $username = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $user = mysqli_query($con, "select * from tbl_mobileuser WHERE email='$email' ");
        $sel = mysqli_fetch_assoc($user);
        if ($sel > 0)
        {
            $json[] = array("id" => "this user email id is already exits");
            echo json_encode($json);
        }
        else
        {
            $filename = $_FILES['file']['name'];
            $tmp_file = $_FILES['file']['tmp_name'];
            $time = time()."_".rand();
            $filepath = "uploads/"."profile_".$time.".png";
            $imagepath="profile_".$time.".png";
            if(move_uploaded_file($tmp_file,$filepath))
            {
                $query = mysqli_query($con, "insert into tbl_mobileuser (username,email,password,image) VALUES ('$username','$email','$password','$imagepath')");
                if ($query)
                {
                    $qury = mysqli_query($con, "select * from tbl_mobileuser WHERE  email='$email' ");
                    while ($res = mysqli_fetch_assoc($qury))
                    {
                        $qury = mysqli_query($con, " select * from tbl_mobileuser WHERE  email='$email' ");
                        $res = mysqli_fetch_assoc($qury);
                        if($res['login_with'] == "")
                        {
                            $image = IMAGE_PATH.$res['image'];
                        }
                        else
                        {
                            $image = $res['image'];
                        }
                        $array[] = array("id" => $res['id'], "name" => $res['username'], "email" => $res['email'], "image" => $image);
                    }
                    echo json_encode($array);
                }
            }
            else
            {
                $arr[] = array("id" => " file uploading error ");
                echo json_encode($arr);
            }
        }
    }
    else
    {
        $arr[] = array("id" => " Required all field !! ");
        echo json_encode($arr);
    }
}
?>