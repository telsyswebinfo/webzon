package com.webzon.Activity;


import androidx.viewpager.widget.ViewPager;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.fxn.BubbleTabBar;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Adapter.AdapterViewPager;
import com.webzon.Fragment.AccountFragment;
import com.webzon.Fragment.HomeFragment;
import com.webzon.Fragment.ManageFragment;
import com.webzon.Fragment.OrderFragment;
import com.webzon.Fragment.ProductsFragment;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;
import com.webzon.utils.ZoomOutPageTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class HomeActivity2  extends CustomActivity {
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.bubbleTabBar)
    BubbleTabBar bubbleTabBar;
    boolean doubleBackToExitPressedOnce = false;
    SessionManager sessionManager = new SessionManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home2);
        ButterKnife.bind(this);

        AdapterViewPager adapterViewPager = new AdapterViewPager(getSupportFragmentManager());
        adapterViewPager.addFragment(new HomeFragment());
        adapterViewPager.addFragment(new ProductsFragment());
        adapterViewPager.addFragment(new OrderFragment());
        adapterViewPager.addFragment(new ManageFragment());
        adapterViewPager.addFragment(new AccountFragment());
        viewPager.setAdapter(adapterViewPager);
        viewPager.setCurrentItem(0);
        bubbleTabBar.setupBubbleTabBar(viewPager);
        bubbleTabBar.getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(0); //TODO Profile
            }
        });
        bubbleTabBar.getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(1); //TODO Home
            }
        });
        bubbleTabBar.getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(2); //TODO Study Material
            }
        });

        bubbleTabBar.getChildAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(3); //TODO Study Material
            }
        });
        bubbleTabBar.getChildAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewPager.setCurrentItem(4); //TODO Study Material
            }
        });

        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        //bottomdilog();
        bottomdilogbox();
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            Glide.with(this).load(sessionManager.getPreferences(this, "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(AccountFragment.img_bus);
            AccountFragment.txt_name.setText(sessionManager.getPreferences(this, "b_name"));
        }catch (Exception e){e.printStackTrace();}
    }

    private void bottomdilog() {
        TextView txt_continue;
        View view = getLayoutInflater().inflate(R.layout.plan_up, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        txt_continue = view.findViewById(R.id.txt_continue);

        txt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void bottomdilogbox() {

        View view = getLayoutInflater().inflate(R.layout.bottom_items, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

    }
}