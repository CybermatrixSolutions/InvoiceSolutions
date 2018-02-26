package com.cybermatrixsolutions.invoicesolutions.customer_module.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.customer_module.activity.AddLubeRequestActivity;

import java.util.ArrayList;

/**
 * Created by Diwakar on 2/7/2018.
 */

public class Lube_ItemAdd_Adapter extends BaseAdapter {
    public Lube_ItemAdd_Adapter(ArrayList<String> arrayList, Context context,ArrayList<String> arrayListqty) {
        this.arrayList = arrayList;
        this.arrayListqty = arrayListqty;


        this.context = context;
    }
    ArrayList<String>arrayList;
    ArrayList<String>arrayListqty;

    Context context;
    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.lube_item_add_layout,null,false);
        TextView item_name=(TextView)view.findViewById(R.id.item_name);
        LinearLayout linear_header=(LinearLayout)view.findViewById(R.id.linear_header);
        ImageView delete_item=(ImageView)view.findViewById(R.id.delete_item);

        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList.remove(position);
                arrayListqty.remove(position);
                Lube_ItemAdd_Adapter.this.notifyDataSetChanged();
                if(arrayList.size()==0){
                    AddLubeRequestActivity.submit.setVisibility(View.GONE);
                }else {
                    AddLubeRequestActivity.submit.setVisibility(View.VISIBLE);

                }

            }
        });
        if(position==0){
            linear_header.setVisibility(View.VISIBLE);
        }else {
            linear_header.setVisibility(View.GONE);
        }
        TextView qty=(TextView)view.findViewById(R.id.qty);
        item_name.setText(arrayList.get(position));
        qty.setText(arrayListqty.get(position));
        return view;
    }
}
