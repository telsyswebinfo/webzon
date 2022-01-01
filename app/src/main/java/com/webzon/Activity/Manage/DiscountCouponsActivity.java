package com.webzon.Activity.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.CoupanListAdapter;
import com.webzon.Model.CoupanListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class DiscountCouponsActivity extends CustomActivity {
    @BindView(R.id.btn_create_coupons) Button btn_create_coupons;
    @BindView(R.id.li_layout) LinearLayout li_layout;
    @BindView(R.id.rec_coupan) RecyclerView rec_coupan;
    @BindView(R.id.swipe) SwipeRefreshLayout swipe;
    SessionManager sessionManager = new SessionManager();
    CoupanListModel coupanListModel;
    ArrayList<CoupanListModel> coupanList =new ArrayList<>();
    CoupanListAdapter coupanListAdapter;
    DiscountCouponsActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_coupons);
        ButterKnife.bind(this);
        setupActionBar("Discount Coupons",true);
        btn_create_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DiscountCouponsActivity.this, CreateCouponsActivity.class));
            }
        });

        rec_coupan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_coupan.setHasFixedSize(true);
       // getproductList1();
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getproductList1();
                swipe.setRefreshing(false);
            }
        });
    }

    private void getproductList1(){
        coupanList.clear();
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CoupanList)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(this, "s_id"))
                .addBodyParameter("type","shop")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ", response.toString());
                        Utlity.dismiss_dilog(DiscountCouponsActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                if (!response.getString("data").equals("")) {
                                    li_layout.setVisibility(View.GONE);
                                    rec_coupan.setVisibility(View.VISIBLE);
                                    JSONObject object = new JSONObject(response.getString("data"));
                                    JSONArray jsonArray = object.getJSONArray("data");
                                    // Log.e("Data  ",jsonArray.toString());
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        coupanListModel = new CoupanListModel();
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        coupanListModel.setId(jsonObject.getString("id"));
                                        coupanListModel.setCode(jsonObject.getString("code"));
                                        coupanListModel.setMax_use(jsonObject.getString("max_use"));
                                        coupanListModel.setMax_use_total(jsonObject.getString("max_use_total"));
                                        coupanListModel.setMin_order_amount(jsonObject.getString("min_order_amount"));
                                        coupanListModel.setMin_discount_amount(jsonObject.getString("min_discount_amount"));
                                        coupanListModel.setValue(jsonObject.getString("value"));
                                        coupanListModel.setStatus(jsonObject.getString("status"));
                                        coupanListModel.setType(jsonObject.getString("type"));
                                        coupanList.add(coupanListModel);
                                    }
                                    try {
                                        if (coupanList.size() > 0) {
                                            Log.e("Data ", coupanList.size() + "");
                                            coupanListAdapter = new CoupanListAdapter(coupanList, baseActivity);
                                            rec_coupan.setAdapter(coupanListAdapter);
                                        }
                                    } catch (NullPointerException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else{
                                    li_layout.setVisibility(View.VISIBLE);
                                    rec_coupan.setVisibility(View.GONE);
                                }
                            } else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(DiscountCouponsActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else {
                                SnackBar.returnFlashBar(DiscountCouponsActivity.this, response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(DiscountCouponsActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getproductList1();
    }
}