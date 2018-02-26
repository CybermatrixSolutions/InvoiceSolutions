<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	$nozzle_nos=mysqli_real_escape_string($con,$_POST['nozzle_nos']);
	
	$nozzle_nosarr=explode(",",$nozzle_nos);  
	
	$nozzle_read=mysqli_real_escape_string($con,$_POST['nozzle_read']);
	
	$nozzle_readarr=explode(",",$nozzle_read);  
	
	$test=mysqli_real_escape_string($con,$_POST['test']);
	
	$test_readarr=explode(",",$test);  
	
	$reading=mysqli_real_escape_string($con,$_POST['reading']);
	
	$reading_readarr=explode(",",$reading);
	
	$Reading_Date=mysqli_real_escape_string($con,$_POST['Reading_Date']);
	
	$Reading_type=mysqli_real_escape_string($con,$_POST['Reading_type']);
	
	
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from   tbl_personnel_master where user_token ='$key'";
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $Personnel_Code = $rows['id'];
	 $RO_CODE = $rows['RO_Code'];
	 $id = $rows['id'];

     }
     if($count == 1)
     {
		$sql="SELECT id from shifts where shift_manager='$id' and is_active=1 and closer_date is null";
         
		$res1  = mysqli_query($con,$sql);
		
		 
		while($rows = mysqli_fetch_assoc($res1))
		{
    
			$shift_id = $rows['id'];
			

		} 
		 
		 
        
		if($Reading_type=='start')
		{
			
			for ($i = 0; $i < count($nozzle_nosarr); $i++) 
			{
				
				
				
				$sql="insert into  tbl_ro_nozzle_reading(RO_code,Nozzle_No,Reading_Date,Nozzle_Start,Reading_by,shift_id) values 
				('$RO_CODE','$nozzle_nosarr[$i]','$Reading_Date','$nozzle_readarr[$i]','$Personnel_Code','$shift_id')";
				
				
				$res1  = mysqli_query($con,$sql);
				
				
				
			}
			
			 
						
			$json = array( "msg"=>"Reading success..!" ,"key"=>$key);
			$json1 = array('status'=>"success","data"=> $json);
			header('Content-type: application/json');   
			echo json_encode($json1);
		}
		else
		{
			for ($i = 0; $i < count($nozzle_nosarr); $i++) 
			{
				
				
				
				$sql="update tbl_ro_nozzle_reading set Nozzle_End='$nozzle_readarr[$i]',shift_id='$shift_id',
				test= '$test_readarr[$i]',reading='$reading_readarr[$i]'
				where Nozzle_No='$nozzle_nosarr[$i]' 
				and Nozzle_End is null";
   

				$res1  = mysqli_query($con,$sql);
				
				//print_r($sql);
				//exit();
				
				
				$sq2="insert into  tbl_ro_nozzle_reading(RO_code,Nozzle_No,Reading_Date,Nozzle_Start,Reading_by) values 
				('$RO_CODE','$nozzle_nosarr[$i]','$Reading_Date','$nozzle_readarr[$i]','$Personnel_Code')";
				
				
				$res1  = mysqli_query($con,$sq2);
				
				
				
				
				
			}
			
			$created_at = date('Y-m-d H:i:s');
			
		    $sql3="update shifts set closer_date='$created_at',is_active='0' where id='$shift_id'";
			
			//print_r($sql3);
//exit();			
				
		    $res1  = mysqli_query($con,$sql3);
			
			
   
				 
						
			$json = array( "msg"=>"Reading success..!" ,"key"=>$key);
			$json1 = array('status'=>"success","data"=> $json);
			header('Content-type: application/json');   
			echo json_encode($json1);
		}
		

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