package com.webzon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.Order.PendingOrderActivity;

import com.webzon.R;

public class o_AllFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    CardView card_rej,card_pen,card_acc;


    public static o_AllFragment newInstance(String param1, String param2) {
        o_AllFragment fragment = new o_AllFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        view = inflater.inflate(R.layout.fragment_o_order, container, false);
       // firstButton = (Button) view.findViewById(R.id.firstButton);
        card_rej =view.findViewById(R.id.card_rej);
        card_pen =view.findViewById(R.id.card_pen);
        card_acc =view.findViewById(R.id.card_acc);


        if(OrderFragment.tab_pos.equals("Pending")){
            card_rej.setVisibility(View.GONE);
            card_pen.setVisibility(View.VISIBLE);
            card_acc.setVisibility(View.GONE);
        }
        else if(OrderFragment.tab_pos.equals("Accepted")){
            card_rej.setVisibility(View.GONE);
            card_pen.setVisibility(View.GONE);
            card_acc.setVisibility(View.VISIBLE);
        }
        else if(OrderFragment.tab_pos.equals("Rejected")){
            card_rej.setVisibility(View.VISIBLE);
            card_pen.setVisibility(View.GONE);
            card_acc.setVisibility(View.GONE);
        }
        else {
            card_rej.setVisibility(View.VISIBLE);
            card_pen.setVisibility(View.VISIBLE);
            card_acc.setVisibility(View.VISIBLE);
        }

        card_pen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PendingOrderActivity.class));
            }
        });

        return view;
    }

    private void bottomdilog() {
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.share_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(),R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }



}