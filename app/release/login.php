<?php
include("connection.php");
session_start();

if(isset($_POST['mobile']))
{
	

    $mobile=mysqli_real_escape_string($con,$_POST['mobile']);
	
	$IMEI_No=mysqli_real_escape_string($con,$_POST['IMEI_No']);
	
	$device_id=mysqli_real_escape_string($con,$_POST['device_id']);
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
     $query="select t1. *,Designation_Name from  tbl_personnel_master t1 inner join tbl_designation_master t2 on t1.Designation=t2.id where mobile ='$mobile' ";
	
	 
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $id = $rows['id'];
	 $role=$rows['Designation_Name']; 

     }
	 
	 // $password=mysqli_real_escape_string($con,$_POST['']);
     $query1="select * from  tbl_customer_master where Mobile ='$mobile' ";
	 //print_r($query);
	 //exit();
	 
    $res1=mysqli_query($con,$query1);
    $count1=mysqli_num_rows($res1);
	while($rows = mysqli_fetch_assoc($res1))
     {
    
	 $id = $rows['id'];

     }
	 
	  
	 // $password=mysqli_real_escape_string($con,$_POST['']);
     $query2="select * from  tbl_customer_vehicle_driver where Driver_Mobile ='$mobile' ";
	 //print_r($query);
	 //exit();
	 
    $res2=mysqli_query($con,$query2);
    $count2=mysqli_num_rows($res2);
	while($rows = mysqli_fetch_assoc($res2))
     {
    
	 $id = $rows['id'];

     }
	 
	 
	 
	 
	 
     if($count == 1)
     {
        $row = mysqli_fetch_assoc($res);
        $key =md5(uniqid(mt_rand(),true));
		
           $query1="UPDATE tbl_personnel_master SET IMEI_No='$IMEI_No',Device_id='$device_id' where Mobile= '$mobile' ";
           mysqli_query($con, $query1);
		
				 
                 
         $json = array( "msg"=>"login success..!" ,"Mobile"=>$mobile,"ID"=>$id,"Usertype"=>"Sales","Role"=>$role);
         $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);

    }
	else if($count1 == 1)
     {
        $row = mysqli_fetch_assoc($res);
        $key =md5(uniqid(mt_rand(),true));
		
           $query1="UPDATE tbl_customer_master SET IMEI_No='$IMEI_No',Device_id='$device_id' where Mobile= '$mobile' ";
           mysqli_query($con, $query1);
		
                 
         $json = array( "msg"=>"login success..!" ,"Mobile"=>$mobile,"ID"=>$id,"Usertype"=>"Customer","Role"=>$role);
         $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);

    }
	else if($count2 == 1)
     {
        $row = mysqli_fetch_assoc($res);
        $key =md5(uniqid(mt_rand(),true));
		
           $query1="UPDATE  tbl_customer_vehicle_driver SET IMEI_No='$IMEI_No',Device_id='$device_id' where Driver_Mobile= '$mobile' ";
           mysqli_query($con, $query1);
				  
                 
         $json = array( "msg"=>"login success..!" ,"Mobile"=>$mobile,"ID"=>$id,"Usertype"=>"Driver","Role"=>$role);
         $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);

    }

    else

    {
             $json = array("msg"=>"login failed..! Not Valid Mobile Number", "key"=>$key,);
			 $json1 = array('status'=>"failed", "data"=> $json);
			  header('Content-type: application/json'); 
             echo json_encode($json1);

    }
    
}
else
{
               $json = array( "msg"=>"Not receive required data..!" );
	           $json1 = array('status'=>"failed","data"=> $json);
			    header('Content-type: application/json'); 
               echo json_encode($json1);

}

?>