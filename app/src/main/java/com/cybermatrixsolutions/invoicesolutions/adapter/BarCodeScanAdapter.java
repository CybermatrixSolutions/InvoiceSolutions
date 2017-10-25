package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.QrScanResult;

import java.util.List;

/**
 * Created by Diwakar on 10/6/2017.
 */

public class BarCodeScanAdapter extends RecyclerView.Adapter<BarCodeScanAdapter.QrViewHolder> {

    Context context;
    private List<QrScanResult> qrScanResultList;

    public BarCodeScanAdapter(Context context, List<QrScanResult> qrScanResultList) {
        this.context = context;
        this.qrScanResultList = qrScanResultList;
    }

    @Override
    public QrViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bar_code_item_layout, parent, false);
        return  new QrViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(QrViewHolder holder, int position) {
        holder.item_code.setText(qrScanResultList.get(position).getFormate());
        holder.item_name.setText(qrScanResultList.get(position).getContent());
        holder.item_price.setText(qrScanResultList.get(position).getFormate());
    }

    @Override
    public int getItemCount() {
        return qrScanResultList.size();
    }
    public class QrViewHolder extends RecyclerView.ViewHolder{
        TextView item_code;
        TextView item_name;
        TextView item_price;

        public QrViewHolder(View itemView) {
            super(itemView);
            item_code =itemView.findViewById(R.id.item_code);
            item_name =itemView.findViewById(R.id.item_name);
            item_price =itemView.findViewById(R.id.item_price);
        }
    }
}
