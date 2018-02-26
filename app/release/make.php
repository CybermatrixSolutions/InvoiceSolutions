<?php
include("connection.php");
session_start();

if(isset($_POST['key']))
{
    	
	$key=mysqli_real_escape_string($con,$_POST['key']);
	
	
   // $password=mysqli_real_escape_string($con,$_POST['']);
    $query="select * from   tbl_customer_master where user_token ='$key'";
    $res=mysqli_query($con,$query);
    $count=mysqli_num_rows($res);
	while($rows = mysqli_fetch_assoc($res))
     {
    
	 $id = $rows['id'];
	 $RO_CODE = $rows['RO_Code'];

     }
     if($count == 1)
     {
		$sql="SELECT * from tpl_vechile_make where IsActive=1";
         
		//print_r($sql);
		//exit();
	
        $res1  = mysqli_query($con,$sql);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$data[] = $row; 
		
		 }
		 
						
          $json = array( "msg"=>"Make!" ,"key"=>$key,'Make'=>$data);
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