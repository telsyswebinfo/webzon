package com.webzon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;


import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;

public class DisCovActivity extends CustomActivity {

    @BindView(R.id.li_location) LinearLayout li_location;
    @BindView(R.id.li_cat) LinearLayout li_cat;
    @BindView(R.id.search) Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dis_cov);
        ButterKnife.bind(this);
        setupActionBar("Discover stores nears you",true);
        li_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             bottomdilog();   
            }
        });

        li_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilog1();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DisCovActivity.this,HomeActivity.class));
            }
        });
    }

    private void bottomdilog() {
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.dilog_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
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

    private void bottomdilog1() {
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.cat_dilog_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
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