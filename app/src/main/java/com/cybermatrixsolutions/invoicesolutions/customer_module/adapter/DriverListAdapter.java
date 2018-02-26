package com.cybermatrixsolutions.invoicesolutions.customer_module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriversList;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Diwakar on 11/30/2017.
 */

public class DriverListAdapter extends Adapter<DriverListAdapter.ViewHolder> {
    Context context;
    List<DriversList>fuellist;
    List<DriversList> arraylist;

    public DriverListAdapter(Context context,List<DriversList> fuellist) {
        this.context = context;
        this.fuellist = fuellist;
        arraylist = new ArrayList<>();
        arraylist.addAll(fuellist);
    }

    @Override
    public DriverListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.driver_list_item_layout,parent,false);
        return new DriverListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DriverListAdapter.ViewHolder holder, int position) {
        DriversList driversList= this.fuellist.get(position);
        holder.dricerName.setText(driversList.getDriver_Name());
        holder.driverMobile.setText(driversList.getDriver_Mobile());
        holder.dlNumber.setText(driversList.getDriver_Licence_No());
        holder.vehicleNumber.setText(driversList.getRegistration_Number());
        String requestDate=(driversList.getValid_upto());
        String [] date=requestDate.split("-");
        String yy=date[0];
        String MONTH="";
        String mm=date[1];
        String dd=date[2];
        holder.t7.setText(dd+"/"+mm+"/"+yy);
    }

    @Override
    public int getItemCount() {
        return this.fuellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout executionDateLayout;
        TextView dricerName,vehicleNumber,driverMobile,dlNumber,t7;
        public ViewHolder(View itemView) {
            super(itemView);
            this.dricerName = itemView.findViewById(R.id.dricerName);
            this.vehicleNumber = itemView.findViewById(R.id.vehicleNumber);
            this.driverMobile = itemView.findViewById(R.id.driverMobile);
            this.dlNumber = itemView.findViewById(R.id.dlNumber);
            this.t7 = itemView.findViewById(R.id.t7);

            this.executionDateLayout =itemView.findViewById(R.id.executionDateLayout);

        }
    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        fuellist.clear();
        if (charText.length() == 0) {
            fuellist.addAll(arraylist);

        } else {
            for (DriversList postDetail : arraylist) {
                final  String shop=postDetail.getRegistration_Number();
                if(shop!=null)

                    if (charText.length() != 0 && postDetail.getDriver_Name().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
                    else if (charText.length() != 0 && postDetail.getDriver_Mobile().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
                    else if (charText.length() != 0 && postDetail.getDriver_Licence_No().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
            }
        }
        this.notifyDataSetChanged();
    }

}
