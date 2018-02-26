<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	$Policy_type=mysqli_real_escape_string($con,$_POST['Policy_type']);
	$Dep_date=mysqli_real_escape_string($con,$_POST['Dep_date']);
	$Arr_date=mysqli_real_escape_string($con,$_POST['Arr_date']);
	$hours=mysqli_real_escape_string($con,$_POST['hours']);
	$Source_location=mysqli_real_escape_string($con,$_POST['Source_location']);
	
	$Dest_location=mysqli_real_escape_string($con,$_POST['Dest_location']);
	$Class=mysqli_real_escape_string($con,$_POST['Class']);
	$Type_Mode=mysqli_real_escape_string($con,$_POST['Type_Mode']);
	$KMS=mysqli_real_escape_string($con,$_POST['KMS']);
	$Per_Amount=mysqli_real_escape_string($con,$_POST['Per_Amount']);
	
	$Claim_cash = mysqli_real_escape_string($con,$_POST['Claim_cash']);
	
	$Claim_card=mysqli_real_escape_string($con,$_POST['Claim_card']);
	
	$Claim_Total = mysqli_real_escape_string($con,$_POST['Claim_Total']);
	
	$bill_detail = mysqli_real_escape_string($con,$_POST['bill_detail']);
	
	$Ref_no = mysqli_real_escape_string($con,$_POST['Ref_no']);
	$APP_REF = mysqli_real_escape_string($con,$_POST['APP_REF']);
	
	$Remarks_employee = mysqli_real_escape_string($con,$_POST['Remarks_employee']);
	
	 
	 
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from  tbl_user_master where user_token ='$key' and is_active=1";
	
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	
	
	
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $Emp_code = $rows['Emp_code'];
	 
     
     }
     if($count == 1)
     {
		 		
		 $query="insert into tbl_employee_tada(Emp_code,Policy_type,Dep_date,Arr_date,hours,Source_location,Dest_location,Class,Type_Mode,KMS,Per_Amount,Claim_cash,Claim_card,Claim_Total,bill_detail,Ref_no,APP_REF,Remarks_employee)
		 values ('$Emp_code','$Policy_type','$Dep_date','$Arr_date','$hours','$Source_location','$Dest_location','$Class','$Type_Mode','$KMS','$Per_Amount','$Claim_cash','$Claim_card','$Claim_Total','$bill_detail','$Ref_no','$APP_REF','$Remarks_employee')";

		 //print_r($query);
		 //exit();
		 $res=mysqli_query($con,$query);
		 $count=mysqli_num_rows($res);
		 
		 		 		
		 $json = array( "msg"=>"ta-da convenence Added..!" ,"key"=>$key);
         $json1 = array('status'=>"success","data"=> $json);
         header('Content-type: application/json');   
		 echo json_encode($json1);
	 
           

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