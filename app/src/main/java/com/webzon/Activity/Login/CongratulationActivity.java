package com.webzon.Activity.Login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.R;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.SessionManager;


public class CongratulationActivity extends CustomActivity {
    SessionManager sessionManager = new SessionManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                StaticVariables.Status="0";
                // This method will be executed once the timer is over
                sessionManager.setPreferences(CongratulationActivity.this,"loginStatus","1");
              //  Intent i = new Intent(CongratulationActivity.this, HomeActivity2.class);
                Intent i = new Intent(CongratulationActivity.this, HomeActivity2.class);
                startActivity(i);
                finish();
            }
        }, 4500);
    }
}