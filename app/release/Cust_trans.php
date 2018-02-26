<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from   tbl_personnel_master where user_token ='$key'";
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	
	$customer_code=mysqli_real_escape_string($con,$_POST['customer_code']);

	$item_code=mysqli_real_escape_string($con,$_POST['item_code']);
	
	$itemcode_arr=explode(",",$item_code);  
	
	
	
	$item_price=mysqli_real_escape_string($con,$_POST['item_price']);
	
	$item_price_arr=explode(",",$item_price);  
	
	
	$petrol_or_lube=mysqli_real_escape_string($con,$_POST['petrol_or_lube']);
	$slip_detail=mysqli_real_escape_string($con,$_POST['slip_detail']);
	$petroldiesel_type=mysqli_real_escape_string($con,$_POST['petroldiesel_type']);
	$petroldiesel_qty=mysqli_real_escape_string($con,$_POST['petroldiesel_qty']);
	$Request_id=mysqli_real_escape_string($con,$_POST['Request_id']);
	$Vehicle_Reg_No=mysqli_real_escape_string($con,$_POST['Vehicle_Reg_No']);
	$Driver_Mobile=mysqli_real_escape_string($con,$_POST['Driver_Mobile']);
	
	$Trans_Mobile=mysqli_real_escape_string($con,$_POST['Trans_Mobile']);
	
	
	$Trans_By=mysqli_real_escape_string($con,$_POST['Trans_By']);
	
	$Total=mysqli_real_escape_string($con,$_POST['Total']);
	
	
	$cust_name=mysqli_real_escape_string($con,$_POST['cust_name']);
	
	
	$Cust_mobile=mysqli_real_escape_string($con,$_POST['Cust_mobile']);
	
	$Cust_GST=mysqli_real_escape_string($con,$_POST['Cust_GST']);
	
	$cust_type=mysqli_real_escape_string($con,$_POST['cust_type']);
	
	
	 
	
	
	
	if($petroldiesel_qty==null)
	{
		$petroldiesel_qty=0;
	}
	
	
		
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $id = $rows['id'];
	 $RO_CODE = $rows['RO_Code'];

     }
     if($count == 1)
     {
		 
		  function random_numbers($digits) 
		  {
				$min = pow(10, $digits - 1);
				$max = pow(10, $digits) - 1;
				return mt_rand($min, $max);
		  }

		$invoice_number="GARINV".random_numbers(6);
		  
		$trans_id="GARTRANS".random_numbers(6);
		
		
		if ($petroldiesel_type=='Full Tank')
		{
				  				
				$Total=$item_price *	$petroldiesel_qty;	
				
				 
				  
				  
		}
		else if($petroldiesel_type=='Ltr')
		{
				 
				
				$Total=$item_price *	$petroldiesel_qty;	
				
				 
				  
				  
		}
		else if ($petroldiesel_type=='Rs')
		{
			
			$petroldiesel_qty=number_format(($Total %	$item_price),2);	
			
			
			
		}
		
		$sql8="SELECT t1.id from shifts t1 inner join shift_personnel t2 on t1.id=t2.shift_id 
		where personnel_id='$id' and closer_date is null";
         
		$res8  = mysqli_query($con,$sql8);
		
		 
		while($rows = mysqli_fetch_assoc($res8))
		{
    
			$shift_id = $rows['id'];
			

		} 
		  
		$sql="insert into tbl_customer_transaction_invoice(trans_id,trans_amount,tax_amount,invoice_number,Trans_By,petrol_or_lube,cust_type,customer_code,RO_CODE,shift_id)
        values ('$trans_id','$Total',0,'$invoice_number','$Trans_By','$petrol_or_lube','$cust_type','$customer_code','$RO_CODE','$shift_id')";
       

	
        $res1  = mysqli_query($con,$sql);
		
		$countpro = count($itemcode_arr);
		
		
		
		
		
		
		
        
			
			for ($i = 0; $i < count($itemcode_arr); $i++) 
			{
				
			
				$sql="insert into tbl_customer_transaction(customer_code,RO_code,item_code,item_price,petrol_or_lube,slip_detail,
				petroldiesel_type,petroldiesel_qty,Request_id,Vehicle_Reg_No,Driver_Mobile,Trans_By,Trans_Mobile,trans_id,cust_name,Cust_mobile,Cust_GST,shift_id)
				values ('$customer_code','$RO_CODE','$itemcode_arr[$i]','$item_price_arr[$i]','$petrol_or_lube','$slip_detail',
				'$petroldiesel_type','$petroldiesel_qty','$Request_id','$Vehicle_Reg_No','$Driver_Mobile','$Trans_By','$Trans_Mobile','$trans_id','$cust_name','$Cust_mobile','$Cust_GST','$shift_id')";
         
			
				$res1  = mysqli_query($con,$sql);
				
				 
				
			
				
				
			}
			
			
		
		$sql1="update tbl_customer_request_lube set Execution_date=curdate() where request_id='$Request_id'";
       
	
	
        $res2  = mysqli_query($con,$sql1);
		
		$sql2="update tbl_customer_request_petroldiesel set Execution_date=curdate() where request_id='$Request_id'";
       
	
	
        $res3  = mysqli_query($con,$sql2);
		
			
		
		  
						
          $json = array( "msg"=>'Trans success..!'  ,"key"=>$key);
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