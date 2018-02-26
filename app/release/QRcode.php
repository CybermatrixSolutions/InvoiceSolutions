<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	$Customer_Code=mysqli_real_escape_string($con,$_POST['Customer_Code']);
	
	$Vehicle_No=mysqli_real_escape_string($con,$_POST['Vehicle_No']);
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from   tbl_personnel_master where user_token ='$key'";
    $res=mysqli_query($con,$query);
    $countkey=mysqli_num_rows($res);
	
	while($rows = mysqli_fetch_assoc($res))
    {
    
	     $id = $rows['id'];
    }
	
	$type=mysqli_real_escape_string($con,$_POST['type']);
	
	$qrcode=mysqli_real_escape_string($con,$_POST['qrcode']);
	
	
	$query="select * from tbl_qrcode_manage where qrcode ='$qrcode' and Vehicle_Reg_no='$Vehicle_No' and is_active=1";
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	
	//print_r($query);
	//exit();
	
	if($count == 1)
    {
	 
     if($countkey == 1)
     {
        
		 $query="select t1. *, t2.name as statefinal,t3.name as cityfinal from   tbl_customer_master t1 inner join states t2 on t1.state=t2.id inner join cities t3 on t1.city=t3.id where Customer_Code ='$Customer_Code' and is_active=1";
		 $res=mysqli_query($con,$query);
		 $count=mysqli_num_rows($res);
		 
		 
		 	while($rows = mysqli_fetch_assoc($res))
     {
    
		    $Customer_Name  = $rows['Customer_Name'];
			$Mobile  = $rows['Mobile'];
			$Credit_limit  = $rows['Credit_limit'];
			
			$dataCustomernew[] = $rows; 

     }
		 
		
		 
		if($count == 1)
		{
		   
			
			
		   $query="select t1.Registration_Number,t2.Driver_Name,t2.Driver_Mobile from  tbl_customer_vehicle_master t1 left join tbl_customer_vehicle_driver t2 on t1.Registration_Number=t2.Registration_Number
		   where t1.Registration_Number ='$Vehicle_No' and t1.Customer_code='$Customer_Code' and t1.is_active=1";
	       $res=mysqli_query($con,$query);
		   
		  // print_r($query);
		   //exit();
		   
		   while($rows = mysqli_fetch_assoc($res))
		   {
    
			    $Registration_Number  = $rows['Registration_Number'];
				$Driver_Name  = $rows['Driver_Name'];
				$Driver_Mobile  = $rows['Driver_Mobile'];

           }
		 
		   
		   
		   $count=mysqli_num_rows($res);
		   
		   
		   
		   
		   
		if($type=='petrol') 
		{			
		   
			
		$sql="SELECT item_code,t3.price,t2.id ,t1.request_id,t1.request_date,Request_Type,Request_Value,Item_name 
		as Petrol_Diesel_Type,current_driver_mobile from  
		tbl_customer_request_petroldiesel t1 
		inner join tbl_price_list_master t2 on t1.PetrolDiesel_Type=t2.id
		inner join tbl_item_price_list t3 on t3.item_id=t2.id
		
		

		where customer_code='$Customer_Code' and Vehicle_Reg_No ='$Vehicle_No' and t1.is_active=1 and t2.is_active=1 and t3.is_active=1 
		and Execution_date is null";
		
		
		//print_r($sql);
		//exit();
   
	
        $res1  = mysqli_query($con,$sql);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$data[] = $row; 
		
		 }
		}
		
		else{
		 
		 $sql="SELECT t1.request_id,t1.request_date,t2.id,t2.item_code,Item_name,t3.Price,Volume_ltr,current_driver_mobile from   tbl_customer_request_lube 
		 t1 inner join tbl_price_list_master
		t2 on t1.Item_id=t2.id
		inner join tbl_item_price_list t3 on t3.item_id=t2.id
		

		where customer_code='$Customer_Code' and Vehicle_Reg_No ='$Vehicle_No' and t1.is_active=1 and t2.is_active=1 and t3.is_active=1  and Execution_date is null";
        
	 //  print_r($sql);
		//exit();
       
	
        $res1  = mysqli_query($con,$sql);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$data[] = $row; 
		
		 }
		}
		 
			
						
          $json = array( "msg"=>"QR SCAN!" ,"key"=>$key,"Customer_Name"=>$Customer_Name,"Customer_Code"=>$Customer_Code,"Credit_limit"=>$Credit_limit,"Registration_Number"=>$Registration_Number,"Driver_Name"=>$Driver_Name,"Driver_Mobile"=>$Driver_Mobile,'Customer_Request'=>$data,'Customer_full_detail'=>$dataCustomernew);
          $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);
		}
		else
		{
			 $json = array("msg"=>"login failed..! Not Valid Customer Code", "key"=>$key,);
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
		       $json = array( "msg"=>"Not Valid QR Code..!" );
	           $json1 = array('status'=>"failed","data"=> $json);
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