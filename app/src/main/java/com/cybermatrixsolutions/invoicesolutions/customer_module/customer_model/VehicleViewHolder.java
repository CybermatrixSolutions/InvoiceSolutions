package com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;


/**
 * Created by Diwakar on 12/4/2017.
 */

public class VehicleViewHolder extends ViewHolder implements OnClickListener {

   public TextView dricerName,vehicleNumber,make,fueltype,pucdate,model,color;

    @SuppressLint("WrongViewCast")
    public VehicleViewHolder(View itemView) {
        super(itemView);
        this.vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
        this.make = itemView.findViewById(R.id.make);
        this.fueltype = itemView.findViewById(R.id.fueltype);
        this.model = itemView.findViewById(R.id.model);
        this.pucdate = itemView.findViewById(R.id.pucdate);
        this.color = itemView.findViewById(R.id.color);
        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
       // Toast.makeText(view.getContext(),"clicked",Toast.LENGTH_LONG).show();
    }


    public static final boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}