package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.NozzlList;
import com.cybermatrixsolutions.invoicesolutions.model.NozzleReadingModel;

import java.util.List;

/**
 * Created by Diwakar on 9/14/2017.
 */

public class PetrolDieselSettlementAdapter extends RecyclerView.Adapter<PetrolDieselSettlementAdapter.SpinnerViewHolder> {
    List<NozzlList> readingModelList;
   // List<ProductDetail> arraylist;
    Context context;
    public PetrolDieselSettlementAdapter(List<NozzlList> productDetail, Context context) {
        this.readingModelList = productDetail;
        this.context = context;
     //   arraylist=new ArrayList<>();
    }

    @Override
    public SpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.petrol_diesel_settlement_layout, parent, false);
        return  new SpinnerViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final SpinnerViewHolder holder, final int position) {
          String NozzleNumber=readingModelList.get(position).getNozzle_No();
          final String NozzleStart=readingModelList.get(position).getNozzle_Start();
          final String NozzleEnd=readingModelList.get(position).getNozzle_End();
          String amount=readingModelList.get(position).getPrice();
        if(NozzleStart!=null&&NozzleEnd!=null&&amount!=null&&NozzleNumber!=null) {
              double startreading = Double.parseDouble(NozzleStart);
              double endReading = Double.parseDouble(NozzleEnd);
              double difference = endReading - startreading;
              double Amount = Double.parseDouble(amount);
              double Total = difference * Amount;
              holder.salesAmount.setText(String.valueOf(Total));
              holder.nozzleNumber.setText(NozzleNumber);
              holder.nozzleEnd.setText(NozzleEnd);
              holder.nozzleStart.setText(NozzleStart);
              holder.test.setText(readingModelList.get(position).getTest());
              holder.reading.setText(readingModelList.get(position).getReading());


          }
    }
    @Override
    public int getItemCount() {
        return readingModelList.size();
    }
    public class SpinnerViewHolder extends RecyclerView.ViewHolder{
        TextView nozzleStart,nozzleEnd, nozzleNumber;
        TextView salesAmount;
        TextView test,reading;
        public SpinnerViewHolder(View itemView) {
            super(itemView);
            nozzleStart = itemView.findViewById(R.id.nozzleStart);
            nozzleEnd = itemView.findViewById(R.id.nozzleEnd);
            salesAmount = itemView.findViewById(R.id.amount);
            nozzleNumber = itemView.findViewById(R.id.nozzleNumber);
            reading  = itemView.findViewById(R.id.reading);
            test  = itemView.findViewById(R.id.test);
        }
    }
    }
