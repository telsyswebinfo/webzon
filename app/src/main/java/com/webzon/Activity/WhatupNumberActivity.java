package com.webzon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.Login.CongratulationActivity;
import com.webzon.utils.SessionManager;
import java.util.concurrent.TimeUnit;
import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class WhatupNumberActivity extends CustomActivity {
    @BindView(R.id.btn_get_otp) Button btn_get_otp;
    @BindView(R.id.btn_verify) Button btn_verify;
     @BindView(R.id.li_otp) LinearLayout li_otp;
     @BindView(R.id.txt_w_number) TextInputEditText txt_w_number;
     @BindView(R.id.txt_w_OTP) TextInputEditText txt_w_OTP;
     @BindView(R.id.txt_timmer) TextView txt_timmer;
    SessionManager sessionManager = new SessionManager();
     String otp,number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whatup_number);
        ButterKnife.bind(this);
        setupActionBar("Whatup Number",true);
        btn_get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                li_otp.setVisibility(View.VISIBLE);
                btn_get_otp.setVisibility(View.GONE);
                btn_verify.setVisibility(View.VISIBLE);
            }
        });

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_w_OTP.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Please enter otp", Toast.LENGTH_SHORT).show();
                }
                else if(!otp.equals(txt_w_OTP.getText().toString())){
                    Toast.makeText(getApplicationContext(), "Please enter valid  otp", Toast.LENGTH_SHORT).show();
                }
                else{
                    sessionManager.setPreferences(WhatupNumberActivity.this,"loginStatus","1");
                    startActivity(new Intent(WhatupNumberActivity.this, CongratulationActivity.class));
                }
              //  startActivity(new Intent(WhatupNumberActivity.this,CongratulationActivity.class));
            }
        });

        txt_timmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_timmer.getText().toString().equals("Resend OTP")){
                    timmer();

                }
            }
        });
    }



    private void timmer() {
        new CountDownTimer(60000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                txt_timmer.setText("Resend OTP in "+String.format("%d seconds",
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                txt_timmer.setText("Resend OTP");
            }
        }.start();
    }
}