package com.cybermatrixsolutions.invoicesolutions.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ScrollingView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cybermatrixsolutions.invoicesolutions.R;

/**
 * Created by Diwakar on 10/6/2017.
 */

public class SalesSummaryFragment extends Fragment {


    Button sendReading,sendDetail;


    int Credit=0,Cash=0,Credit1=0,PetroCash=0;

    private LinearLayout scrollingView;

    private TextView difference;

    private TextView creditAmonut;
    private EditText cashAmonut;
    private EditText petroAmonut;
    int sum=0;
    int total=888;
    private LinearLayout linearLayout;
    public static SalesSummaryFragment newInstance() {
        SalesSummaryFragment fragment = new SalesSummaryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_sales_summary, container, false);

        creditAmonut=view.findViewById(R.id.creditAmount);
        cashAmonut=view.findViewById(R.id.cashAmount);
        petroAmonut=view.findViewById(R.id.petroAmount);

        difference=view.findViewById(R.id.difference);

        scrollingView=view.findViewById(R.id.enterDetailLayout);

        linearLayout=view.findViewById(R.id.readingLayout);
        creditAmonut.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                        String credit=s.toString().trim();
                if(!credit.isEmpty())
                        Credit=Integer.parseInt(credit);
                       sum=Credit+Credit1+Cash+PetroCash;
                      difference.setText(String.valueOf(total-sum));
                       Log.e("Credit",credit);
                       Log.e("Sum",String.valueOf(sum));
             }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

             }

            @Override
            public void afterTextChanged(Editable s) {

             }
        });
         cashAmonut.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String cash=s.toString().trim();
                if(!cash.isEmpty())
                    Cash=Integer.parseInt(cash);
                sum=Credit+Credit1+Cash+PetroCash;
                difference.setText(String.valueOf(total-sum));
                Log.e("Cash",cash);
                Log.e("Sum",String.valueOf(sum));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        petroAmonut.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String petroCash=s.toString().trim();
                if(!petroCash.isEmpty())
                    PetroCash=Integer.parseInt(petroCash);
                sum=Credit+Credit1+Cash+PetroCash;
                difference.setText(String.valueOf(total-sum));
                Log.e("PetroCash",petroCash);
                Log.e("Sum",String.valueOf(total-sum));
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        sendReading=view.findViewById(R.id.sendReading);
        sendDetail=(Button)view.findViewById(R.id.sendDetail);
        sendReading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scrollingView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
