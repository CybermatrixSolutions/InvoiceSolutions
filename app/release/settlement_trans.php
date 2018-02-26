<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	$shift_id=mysqli_real_escape_string($con,$_POST['shift_id']);
	
	$Nozzle_Amount=mysqli_real_escape_string($con,$_POST['Nozzle_Amount']);
	
	$Credit_fuel=mysqli_real_escape_string($con,$_POST['Credit_fuel']);
	$Credit_lube=0;
	
	
	$mode=mysqli_real_escape_string($con,$_POST['mode']);
	
	$modearr=explode(",",$mode);  
	
	$amount=mysqli_real_escape_string($con,$_POST['amount']);
	
	$amount_arr=explode(",",$amount);
	
	
	
	//$Cash=mysqli_real_escape_string($con,$_POST['Cash']);
	//$Petrocard=mysqli_real_escape_string($con,$_POST['Petrocard']);
	//$Debit_credit_card=mysqli_real_escape_string($con,$_POST['Debit_credit_card']);
	
	//$Wallet=mysqli_real_escape_string($con,$_POST['Wallet']);
	
	
	$Diff=mysqli_real_escape_string($con,$_POST['Diff']);


	
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from   tbl_personnel_master where user_token ='$key'";
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $id = $rows['id'];
	 $RO_CODE = $rows['RO_Code'];

     }
     if($count == 1)
     {
        
		
		
		
		
		
		
		$sql="insert into tbl_shift_settlement(Nozzle_Amount,Credit_fuel,Credit_lube,Cash,Petrocard,Debit_credit_card,
		Wallet,trans_by,Diff,shift_id,type) 
		values ('$Nozzle_Amount','$Credit_fuel','0','0','0','0',
		'0','$id','$Diff','$shift_id',1) ";
         
	
        $res2  = mysqli_query($con,$sql);
		
		$last_id = mysqli_insert_id($con);
		
		for ($i = 0; $i < count($modearr); $i++) 
			{
				
				
				
				$sql="insert into  paymentmode_sattlement(paymentmode_id,sattlement_id,amount,type) values 
				('$modearr[$i]','$last_id','$amount_arr[$i]',1)";
				
				
				$res1  = mysqli_query($con,$sql);
				
				
				
			}
			
		
		  $sql3="update shifts set fuel='1' where id='$shift_id'";
				
				
				
		  $res1  = mysqli_query($con,$sql3);
   
		
		 
		 
						
          $json = array( "msg"=>"Settlement Done!" ,"key"=>$key);
          $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);
		

    }

    else

    {
             $json = array("msg"=>"login failed..! Not Valid ", "key"=>$key,);
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