<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from   tbl_user_master where user_token ='$key'";
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $Emp_Group = $rows['Emp_Group'];
	 $Emp_code = $rows['Emp_code'];

     }
     if($count == 1)
     {
        
		
		$sql="Select t1.*,t2.Employee_name  	
		from tbl_employee_applyleave t1 inner join tbl_user_master t2 on t1.emp_code=t2.Emp_code 
		where Approval='$Emp_code' and Approval_Status=0 order by t1.id DESC";
         
		//print_r($sql);
		//exit();
	
        $res1  = mysqli_query($con,$sql);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$data[] = $row; 
		
		 }
		 
						
          $json = array( "msg"=>"leave approve!" ,"key"=>$key,'EmployeeLeave'=>$data);
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