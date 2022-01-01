package com.webzon.Activity.Account;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.browser.customtabs.CustomTabsIntent;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.Login.EnterWhatupNoActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class AdditionalInformationActivity extends CustomActivity {

    @BindView(R.id.li_share_app)LinearLayout li_share_app;
    @BindView(R.id.li_rate_us)LinearLayout li_rate_us;
    @BindView(R.id.li_pp)LinearLayout li_pp;
    @BindView(R.id.li_sign_out)LinearLayout li_sign_out;
    @BindView(R.id.btn_sw) Switch btn_sw;
    SessionManager sessionManager = new SessionManager();
    String currentDateandTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_additional_information);
        ButterKnife.bind(this);

        setupActionBar("Additional Information",true);
        li_share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdditionalInformationActivity.this, ShareAppActivity.class));
            }
        });

        if(sessionManager.getPreferences(AdditionalInformationActivity.this,"whatappchat").equals("Yes")){
            btn_sw.setChecked(true);
        }else{
            btn_sw.setChecked(false);
        }
        btn_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getshopNotificationsUpdate("Yes");
                } else {
                    getshopNotificationsUpdate("No");
                }
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        li_rate_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateApp();
            }
        });

        li_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariables.WebPageType =ApiUrl.privacy_policy;
                String url=ApiUrl.BaseUrl+ApiUrl.privacy_policy;
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                openCustomTab(AdditionalInformationActivity.this, customIntent.build(), Uri.parse(url));
               // startActivity(new Intent(AdditionalInformationActivity.this,WebViewActivity.class));
            }
        });

        li_sign_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilog();
            }
        });
    }

    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }


    private void bottomdilog() {
        ImageView close;
        LinearLayout li_rej;
        View view = getLayoutInflater().inflate(R.layout.signout_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close = view.findViewById(R.id.close);
        li_rej = view.findViewById(R.id.li_rej);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                getLogout();
            }
        });
    }

    private void getLogout(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.Logout)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(AdditionalInformationActivity.this,"user_id"))


                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utlity.dismiss_dilog(AdditionalInformationActivity.this);
                        try {

                             if(response.getString("status").equals("200")){
                                 SnackBar.returnFlashBar(AdditionalInformationActivity.this, response.getString("message"));
                                 sessionManager.setPreferences(AdditionalInformationActivity.this,"user_id","");
                                 sessionManager.setPreferences(AdditionalInformationActivity.this,"loginStatus","");
                                 sessionManager.setPreferences(AdditionalInformationActivity.this,"p_status","");
                                 Intent intent = new Intent(AdditionalInformationActivity.this, EnterWhatupNoActivity.class);
                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 startActivity(intent);
                                 overridePendingTransition(R.anim.left_in, R.anim.left_out);
                             }
                             else{
                                 SnackBar.returnFlashBar(AdditionalInformationActivity.this, response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(AdditionalInformationActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    /*private void getLogout(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.Logout)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(AdditionalInformationActivity.this,"user_id"))


                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Utlity.dismiss_dilog(AdditionalInformationActivity.this);
                        try {

                            if(response.getString("status").equals("200")){
                                SnackBar.returnFlashBar(AdditionalInformationActivity.this, response.getString("message"));
                                sessionManager.setPreferences(AdditionalInformationActivity.this,"loginStatus","");
                                Intent intent = new Intent(AdditionalInformationActivity.this, EnterWhatupNoActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
                            }
                            else{
                                SnackBar.returnFlashBar(AdditionalInformationActivity.this, response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }*/

                    /*.addBodyParameter("online",sessionManager.getPreferences(this,"online"))
            .addBodyParameter("lock_screen",sessionManager.getPreferences(this,"lockscreen"))
            .addBodyParameter("whatsapp_chat",sessionManager.getPreferences(this,"whatappchat"))*/

    private void getshopNotificationsUpdate(String status){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.shopNotificationsUpdate)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(this,"s_id"))
                .addBodyParameter("online",sessionManager.getPreferences(AdditionalInformationActivity.this,"online"))
                .addBodyParameter("lock_screen",sessionManager.getPreferences(AdditionalInformationActivity.this,"lock_screen"))
                .addBodyParameter("whatsapp_chat",status)
                .addBodyParameter("shop_open_time",currentDateandTime)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(AdditionalInformationActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                sessionManager.setPreferences(AdditionalInformationActivity.this,"whatappchat",response.getJSONObject("data").getJSONObject("business").getString("whatsapp_chat"));
                                sessionManager.setPreferences(AdditionalInformationActivity.this,"lock_screen",response.getJSONObject("data").getJSONObject("business").getString("lock_screen"));
                                sessionManager.setPreferences(AdditionalInformationActivity.this,"online",response.getJSONObject("data").getJSONObject("business").getString("online"));
                            }else{
                                SnackBar.returnFlashBar(AdditionalInformationActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(AdditionalInformationActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }
    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

}