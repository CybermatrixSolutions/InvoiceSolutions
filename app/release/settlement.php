<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	$shift_id=mysqli_real_escape_string($con,$_POST['shift_id']);
	
	$trans_date=mysqli_real_escape_string($con,$_POST['trans_date']);
	
	
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
		 
		
		 
		 
		
        
		$sql="SELECT sum(trans_amount) as trans_amount_p from tbl_customer_transaction_invoice where Trans_By='$id' 
		and shift_id='$shift_id' and cust_type='credit' and petrol_or_lube=1 ";
         
		
	
        $res1  = mysqli_query($con,$sql);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$trans_amount_p= $row['trans_amount_p'];
		
		 }
		// print_r($sql);
		 //exit();
		 
		 $sql3="SELECT sum(trans_amount) as trans_amount_l from tbl_customer_transaction_invoice where Trans_By='$id' 
		and shift_id='$shift_id' and cust_type='credit' and petrol_or_lube=2 ";
         
		
	
        $res3  = mysqli_query($con,$sql3);
   
		 while($row = mysqli_fetch_assoc($res3))
		 {
			
			$trans_amount_l = $row['trans_amount_l']; 
		
		 }
		 
		 
		
		$sql2="SELECT t1.Nozzle_No,t1.Nozzle_Start,t1.Nozzle_End,t4.price,t1.test,t1.reading from tbl_ro_nozzle_reading t1 
		inner join tbl_pedestal_nozzle_master t2 on t1.Nozzle_No=t2.Nozzle_Number 
		inner join tbl_price_list_master t3 on t3.id=t2.fuel_type 
		inner join tbl_item_price_list t4 on t3.id=t4.item_id
		inner join shift_pedestal t5 on t5.pedestal_id=t2.Pedestal_id
		inner join shifts t6 on t6.id=t5.shift_id
		where Reading_by='$id' and t4.is_active=1 and t3.is_active=1 and t2.is_active=1 and t6.id='$shift_id' and t6.fuel=0
		and t1.shift_id='$shift_id' AND Nozzle_End is NOT NULL";
		
	
         
		//print_r($sql2);
		//exit();
	
        $res2  = mysqli_query($con,$sql2);
   
		 while($row = mysqli_fetch_assoc($res2))
		 {
			
			$data2[] = $row; 
		
		 }
		 
		 
		 
						
          $json = array( "msg"=>"Settlement!" ,"key"=>$key,'Credit_Petrol'=>$trans_amount_p,'Credit_lube'=>$trans_amount_l,'Nozzle_No'=>$data2);
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