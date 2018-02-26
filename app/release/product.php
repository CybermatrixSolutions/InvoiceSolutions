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
		$sql="SELECT DISTINCT Item_Code,Item_Name,t3.Price,Volume_ltr,t1.id FROM tbl_price_list_master t1 inner join tbl_stock_item_group t2 
		on t1.Stock_Group=t2.id INNER join tbl_item_price_list t3 on t3.item_id=t1.id
		where RO_Code='$RO_CODE' and t1.is_active=1 and t2.is_active=1 and t3.is_active=1 and Group_Name<>'FUEL'";
         
		//print_r($sql);
		//exit();
	
        $res1  = mysqli_query($con,$sql);
   
		 while($row = mysqli_fetch_assoc($res1))
		 {
			
			$data[] = $row; 
		
		 }
		 
						
          $json = array( "msg"=>"Product!" ,"key"=>$key,'Product'=>$data);
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