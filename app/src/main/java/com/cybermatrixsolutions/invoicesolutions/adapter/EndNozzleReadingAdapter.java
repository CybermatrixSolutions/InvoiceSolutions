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
import com.cybermatrixsolutions.invoicesolutions.model.NozzlListModel;

import java.util.List;

/**
 * Created by Diwakar on 9/14/2017.
 */

public class EndNozzleReadingAdapter extends RecyclerView.Adapter<EndNozzleReadingAdapter.SpinnerViewHolder> {
    List<NozzlListModel> readingModelList;
   // List<ProductDetail> arraylist;
    Context context;
    private OnEditTextChanged onEditTextChanged;
    double endsreading=0;

    //public static MyClickListerer myClickListerer;
    public EndNozzleReadingAdapter(List<NozzlListModel> productDetail, Context context) {
        this.readingModelList = productDetail;
        this.context = context;
        this.onEditTextChanged = onEditTextChanged;

    }
    public interface OnEditTextChanged {
        void onTextChanged(int position, String charSeq);
    }

    @Override
    public SpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.nozzle_end_reading_layout, parent, false);
        return  new SpinnerViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(final SpinnerViewHolder holder, final int position) {
            holder.NozzleNumber.setText(readingModelList.get(position).getNozzle_No());
           // holder.StartReading.setText(readingModelList.get(position).getStarReading());
            int SNO=position+1;
            holder.sno.setText(String.valueOf(SNO));
            holder.StartReading.setText(readingModelList.get(position).getNozzle_Start());
            holder.test.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(holder.EndReading.getText().toString().length()==0){

                        endsreading=0;
                    }else {
                        endsreading=Double.parseDouble(holder.EndReading.getText().toString());

                        if(holder.test.getText().toString().length()>0){
                            Double totalread=endsreading-Double.parseDouble(holder.StartReading.getText().toString())-Double.parseDouble(holder.test.getText().toString());
                            holder.reading.setText(""+totalread);
                        }else {
                            holder.reading.setText("0");
                        }
                    }



                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        holder.EndReading.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(holder.test.getText().toString().length()>0){
                    if(holder.EndReading.getText().toString().length()>0){
                        endsreading=Double.parseDouble(holder.EndReading.getText().toString());
                    }else {

                        endsreading=0;
                    }

                    Double totalread=endsreading-Double.parseDouble(holder.StartReading.getText().toString())-Double.parseDouble(holder.test.getText().toString());
                    holder.reading.setText(""+totalread);
                }



            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    public int getItemCount() {
        return readingModelList.size();
    }
    public class SpinnerViewHolder extends RecyclerView.ViewHolder{
        TextView NozzleNumber,StartReading,sno,reading;
        EditText EndReading;
        EditText test;
        public SpinnerViewHolder(View itemView) {
            super(itemView);
            NozzleNumber = itemView.findViewById(R.id.nozzleNumber);
            StartReading = itemView.findViewById(R.id.startreading);
            EndReading = itemView.findViewById(R.id.endReading);
             test=itemView.findViewById(R.id.test);
             sno= itemView.findViewById(R.id.sno);
            reading= itemView.findViewById(R.id.reading);
        }





    }



}
