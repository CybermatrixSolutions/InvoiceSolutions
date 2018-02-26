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

public class LubeListAdapter extends Adapter<LubeListAdapter.ViewHolder> {
    Context context;
    ArrayList<FuelRequestModel>fuellist;
    List<FuelRequestModel> arraylist;
    public LubeListAdapter(Context context, ArrayList<FuelRequestModel> fuellist) {
        this.context = context;
        this.fuellist = fuellist;
        arraylist = new ArrayList<>();
        arraylist.addAll(fuellist);
    }

    @Override
    public LubeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.viewlubelayout,parent,false);
        return new LubeListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LubeListAdapter.ViewHolder holder, int position) {
        FuelRequestModel fuelRequestModel= this.fuellist.get(position);
        holder.t1.setText(fuelRequestModel.getVehicle_reg_No());
        String requestDate=(fuelRequestModel.getRequest_date());
        holder.t3.setText(fuelRequestModel.getItem_Name());
        holder.qty.setText(fuelRequestModel.getQuantity());
        String [] datetime=requestDate.split(" ");
        String [] date=datetime[0].split("-");
        String time=datetime[1];
        String yy=date[0];
        String MONTH="";
        String mm=date[1];
        String dd=date[2];
        holder.t2.setText(dd+"/"+mm+"/"+yy+" ");
        String ExecutionDate=fuelRequestModel.getExecution_date();
        if(ExecutionDate!=null){
            holder.executionDateLayout.setVisibility(View.VISIBLE);
            holder.t6.setText(ExecutionDate);
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

        private final LinearLayout executionDateLayout;
        TextView t1,t2,t3,t5,t6,t7,qty;
        public ViewHolder(View itemView) {
            super(itemView);
            this.t1 = itemView.findViewById(R.id.t1);
            this.t2 = itemView.findViewById(R.id.t2);
            this.t3 = itemView.findViewById(R.id.t3);
            this.t5 = itemView.findViewById(R.id.t5);
            this.t6 = itemView.findViewById(R.id.t6);
            this.t7 = itemView.findViewById(R.id.t7);
            this.qty = itemView.findViewById(R.id.qty);


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
                    else if (charText.length() != 0 && postDetail.getItem_Name().toLowerCase(Locale.getDefault()).contains(charText)) {
                        fuellist.add(postDetail);
                    }
                    else if (charText.length() != 0 && postDetail.getRequest_id().toLowerCase(Locale.getDefault()).contains(charText)) {
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
