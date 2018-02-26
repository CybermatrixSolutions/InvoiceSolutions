package com.cybermatrixsolutions.invoicesolutions.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.LubeList;
import com.cybermatrixsolutions.invoicesolutions.model.ProductModel;

import java.util.List;

/**
 * Created by Diwakar on 9/14/2017.
 */

public class ProductNobarCodeAdapter extends RecyclerView.Adapter<ProductNobarCodeAdapter.SpinnerViewHolder> {
    List<LubeList> productDetail;
    Context context;
    public ProductNobarCodeAdapter(List<LubeList> productDetail, Context context) {
        this.productDetail = productDetail;
        this.context = context;
    }

    @Override
    public SpinnerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout, parent, false);
        return  new SpinnerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpinnerViewHolder holder,final int position) {
            holder.medicine.setText(productDetail.get(position).getItem_Name());
           // double i=Double.parseDouble(productDetail.get(position).getPrice());

            holder.price.setText("Rs. "+productDetail.get(position).getPrice());
            int SNO=position+1;
            holder.sno.setText(String.valueOf(SNO));

    }
    @Override
    public int getItemCount() {
        return productDetail.size();
    }

    public class SpinnerViewHolder extends RecyclerView.ViewHolder {
        TextView medicine;
        TextView price, sno;
        //ImageView remove;

        public SpinnerViewHolder(View itemView) {
            super(itemView);
            medicine = itemView.findViewById(R.id.medicine);
            price = itemView.findViewById(R.id.days);
            sno= itemView.findViewById(R.id.sno);
        }

    }

 }
