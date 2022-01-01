package com.webzon.Activity.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.CustomerListAdapter;
import com.webzon.Model.CustomerListModel;
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
import com.webzon.R;


public class MyCustomersActivity extends CustomActivity {
    SessionManager sessionManager = new SessionManager();
    private ArrayList<String> orderType=new ArrayList<>();
    @BindView(R.id.cardview_all) CardView cardview_all;
    @BindView(R.id.cardview_new) CardView cardview_new;
    @BindView(R.id.cardview_re) CardView cardview_re;
    @BindView(R.id.cardview_im) CardView cardview_im;
    @BindView(R.id.txt_all) TextView txt_all;
    @BindView(R.id.txt_new) TextView txt_new;
    @BindView(R.id.txt_re) TextView txt_re;
    @BindView(R.id.txt_imp) TextView txt_imp;
    @BindView(R.id.rec_c_list) RecyclerView rec_c_list;
    @BindView(R.id.swipe) SwipeRefreshLayout swipe;
    CustomerListModel customerListModel;
    ArrayList<CustomerListModel> c_list =new ArrayList<>();
    CustomerListAdapter customerListAdapter;
    MyCustomersActivity baseActivity;
    String type="all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_customers);
        ButterKnife.bind(this);
        setupActionBar("My Customers",true);

        rec_c_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_c_list.setHasFixedSize(true);
        getCustomerList("all");
        cardview_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_all.setCardBackgroundColor(getResources().getColor(R.color.color_view_bg));
                cardview_new.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_re.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_im.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                getCustomerList("all");
                type ="all";
            }
        });
        cardview_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_all.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_new.setCardBackgroundColor(getResources().getColor(R.color.color_view_bg));
                cardview_re.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_im.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                getCustomerList("new");
                type ="new";
            }
        });
        cardview_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_all.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_new.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_re.setCardBackgroundColor(getResources().getColor(R.color.color_view_bg));
                cardview_im.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                getCustomerList("returning");
                type ="returning";
            }
        });
        cardview_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardview_all.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_new.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_re.setCardBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cardview_im.setCardBackgroundColor(getResources().getColor(R.color.color_view_bg));
                getCustomerList("imported");
                type ="imported";
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getCustomerList(type);
                swipe.setRefreshing(false);
            }
        });

    }
    public void getCustomerList(String type){
        c_list.clear();
        Utlity.dismiss_dilog(MyCustomersActivity.this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CustomerList)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(this,"s_id"))
                .addBodyParameter("type","shop")
                .addBodyParameter("customer_type",type)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Utlity.dismiss_dilog(MyCustomersActivity.this);
                            Log.e("Res  ", response + "");
                            if (response.getString("status").equals("200")) {
                                txt_all.setText("All("+response.getString("all")+")");
                                txt_new.setText("New("+response.getString("new")+")");
                                txt_re.setText("Returning("+response.getString("returning")+")");
                                txt_imp.setText("Imported("+response.getString("imported")+")");
                                JSONObject object = new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    customerListModel = new CustomerListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    customerListModel.setId(jsonObject.getString("id"));
                                    customerListModel.setName(jsonObject.getString("name"));
                                    customerListModel.setAddress(jsonObject.getString("address"));
                                    customerListModel.setTotal(String.valueOf(jsonObject.getString("total")));
                                    customerListModel.setPrice(jsonObject.getString("price"));
                                    c_list.add(customerListModel);
                                }
                                try {
                                    if (c_list.size() > 0) {
                                        customerListAdapter = new CustomerListAdapter(c_list, baseActivity);
                                        rec_c_list.setAdapter(customerListAdapter);
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(MyCustomersActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(MyCustomersActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                        Utlity.dismiss_dilog(MyCustomersActivity.this);
                    }
                });
    }
}


//holder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
//holder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
