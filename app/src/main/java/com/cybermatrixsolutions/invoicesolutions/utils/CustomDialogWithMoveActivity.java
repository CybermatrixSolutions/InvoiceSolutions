package com.cybermatrixsolutions.invoicesolutions.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;


/**
 * Created by abcd on 11/13/2017.
 */

public class CustomDialogWithMoveActivity {
Context context;
    public static void customDialogwithsingleButton(final Context context, String msg , int id, final Context context1) {
     final Dialog ab=new Dialog(context);
     ab.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
     View view= LayoutInflater.from(context).inflate(R.layout.popdialogwithsinglebutton,null,false);
     ab.setContentView(R.layout.popdialogwithsinglebutton);
     TextView alertmessage=(TextView)ab.findViewById(R.id.alertmessage);
     ImageView image=(ImageView)ab.findViewById(R.id.image);
     image.setImageResource(id);
        TextView clickok=(TextView)ab.findViewById(R.id.clickok);
     alertmessage.setText(msg);
     clickok.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             ab.dismiss();
             Intent i=new Intent(context.getApplicationContext(),context1.getClass());
             ((Activity)context).startActivity(i);
         }
     });



     ab.show();





 }



}
