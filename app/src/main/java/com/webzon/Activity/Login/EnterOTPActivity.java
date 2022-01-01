package com.webzon.Activity.Login;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Activity.OtpEditText;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.halper.StaticVariables;
import com.webzon.otptextview.OTPListener;
import com.webzon.otptextview.OtpTextView;
import com.webzon.smeRetriever.AppSMSBroadcastReceiver;
import com.webzon.smeRetriever.AutoDetectOTP;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;

public class EnterOTPActivity extends CustomActivity {
    @BindView(R.id.btn_submit) Button btn_submit;
    @BindView(R.id.et_otp)
    OtpEditText et_otp;
    @BindView(R.id.txt_number) TextView txt_number;
    @BindView(R.id.txt_timmer) TextView txt_timmer;
    @BindView(R.id.otp_view) OtpTextView otpTextView;
    private IntentFilter intentFilter;
    private AppSMSBroadcastReceiver appSMSBroadcastReceiver;
    SessionManager sessionManager = new SessionManager();


    private String otpnN0;
    AutoDetectOTP autoDetectOTP;

    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(otpnN0!=null&&otpnN0.equals("1234")){
                otpTextView.showSuccess();
            }
            else {
                otpTextView.showError();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_enter_otpactivity);
        ButterKnife.bind(this);

        setupActionBar("Enter OTP",false);
        // bottomdilog();
        // =StaticVariables.Number;
        String number= sessionManager.getPreferences(EnterOTPActivity.this,"m_number");
        String first = number.substring(0, 2);
        String last = number.substring(8, 10);
        txt_number.setText("+91 "+first+"******"+last);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(otpnN0.equals("")){
                 //   Toast.makeText(getApplicationContext(), "Please enter otp", Toast.LENGTH_SHORT).show();
                    et_otp.setError("Please enter otp");
                }
                else{
                    getlogin();
                }

            }
        });
        timmer();
        txt_timmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_timmer.getText().toString().equals("Resend OTP")){
                    getOtp();
                }else{
                    getOtp();
                }
            }
        });

        et_otp.setText(EnterWhatupNoActivity.otp);

        smsListener();
        initBroadCast();

        //****************************
        autoDetectOTP=new AutoDetectOTP(this);

        TextView phoneview=findViewById(R.id.phone_);


        String no=getIntent().getStringExtra("NO");
        if(no!=null){
            phoneview.append(no);
        }
        otpTextView.requestFocusOTP();
        otpTextView.setOtpListener(new OTPListener() {;

            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                otpnN0=otp;
               // Toast.makeText(EnterOTPActivity.this,"The OTP is " + otp, Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable,100);

            }
        });
         autoDetectOTP.startSmsRetriver(new AutoDetectOTP.SmsCallback() {
            @Override
            public void connectionfailed() {
             //   Toast.makeText(EnterOTPActivity.this,"Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionSuccess(Void aVoid) {
              //  Toast.makeText(EnterOTPActivity.this,"Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void smsCallback(String sms) {
                if(sms.contains(":") && sms.contains(".")) {
                    String otp = sms.substring( sms.indexOf(":")+1 , sms.indexOf(".") ).trim();
                    otpTextView.setOTP(otp);
                    Toast.makeText(EnterOTPActivity.this,"The OTP is " + otp, Toast.LENGTH_SHORT).show();
                }
            }
        });
        //****************************

    }


    private void timmer() {
        int timmer =StaticVariables.Timer*1000;
        new CountDownTimer(timmer, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {
                txt_timmer.setText(""+String.format("Resend OTP in "+"%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }
            public void onFinish() {
                txt_timmer.setText("Resend OTP");
            }
        }.start();
    }

    private void bottomdilog() {
        Button btn_submit;
        View view = getLayoutInflater().inflate(R.layout.otp_dilog_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        btn_submit = view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getlogin();
            }
        });
    }



    private void initBroadCast() {
        intentFilter = new IntentFilter("com.google.android.gms.auth.api.phone.SMS_RETRIEVED");
        appSMSBroadcastReceiver = new AppSMSBroadcastReceiver();
        appSMSBroadcastReceiver.setOnSmsReceiveListener(new AppSMSBroadcastReceiver.OnSmsReceiveListener() {
            @Override
            public void onReceive(String code) {
              //  Toast.makeText(EnterOTPActivity.this, code, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void smsListener() {
        SmsRetrieverClient client = SmsRetriever.getClient(this);
        client.startSmsRetriever();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(appSMSBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(appSMSBroadcastReceiver);
    }


    private void getlogin() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(EnterOTPActivity.this,"user_id"));
        hashMap.put("code", otpnN0);
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);

        WebServiceHandler webServiceHandler = new WebServiceHandler(EnterOTPActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        if (jsonObect.getString("products").equals("Yes")) {
                            sessionManager.setPreferences(EnterOTPActivity.this, "p_status", "1");

                            if (jsonObect.getString("business").equals("Yes")) {
                                StaticVariables.Product_Status = "1";
                                sessionManager.setPreferences(EnterOTPActivity.this, "login", "");
                                sessionManager.setPreferences(EnterOTPActivity.this, "loginStatus", "1");
                                startActivity(new Intent(EnterOTPActivity.this, HomeActivity2.class));
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                finish();
                            } else {

                                startActivity(new Intent(EnterOTPActivity.this, EnterBNameActivity.class));
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                finish();
                            }
                        } else {
                            sessionManager.setPreferences(EnterOTPActivity.this, "p_status", "0");
                            if (jsonObect.getString("business").equals("Yes")) {
                                StaticVariables.Product_Status = "1";
                                sessionManager.setPreferences(EnterOTPActivity.this, "login", "");
                                sessionManager.setPreferences(EnterOTPActivity.this, "loginStatus", "1");
                                startActivity(new Intent(EnterOTPActivity.this, HomeActivity2.class));
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                finish();
                            } else {
                                StaticVariables.Product_Status = "0";
                                startActivity(new Intent(EnterOTPActivity.this, EnterBNameActivity.class));
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                                finish();

                            }
                        }
                    }else{
                        SnackBar.returnFlashBar(EnterOTPActivity.this,jsonObect.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.verifyUser);
    }

    private void getOtp() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(EnterOTPActivity.this,"user_id"));
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);

        WebServiceHandler webServiceHandler = new WebServiceHandler(EnterOTPActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        SnackBar.returnFlashBar(EnterOTPActivity.this,jsonObect.getString("message"));
                        StaticVariables.Timer = jsonObect.getInt("timer");
                        timmer();

                    }else{
                        SnackBar.returnFlashBar(EnterOTPActivity.this,jsonObect.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.resendOTP);
    }


    @Override
    protected void onStop() {
        super.onStop();
        autoDetectOTP.stopSmsReciever();
    }


}