package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.NozzleReadingModel;

import java.util.List;

/**
 * Created by Diwakar on 9/14/2017.
 */

public class LubeSalesSettlementAdapter extends RecyclerView.Adapter<LubeSalesSettlementAdapter.SpinnerViewHolder> {
    List<NozzleReadingModel> readingModelList;
   // List<ProductDetail> arraylist;
    Context context;
    public static MyClickListerer myClickListerer;
    public LubeSalesSettlementAdapter(List<NozzleReadingModel> productDetail, Context context) {
        this.readingModelList = productDetail;
        this.context = context;
     //   arraylist=new ArrayList<>();
       // arraylist.addAll(readingModelList);
    }

    @Override
    public SpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.lube_settlement_layout, parent, false);
        return  new SpinnerViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(SpinnerViewHolder holder,final int position) {
            holder.NozzleNumber.setText(readingModelList.get(position).getLubeName());
            holder.salesAmount.setText("Rs. "+readingModelList.get(position).getPrice());
            int SNO=position+1;
            holder.sno.setText(String.valueOf(SNO));
    }
    @Override
    public int getItemCount() {
        return readingModelList.size();
    }
    public interface MyClickListerer {
        void onItemClick(int position, View view);
    }
    public class SpinnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView NozzleNumber,sno;
        TextView salesAmount;
        public SpinnerViewHolder(View itemView) {
            super(itemView);
            NozzleNumber = itemView.findViewById(R.id.nozzleNumber);
            salesAmount = itemView.findViewById(R.id.salesAmount);
             sno= itemView.findViewById(R.id.sno);
        }
        @Override
        public void onClick(View view) {
        }
    }
    }
