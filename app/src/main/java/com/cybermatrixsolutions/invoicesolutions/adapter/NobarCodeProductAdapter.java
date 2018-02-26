package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.ProductModel;

import java.util.List;
/**
 * Created by Diwakar on 9/14/2017.
 */

public class NobarCodeProductAdapter extends RecyclerView.Adapter<NobarCodeProductAdapter.SpinnerViewHolder> {
    List<ProductModel> productDetail;
    Context context;
    public static MyClickListerer myClickListerer;
    public NobarCodeProductAdapter(List<ProductModel> productDetail, Context context) {
        this.productDetail = productDetail;
        this.context = context;
    }

    @Override
    public SpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout2, parent, false);
        return  new SpinnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpinnerViewHolder holder,final int position) {
            holder.medicine.setText(productDetail.get(position).getProductName());
           int SNO=position+1;
             holder.sno.setText(String.valueOf(SNO));
             holder.price.setText(productDetail.get(position).getPrice());
             holder.qty.setText(productDetail.get(position).getQty());
         holder.remove.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 removeAt(position);
             }
         });
    }
    public void removeAt(int position) {
        productDetail.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, productDetail.size());
    }
    @Override
    public int getItemCount() {
        return productDetail.size();
    }
    public interface MyClickListerer {
        void onItemClick(int position, View view);
    }
    public class SpinnerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView medicine;
        TextView price,sno;
        EditText qty;
        ImageView remove;

        public SpinnerViewHolder(View itemView) {
            super(itemView);
            medicine= itemView.findViewById(R.id.medicine);
            price = itemView.findViewById(R.id.days);
            qty = itemView.findViewById(R.id.times);
            sno= itemView.findViewById(R.id.sno);
            remove= itemView.findViewById(R.id.delete);
          //  itemView.setOnClickListener(this);
            remove.setOnClickListener(this);

        }
      @Override
        public void onClick(View view) {
        }
    }



    }
