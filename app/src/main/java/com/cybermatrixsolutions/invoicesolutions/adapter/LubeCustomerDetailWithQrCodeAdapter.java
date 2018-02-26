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

public class LubeCustomerDetailWithQrCodeAdapter extends RecyclerView.Adapter<LubeCustomerDetailWithQrCodeAdapter.QrViewHolder> {

    Context context;
    private List<CustomerRequestList> qrScanResultList;
    public LubeCustomerDetailWithQrCodeAdapter(Context context, List<CustomerRequestList> qrScanResultList) {
        this.context = context;
        this.qrScanResultList = qrScanResultList;
    }

    @Override
    public QrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lube_with_qr_code_item_layout, parent, false);
        return  new QrViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(QrViewHolder holder, int position) {
        holder.lubeName.setText(qrScanResultList.get(position).getItem_code());
        String requestDate=qrScanResultList.get(position).getRequest_date();
        holder.qty.setText(qrScanResultList.get(position).getVolume_ltr()+" Ltr");
        String [] datetime=requestDate.split(" ");
        String [] date=datetime[0].split("-");
        String time=datetime[1];
        String yy=date[0];
        String MONTH="";
        String mm=date[1];
        String dd=date[2];
        holder.date.setText(dd+"-"+mm+"-"+yy+" "+time);
    }
    @Override
    public int getItemCount() {
        return qrScanResultList.size();
    }

    public class QrViewHolder extends RecyclerView.ViewHolder{

        TextView lubeName;
        TextView qty,date;
        public QrViewHolder(View itemView) {
            super(itemView);
            lubeName = itemView.findViewById(R.id.itemName);
            qty = itemView.findViewById(R.id.qty);
            date = itemView.findViewById(R.id.request_date);
        }
    }
}
