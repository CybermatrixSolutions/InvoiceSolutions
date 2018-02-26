package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.CustomerRequestList;

import java.util.List;

/**
 * Created by Diwakar on 10/6/2017.
 */

public class PetrolCustomerDetailWithoutQrCodeAdapter extends RecyclerView.Adapter<PetrolCustomerDetailWithoutQrCodeAdapter.QrViewHolder> {

    Context context;
    private List<CustomerRequestList> qrScanResultList;
    public PetrolCustomerDetailWithoutQrCodeAdapter(Context context, List<CustomerRequestList> qrScanResultList) {
        this.context = context;
        this.qrScanResultList = qrScanResultList;
    }

    @Override
    public QrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_code_item_layout, parent, false);
        return  new QrViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(QrViewHolder holder, int position) {
        holder.fuel.setText(qrScanResultList.get(position).getPetrol_Diesel_Type());
        String requestDate=qrScanResultList.get(position).getRequest_date();
        String requestType=qrScanResultList.get(position).getRequestType();
       // holder.lube.setText(qrScanResultList.get(position).getRequest_Value()+" Ltr");
        String [] datetime=requestDate.split(" ");
        String [] date=datetime[0].split("-");
        String time=datetime[1];
        String yy=date[0];
        String MONTH="";
        String mm=date[1];
        String dd=date[2];
        holder.date.setText(dd+"-"+mm+"-"+yy+" "+time);
         try {
            if(requestType.equalsIgnoreCase("FULL TANK")){
                holder.lube.setText("Full Tank");
            }
            if (requestType.equalsIgnoreCase("RS")){
                holder.lube.setText("Rs. "+qrScanResultList.get(position).getRequest_Value());
            }
            if (requestType.equalsIgnoreCase("LTR")){
                holder.lube.setText(qrScanResultList.get(position).getRequest_Value()+" Ltr");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        return qrScanResultList.size();

    }

    public class QrViewHolder extends RecyclerView.ViewHolder{

        TextView fuel;
        TextView lube,date;
        public QrViewHolder(View itemView) {
            super(itemView);
            fuel = itemView.findViewById(R.id.fuel);
            lube = itemView.findViewById(R.id.lube);
            date = itemView.findViewById(R.id.request_date);
        }
    }
}
