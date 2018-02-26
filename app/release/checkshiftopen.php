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
		$sql8="SELECT t1.id from shifts t1 inner join shift_personnel t2 on t1.id=t2.shift_id 
		where personnel_id='$id' and closer_date is null";
         
		//print_r($sql);
		//exit();
	
        $res1  = mysqli_query($con,$sql8);
		 $count=mysqli_num_rows($res1);
		
		
		if($count == 1)
        {
		  $json = array( "msg"=>"Shift Open!" ,"key"=>$key);
          $json1 = array('status'=>"success","data"=> $json);
          header('Content-type: application/json');   
		  echo json_encode($json1);
		}
		else
		{
		  $json = array( "msg"=>"Shift Not Open!" ,"key"=>$key);
          $json1 = array('status'=>"fail","data"=> $json);
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