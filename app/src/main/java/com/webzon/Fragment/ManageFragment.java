package com.webzon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.webzon.Activity.Manage.DiscountCouponsActivity;
import com.webzon.Activity.Manage.DukaanMarketingActivity;
import com.webzon.Activity.Manage.DukanDeliveryActivity;
import com.webzon.Activity.Manage.ExtraChargesActivity;
import com.webzon.Activity.Manage.GSTTaxesActivity;
import com.webzon.Activity.Manage.MarketingsActivity;
import com.webzon.Activity.Manage.MyCustomersActivity;
import com.webzon.Activity.Manage.OnlinePaymentActivity;
import com.webzon.Activity.Manage.OrderFormActivity;
import com.webzon.Activity.Manage.QRCodeActivity;

import com.webzon.Activity.Manage.TaxesActivity;
import com.webzon.R;


public class ManageFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout li_marketings,li_ds,li_qr_code,li_extra_charges,li_mc,li_dukan_delivery,li_du_maarketing,li_onlinepayment,li_orderform,li_taxes,li_gst;

    public static ManageFragment newInstance(String param1, String param2) {
        ManageFragment fragment = new ManageFragment();
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
        view = inflater.inflate(R.layout.fragment_manage, container, false);
       // firstButton = (Button) view.findViewById(R.id.firstButton);
        li_marketings =view.findViewById(R.id.li_marketings);
        li_ds =view.findViewById(R.id.li_ds);
        li_qr_code =view.findViewById(R.id.li_qr_code);
        li_extra_charges =view.findViewById(R.id.li_extra_charges);
        li_mc =view.findViewById(R.id.li_mc);
        li_dukan_delivery =view.findViewById(R.id.li_dukan_delivery);
        li_du_maarketing =view.findViewById(R.id.li_du_maarketing);
        li_onlinepayment =view.findViewById(R.id.li_onlinepayment);
        li_taxes =view.findViewById(R.id.li_taxes);
        li_gst =view.findViewById(R.id.li_gst);
        li_orderform =view.findViewById(R.id.li_orderform);
        li_marketings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MarketingsActivity.class));
            }
        });

        li_ds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DiscountCouponsActivity.class));
            }
        });

        li_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QRCodeActivity.class));
            }
        });

        li_extra_charges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExtraChargesActivity.class));
            }
        });

        li_mc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MyCustomersActivity.class));
            }
        });
        li_dukan_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DukanDeliveryActivity.class));
            }
        });

        li_du_maarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), DukaanMarketingActivity.class));
            }
        });

        li_onlinepayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OnlinePaymentActivity.class));
            }
        });

        li_orderform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), OrderFormActivity.class));
            }
        });

        li_taxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TaxesActivity.class));
            }
        });

        li_gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GSTTaxesActivity.class));
            }
        });

        return view;
    }
}

