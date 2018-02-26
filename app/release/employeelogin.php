<?php
include("connection.php");
session_start();

if(isset($_POST['pin']))
{
    $empid=mysqli_real_escape_string($con,$_POST['empid']);
	
	$pin=$_POST['pin'];
	
	$IMEI_No=mysqli_real_escape_string($con,$_POST['IMEI_No']);
	
	$mobile=mysqli_real_escape_string($con,$_POST['mobile']);
	
	$type=mysqli_real_escape_string($con,$_POST['type']);
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
   
   
   
    if ($type=="Sales")
	{
		    $query="select * from  tbl_personnel_master where Mobile='$mobile' and Mobile_Pin ='$pin' and IMEI_No='$IMEI_No'";
	}
	else if ($type=="Customer")
	{
		  $query="select * from  tbl_customer_master where Mobile_Pin ='$pin' and IMEI_No='$IMEI_No'";
		
		
	}
	else
	{
		  $query="select * from  tbl_customer_vehicle_driver where Mobile_Pin ='$pin' and IMEI_No='$IMEI_No'";
		  
		 
	}
   
   
   
   
   
   
  
	
	
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $id = $rows['id'];

     }
     if($count == 1)
     {
        
		 
		 
		 if ($type=="Sales")
		{
		 $query="select t1.id,Personnel_Name,t2.Designation_Name,Date_of_Birth,Date_of_Appointment,t1.Mobile,t1.Email,
		 Personnel_Code as Employeecode,t3.pump_legal_name,t3.pump_address,t3.address_2,t3.address_3,t5.name as city,t4.name as state,
		 t4.gstcode,t3.pin_code,
		 t3.customer_care,t3.phone_no,t3.mobile,VAT_TIN,GST_TIN,VAT_prefix as invoice_prefix,GST_prefix,PAN_No from  tbl_personnel_master t1 
		 inner join tbl_designation_master t2 on t2.id=t1.Designation 
		 inner join  tbl_ro_master t3 on t3.RO_code=t1.RO_Code
		 inner join  states t4 on t4.id=t3.state
		 inner join  cities t5 on t5.id=t3.city
		 
		 where Mobile_Pin ='$pin' 
		 AND t1.Mobile='$mobile'
		 and t1.IMEI_No='$IMEI_No'";
		 
		
		}
		else if ($type=="Customer")
		{
		  $query="select * from  tbl_customer_master where Mobile_Pin ='$pin' and IMEI_No='$IMEI_No'";
		
		  
		}
		else
		{
			$query="select t1. *,t2.Customer_Name,t3.pump_legal_name from tbl_customer_vehicle_driver t1 inner join tbl_customer_master t2 on 
			t1.customer_code=t2.customer_code inner join tbl_ro_master t3 on t3.ro_code=t1.ro_code where t1.Mobile_Pin ='$pin' and t1.IMEI_No='$IMEI_No'";
			
			
			
		 // $query="select * from  tbl_customer_vehicle_driver t1 inner join tbl_customer_master t2 
//on t1.customer_code=t2.customer_code inner join tbl_ro_master t3 on t3.ro_code=t1.ro_code		  where Mobile_Pin ='$pin' and IMEI_No='$IMEI_No'";
		  
		 
		}
		 
		 
		
		 $res=mysqli_query($con,$query);
		 $count=mysqli_num_rows($res);
		 
		
		
		 while ($row = mysqli_fetch_assoc($res))
		 {
			$data[] = $row;
		 }
		 
		 
		 
		if($count == 1)
		{
		   
		   
		   
		   if ($type=="Sales")
	{
		     $query1="insert into tbl_personnel_login_log(Personnel_Code,IMEI_code) values ('$empid','$IMEI_No') ";
           mysqli_query($con, $query1);
		   
		   
		
           $key =md5(uniqid(mt_rand(),true));
		   
		   $query2="UPDATE tbl_personnel_master SET user_token='$key' WHERE  Mobile_Pin ='$pin' and IMEI_No='$IMEI_No' AND Mobile='$mobile' ";
           mysqli_query($con, $query2);
	}
	else if ($type=="Customer")
	{
		 $key =md5(uniqid(mt_rand(),true));
		   
		   $query2="UPDATE tbl_customer_master SET user_token='$key' WHERE  Mobile_Pin ='$pin' and IMEI_No='$IMEI_No' ";
           mysqli_query($con, $query2);
		  
	}
	else
	{
		 $key =md5(uniqid(mt_rand(),true));
		   
		   $query2="UPDATE tbl_customer_vehicle_driver SET user_token='$key' WHERE  Mobile_Pin ='$pin' and IMEI_No='$IMEI_No' ";
           mysqli_query($con, $query2);
		  
		 
	}
   
		   
		   
		   
		   
		   
		  
		   
		  // print_r($query2);
	//exit();
			
          $json = array( "msg"=>"login success..!" ,"key"=>$key,"EMPDETAIL"=>$data);
         $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);
		}
		else
		{
			 $json = array("msg"=>"login failed..! Not Valid Employee Code", "key"=>$key,);
			 $json1 = array('status'=>"failed", "data"=> $json);
			  header('Content-type: application/json'); 
             echo json_encode($json1);
		}
	
	 
           

    }

    else

    {
             $json = array("msg"=>"login failed..! Not Valid Pin", "key"=>$key,);
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