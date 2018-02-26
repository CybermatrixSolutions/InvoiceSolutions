package com.cybermatrixsolutions.invoicesolutions.adapter;

/**
 * Created by Ravi on 29/07/15.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.model.NavDrawerItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.MyViewHolder> {

    //private SparseBooleanArray selectedItems;
    int selected_position = 0;

    SharedPreferences mPreferences;
    String mUserID;
    List<NavDrawerItem> data = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;
    List<NavDrawerItem> data1 = new ArrayList<>();

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.imageId.setImageResource(current.getImageId());
    }

/*
    private void setAnimation(View viewToAnimate, int position) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);

        animation.setDuration(position * 50 + 600);
        viewToAnimate.startAnimation(animation);

    }
*/

    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, count;
        ImageView imageId;
        //LinearLayout myBackground;

        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            count = itemView.findViewById(R.id.count);
            imageId = itemView.findViewById(R.id.imageView);
            //myBackground = (LinearLayout)itemView.findViewById(R.id.layout_background);
            //myBackground = (LinearLayout) itemView;
        }


    }

    public void hideView(int pos) {
        data.remove(pos);
        notifyDataSetChanged();
    }

    public void showView(int pos) {
        data.add(pos, data1.get(pos));
        notifyDataSetChanged();
    }

/*
    private void callorderapi(String apikey, String userid) {
        ApiInterface apiInterface = Apiclient1.getClient().create(ApiInterface.class);
        Call<OrderResponse> call = apiInterface.getOrderDetail(apikey, userid);
        call.enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, retrofit2.Response<OrderResponse> response) {
                OrderResponse orderResponse=response.body();
                String error=orderResponse.getError();
                if(error.equalsIgnoreCase("false")) {
                    try {
                        orderDetailsInfoList = orderResponse.getOrderLists();
                        if (orderDetailsInfoList.size() >= 0) {
                            orderCount = orderDetailsInfoList.size();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {

            }
        });
    }
*/
}
