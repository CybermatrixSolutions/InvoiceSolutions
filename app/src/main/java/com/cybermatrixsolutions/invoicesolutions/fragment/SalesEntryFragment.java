package com.cybermatrixsolutions.invoicesolutions.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;
import com.cybermatrixsolutions.invoicesolutions.activity.CreditCustomerActivity;
import com.cybermatrixsolutions.invoicesolutions.activity.WalkinCustomerInformationActivity;

/**
 * Created by Diwakar on 10/6/2017.
 */

public class SalesEntryFragment extends Fragment {

    private TextView creditCustomer;
    private TextView walkInCustomer;

    public static SalesEntryFragment newInstance() {
        SalesEntryFragment fragment = new SalesEntryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sales_entry, container, false);
        creditCustomer=(TextView)view.findViewById(R.id.creditCustomer);
        walkInCustomer=(TextView)view.findViewById(R.id.walkinCustomer);

        creditCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CreditCustomerActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });

        walkInCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalkinCustomerInformationActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            }
        });

        return view;
    }
}
