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
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $id = $rows['id'];
	 $RO_CODE = $rows['RO_Code'];

     }
     if($count == 1)
     {
		$sql="SELECT * from shifts where shift_manager='$id' and is_active=1 and closer_date is null";
         
		//print_r($sql);
		//exit();
	
        $res1  = mysqli_query($con,$sql);
		$count=mysqli_num_rows($res1);
		
		if($count == 1)
		{
			 $json = array("msg"=>"Previous Shift not closed", "key"=>$key);
			 $json1 = array('status'=>"failed", "data"=> $json);
			 header('Content-type: application/json'); 
             echo json_encode($json1);

		}	
        else

		{
		
		$sql1="SELECT Pedestal_Number,id from tbl_pedestal_master where RO_code='$RO_CODE' and id not in 
		(select pedestal_id from Shifts t1 inner join shift_pedestal t2 on t1.id=t2.shift_id where ro_code='$RO_CODE' and closer_date is null )";
         
		 
		
		 $res1  = mysqli_query($con,$sql1);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$data[] = $row; 
		
		 }
		 
		 
		$sql2="SELECT Personnel_Name,t1.id from tbl_personnel_master t1 inner join tbl_designation_master t2 on t1.Designation=t2.id
		where t1.RO_Code='$RO_CODE' and t1.is_active=1 and (Designation_Name='salesman' or Designation_Name='shift manager') and t1.id not in 
		(select personnel_id from Shifts t3 inner join shift_personnel t4 on t3.id=t4.shift_id 
		where ro_code='$RO_CODE' and closer_date is null )";
		 
		 $res2  = mysqli_query($con,$sql2);
   
		 while($row = mysqli_fetch_assoc($res2))
		 {
			
			$data1[] = $row; 
		
		 }
		 
		 
						
          $json = array( "msg"=>"Shift!" ,"key"=>$key,'Pedestal'=>$data,'Sales'=>$data1);
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