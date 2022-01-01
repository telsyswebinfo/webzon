package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class OnlinePaymentActivity extends AppCompatActivity {
    @BindView(R.id.li_bank) LinearLayout li_bank;
    @BindView(R.id.li_upi) LinearLayout li_upi;
    @BindView(R.id.radioGroup) RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_payment);
        ButterKnife.bind(this);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton1) {
                    li_bank.setVisibility(View.GONE);
                    li_upi.setVisibility(View.VISIBLE);
                } else if (checkedId == R.id.radioButton2) {
                    li_bank.setVisibility(View.VISIBLE);
                    li_upi.setVisibility(View.GONE);
                }
            }
        });
    }
}