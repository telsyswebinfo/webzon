package com.webzon.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.webzon.Activity.Login.EnterWhatupNoActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.intro.Intro;
import com.webzon.utils.SessionManager;

import webzon.R;

public class SplashActivity extends CustomActivity {
    SessionManager sessionManager = new SessionManager();
    private SharedPreferences spf;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        spf = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("token failed", task.getException());
                            token();
                            return;
                        }
                        // Get new FCM registration token
                        token = task.getResult();
                        StaticVariables.Token= token;
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Token", msg);
                        // Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


        try{
            String data = getIntent().getExtras().getString("log","defaultKey");
            Log.e("LogoutStatus",data+"Hello");

            if(data.equals("logout")){
                sessionManager.setPreferences(SplashActivity.this,"logout","");
                sessionManager.setPreferences(SplashActivity.this,"user_id","");
                sessionManager.setPreferences(SplashActivity.this,"loginStatus","");
                sessionManager.setPreferences(SplashActivity.this,"p_status","");
                Log.e("LogoutStatus","Hello1");
            }else{
                Log.e("LogoutStatus","Hello2");
            }
        }catch (Exception e){e.printStackTrace();}

        StaticVariables.DeviceID = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.e("Build.ID",StaticVariables.DeviceID+"");

       /* AppSignatureHelper appSignatureHelper = new AppSignatureHelper(SplashActivity.this);
        Log.e("Hkey", appSignatureHelper.getAppSignatures().get(0));*/


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                if (spf.getBoolean("intro", false)) {
                    if(sessionManager.getPreferences(SplashActivity.this, "loginStatus").equals("1")){
                        startActivity(new Intent(SplashActivity.this,HomeActivity2.class));
                        //  startActivity(new Intent(SplashActivity.this,HomeActivity2.class));
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        finish();
                    }
                    else{
                        startActivity(new Intent(SplashActivity.this, EnterWhatupNoActivity.class));
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        finish();
                    }
                } else {
                    startActivity(new Intent(SplashActivity.this, Intro.class));
                    overridePendingTransition(R.anim.left_in, R.anim.left_out);
                    finish();
                }
            }
        }, 5000);
    }

    private void token() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w("token failed", task.getException());
                            token();
                            return;
                        }
                        // Get new FCM registration token
                        String token = task.getResult();
                        StaticVariables.Token= token;
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d("Token", msg);
                        // Toast.makeText(SplashActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


}