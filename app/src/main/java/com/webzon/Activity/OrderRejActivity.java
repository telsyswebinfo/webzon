package com.webzon.Activity;

import android.os.Bundle;

import webzon.R;


public class OrderRejActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_rej);
        setupActionBar("Order Rejected",true);
    }
}