package com.cybermatrixsolutions.invoicesolutions.activity.With_QR;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.cybermatrixsolutions.invoicesolutions.R;

import java.util.ArrayList;

/**
 * Created by Diwakar on 1/15/2018.
 */

public class LubeItemAdapter extends BaseAdapter {
    Context context;
    ArrayList<String>arrayList;

    public LubeItemAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= LayoutInflater.from(context).inflate(R.layout.item,null,false);

        return null;
    }
}
