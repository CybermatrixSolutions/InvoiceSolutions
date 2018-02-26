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
import com.cybermatrixsolutions.invoicesolutions.activity.shift_sattlement.ShiftSettlementActivity;
import com.cybermatrixsolutions.invoicesolutions.model.Payment_modeModel;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Diwakar on 1/11/2018.
 */

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    Context context;
    private List<Payment_modeModel> arraylist;

    public PaymentAdapter(Context context, List<Payment_modeModel> arraylist) {
        this.context = context;
        this.arraylist = arraylist;
    }

    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_layout, parent, false);
        return  new PaymentAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final PaymentAdapter.ViewHolder holder, final int position) {
        final Payment_modeModel modeModel=arraylist.get(position);
        modeModel.setPrice("");
        holder.price.setText(modeModel.getPrice());
        holder.text.setText(modeModel.getRo_code());


        holder.price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String ss=holder.price.getText().toString();
                if(ss.equals("")){
                    ss="0";
                }
                modeModel.setPrice(""+Double.parseDouble(ss));
                    double sum=0;
                    for (int i=0;i<arraylist.size();i++){
                        if(!arraylist.get(i).getPrice().equals("")){
                            sum=sum+(Double.parseDouble(arraylist.get(i).getPrice()));}
                    }
                    ;
                  Double diffrence=sum-ShiftSettlementActivity.total;
                  DecimalFormat df= new DecimalFormat("###############00");


                      ShiftSettlementActivity.differenceAmount.setText(df.format(diffrence));









            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView text;
        EditText price;

        public ViewHolder(View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            price=(EditText)itemView.findViewById(R.id.price);
        }
    }

}