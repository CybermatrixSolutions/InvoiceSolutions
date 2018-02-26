package com.cybermatrixsolutions.invoicesolutions.customer_module.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.DriversList;
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.VehicleViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class VehicleListAdapter extends Adapter<VehicleViewHolder> {
    Context context;
    List<DriversList>fuellist;
    List<DriversList> arraylist;
    public VehicleListAdapter(Context context, List<DriversList> fuellist) {
        this.context = context;
        this.fuellist = fuellist;
        arraylist = new ArrayList<>();
        arraylist.addAll(fuellist);
    }
    @Override
    public VehicleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(this.context).inflate(R.layout.vehicle_item_layout,parent,false);
        return new VehicleViewHolder(view);
    }
    @Override
    public void onBindViewHolder(VehicleViewHolder holder, int position) {
        DriversList driversList= this.fuellist.get(position);
        holder.vehicleNumber.setText(driversList.getRegistration_Number());
        holder.make.setText(driversList.getMake());
        holder.model.setText(driversList.getModel());
        String requestDate=(driversList.getPuc_date());
        holder.color.setText(driversList.getVan_color());
        String [] date=requestDate.split("-");
        String yy=date[0];
        String MONTH="";
        String mm=date[1];
        String dd=date[2];
        holder.pucdate.setText(dd+"/"+mm+"/"+yy);
    }
    @Override
    public int getItemCount() {
        return this.fuellist.size();
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
                    if (charText.length() != 0 && postDetail.getRegistration_Number().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
                    else if (charText.length() != 0 && postDetail.getVan_color().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
            }
        }
        this.notifyDataSetChanged();
    }}
