package com.webzon.Activity.Manage;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.material.textfield.TextInputLayout;
import com.webzon.Activity.CustomActivity;

import butterknife.BindView;
import com.webzon.R;

public class DukanDeliveryActivity extends CustomActivity {

    TextInputLayout userIDTextInputLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dukan_delivery);

        userIDTextInputLayout = findViewById(R.id.userIDTextInputLayout);
      //  userIDTextInputLayout.setError("You need to enter a name");


    }
}