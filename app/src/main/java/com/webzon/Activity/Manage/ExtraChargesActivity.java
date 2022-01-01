package com.webzon.Activity.Manage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Switch;

import androidx.cardview.widget.CardView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;

public class ExtraChargesActivity extends CustomActivity {

    @BindView(R.id.li_cr_ext) LinearLayout li_cr_ext;
    @BindView(R.id.li_og) LinearLayout li_og;
    @BindView(R.id.sw_gst) Switch sw_gst;
    @BindView(R.id.btn_save_change) Button btn_save_change;
    @BindView(R.id.card_charge) CardView card_charge;
    SessionManager sessionManager = new SessionManager();
    String type ="percent";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_charges);
        ButterKnife.bind(this);
        setupActionBar("Extra Charges",true);

        li_cr_ext.setVisibility(View.VISIBLE);
        li_og.setVisibility(View.GONE);
       /* sw_gst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==false){
                    li_cr_ext.setVisibility(View.GONE);
                    li_og.setVisibility(View.VISIBLE);
                }else{
                    li_cr_ext.setVisibility(View.VISIBLE);
                    li_og.setVisibility(View.GONE);
                }
            }

        });*/
        card_charge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*li_cr_ext.setVisibility(View.GONE);
                li_og.setVisibility(View.VISIBLE);*/
                bottomdilog();
            }
        });

        shippingList();
    }

    private void bottomdilog() {

        ImageView close;
        Button btn_create;
        TextInputEditText txt_charegename,txt_charge;
        RadioGroup radioGroup1;
        View view = getLayoutInflater().inflate(R.layout.cat_dilog_extra_charge, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        txt_charge =view.findViewById(R.id.txt_charge);
        txt_charegename =view.findViewById(R.id.txt_charegename);
        radioGroup1 =view.findViewById(R.id.radioGroup1);
        close =view.findViewById(R.id.close);
        btn_create =view.findViewById(R.id.btn_create);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* li_cr_ext.setVisibility(View.GONE);
                li_og.setVisibility(View.VISIBLE);
                dialog.cancel();*/
                if(txt_charegename.getText().toString().equals("")){
                    txt_charegename.setError("Please enter name");
                }
                if(txt_charge.getText().toString().equals("")){
                    txt_charegename.setError("Plese enter charge");
                }
                else{
                    CreateExtraCharge(txt_charegename.getText().toString(),txt_charge.getText().toString());
                    dialog.cancel();
                }

            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton1) {
                    type ="percent";
                  //  et_per.setHint("Percent");
                } else if (checkedId == R.id.radioButton2) {
                   // dis_type="fixed";
                    type ="percent";
                }

            }
        });
    }

    private void CreateExtraCharge1(String title, String value){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CreateExtraCharge)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(this,"s_id"))
                .addBodyParameter("type",type)
                .addBodyParameter("title",title)
                .addBodyParameter("value",value)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(ExtraChargesActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                String title,slug,description,price,discount,cat_id;
                                JSONObject object =new JSONObject(response.getString("product"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ",jsonArray.toString());
                                SnackBar.returnFlashBar(ExtraChargesActivity.this,response.getString("message"));

                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(ExtraChargesActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(ExtraChargesActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(ExtraChargesActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void CreateExtraCharge(String title, String value) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("shop_id", sessionManager.getPreferences(this,"s_id"));
        hashMap.put("type", type);
        hashMap.put("title", title);
        hashMap.put("value", value);

        WebServiceHandler webServiceHandler = new WebServiceHandler(ExtraChargesActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        String title,slug,description,price,discount,cat_id;
                        JSONObject object =new JSONObject(jsonObect.getString("product"));
                        JSONArray jsonArray = object.getJSONArray("data");
                        Log.e("Data  ",jsonArray.toString());
                        SnackBar.returnFlashBar(ExtraChargesActivity.this,jsonObect.getString("message"));

                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(ExtraChargesActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        SnackBar.returnFlashBar(ExtraChargesActivity.this,jsonObect.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.CreateExtraCharge);
    }

    private void shippingList() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("shop_id", sessionManager.getPreferences(this,"s_id"));
        hashMap.put("type", type);


        WebServiceHandler webServiceHandler = new WebServiceHandler(ExtraChargesActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                  /*  if (jsonObect.getString("status").equals("200")) {
                        String title,slug,description,price,discount,cat_id;
                        JSONObject object =new JSONObject(jsonObect.getString("product"));
                        JSONArray jsonArray = object.getJSONArray("data");
                        Log.e("Data  ",jsonArray.toString());
                        SnackBar.returnFlashBar(ExtraChargesActivity.this,jsonObect.getString("message"));

                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(ExtraChargesActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        SnackBar.returnFlashBar(ExtraChargesActivity.this,jsonObect.getString("message"));
                    }*/
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.shippingList);
    }
}