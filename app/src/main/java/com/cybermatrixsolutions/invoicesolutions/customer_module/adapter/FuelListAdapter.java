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
import com.cybermatrixsolutions.invoicesolutions.customer_module.customer_model.FuelRequestModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Diwakar on 11/30/2017.
 */

public class FuelListAdapter extends Adapter<FuelListAdapter.ViewHolder> {
    Context context;
    ArrayList<FuelRequestModel>fuellist;
    List<FuelRequestModel> arraylist;

    public FuelListAdapter(Context context, ArrayList<FuelRequestModel> fuellist) {
        this.context = context;
        this.fuellist = fuellist;
        arraylist = new ArrayList<>();
        arraylist.addAll(fuellist);
    }

    @Override
    public FuelListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewfuellayout,parent,false);
        return new FuelListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FuelListAdapter.ViewHolder holder, int position) {
        FuelRequestModel fuelRequestModel= this.fuellist.get(position);
        holder.t1.setText(fuelRequestModel.getVehicle_reg_No());
        String requestDate= (fuelRequestModel.getRequest_date());

        String [] datetime=requestDate.split(" ");
        String [] date=datetime[0].split("-");
        String time=datetime[1];
        String yy=date[0];
        String MONTH="";
        String mm=date[1];
        String dd=date[2];

        holder.t2.setText(dd+"/"+mm+"/"+yy+" ");
        holder.t3.setText(fuelRequestModel.getItem_Name());
        holder.t4.setText(fuelRequestModel.getRequest_Type());

        String RequestValue=fuelRequestModel.getRequest_Value();
        if ((RequestValue!=null)){
            if(!(RequestValue.equals("0"))) {
                holder.requestValueLayout.setVisibility(View.VISIBLE);
                holder.t5.setText(RequestValue);
            }
            else {
                holder.requestValueLayout.setVisibility(View.GONE);

            }
        }else {
            holder.requestValueLayout.setVisibility(View.GONE);

        }
        String ExecutionDate=fuelRequestModel.getExecution_date();
        if(ExecutionDate!=null){
             datetime=ExecutionDate.split(" ");
             date=datetime[0].split("-");
             time=datetime[1];
             yy=date[0];
             MONTH="";
             mm=date[1];
            dd=date[2];
            holder.executionDateLayout.setVisibility(View.VISIBLE);
//            holder.t6.setText(ExecutionDate);
            holder.t6.setText(dd+"-"+mm+"-"+yy+" "+time);
        }else {
            holder.executionDateLayout.setVisibility(View.GONE);

        }

        holder.t7.setText(fuelRequestModel.getRequest_id());
    }

    @Override
    public int getItemCount() {
        return this.fuellist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout requesrTypeLayout;
        private final LinearLayout requestValueLayout;
        private final LinearLayout executionDateLayout;
        TextView t1,t2,t3,t4,t5,t6,t7;
        public ViewHolder(View itemView) {
            super(itemView);
            this.t1 = itemView.findViewById(R.id.t1);
            this.t2 = itemView.findViewById(R.id.t2);
            this.t3 = itemView.findViewById(R.id.t3);
            this.t4 = itemView.findViewById(R.id.t4);
            this.t5 = itemView.findViewById(R.id.t5);
            this.t6 = itemView.findViewById(R.id.t6);
            this.t7 = itemView.findViewById(R.id.t7);
            this.requesrTypeLayout =itemView.findViewById(R.id.requestTypeLayout);
            this.requestValueLayout =itemView.findViewById(R.id.requestValueLayout);
            this.executionDateLayout =itemView.findViewById(R.id.executionDateLayout);

        }
    }
    public void filter(String charText) {

        charText = charText.toLowerCase(Locale.getDefault());

        fuellist.clear();
        if (charText.length() == 0) {
            fuellist.addAll(arraylist);

        } else {
            for (FuelRequestModel postDetail : arraylist) {
                final  String shop=postDetail.getVehicle_reg_No();
                if(shop!=null)

                    if (charText.length() != 0 && postDetail.getVehicle_reg_No().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
                    else if (charText.length() != 0 && postDetail.getRequest_Type().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
                    else if (charText.length() != 0 && postDetail.getRequest_date().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }

            }
        }
        this.notifyDataSetChanged();
    }

}
