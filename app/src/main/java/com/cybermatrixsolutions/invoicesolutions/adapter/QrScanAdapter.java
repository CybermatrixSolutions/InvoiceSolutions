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

public class QrScanAdapter extends RecyclerView.Adapter<QrScanAdapter.QrViewHolder> {

    Context context;
    private List<QrScanResult> qrScanResultList;

    public QrScanAdapter(Context context, List<QrScanResult> qrScanResultList) {
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
        holder.fuel.setText(qrScanResultList.get(position).getFormate());
        holder.lube.setText(qrScanResultList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return qrScanResultList.size();

    }

    public class QrViewHolder extends RecyclerView.ViewHolder{

        TextView fuel;
        TextView lube;
        public QrViewHolder(View itemView) {
            super(itemView);
            fuel =(TextView)itemView.findViewById(R.id.fuel);
            lube =(TextView)itemView.findViewById(R.id.lube);
        }
    }
}
