package com.webzon.Activity.Order;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.OrderDetailsAdapter;
import com.webzon.Model.OrderDetailsModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class PendingOrderActivity extends CustomActivity {
    @BindView(R.id.li_acc) LinearLayout li_acc;
    @BindView(R.id.li_rej) LinearLayout li_rej;
    @BindView(R.id.linearLayout) LinearLayout linearLayout;
    @BindView(R.id.txt_total) TextView txt_total;
    @BindView(R.id.txt_Gtotal) TextView txt_Gtotal;
    @BindView(R.id.txt_name) TextView txt_name;
    @BindView(R.id.txt_number) TextView txt_number;
    @BindView(R.id.txt_address) TextView txt_address;
    @BindView(R.id.txt_pincode) TextView txt_pincode;
    @BindView(R.id.txt_city) TextView txt_city;
    @BindView(R.id.txt_pType1) TextView txt_pType1;
    @BindView(R.id.txt_shiping) TextView txt_shiping;
    @BindView(R.id.rec_orderlist) RecyclerView rec_orderlist;
    SessionManager sessionManager = new SessionManager();
    OrderDetailsModel orderListModel;
    OrderDetailsAdapter orderDetailsAdapter;
    private ArrayList<OrderDetailsModel> ordeListdata = new ArrayList<>();
    PendingOrderActivity baseActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_order);
        ButterKnife.bind(this);
        setupActionBar("Order Details",true);

        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilog();
            }
        });

        li_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilog2();
                //bottomdilog1();
            }
        });

        rec_orderlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_orderlist.setHasFixedSize(true);
        getorderDetails();
    }

    private void bottomdilog() {
        LinearLayout li_rej;
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.rej_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        li_rej = view.findViewById(R.id.li_rej);
        close = view.findViewById(R.id.close);
        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  StaticVariables.Status="2";
              //  startActivity(new Intent(PendingOrderActivity.this,HomeActivity2.class));
                dialog.dismiss();
                getorderStatusUpdate("RN");
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void bottomdilog1() {
        LinearLayout li_rej;
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.acc_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        li_rej = view.findViewById(R.id.li_rej);
        close = view.findViewById(R.id.close);
        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  StaticVariables.Status="2";
                //  startActivity(new Intent(PendingOrderActivity.this,HomeActivity2.class));
                dialog.dismiss();
                bottomdilog2();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void bottomdilog2() {
        LinearLayout li_rej;
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.ship_order_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        li_rej = view.findViewById(R.id.li_rej);
        close = view.findViewById(R.id.close);
        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  StaticVariables.Status="2";
                //  startActivity(new Intent(PendingOrderActivity.this,HomeActivity2.class));
                dialog.dismiss();
                getorderStatusUpdate("AC");
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    public void getorderDetails(){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.OrderDetails)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("order_id",StaticVariables.O_Id)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Responce ",response.toString());
                        try {
                            if (response.getString("status").equals("200")) {
                            JSONObject object =new JSONObject(response.getString("data"));
                            JSONArray jsonArray = object.getJSONArray("order_items");

                            //txt_orderid.setText(object.getString("order_no"));
                            txt_total.setText(object.getString("total_amount"));
                            txt_total.setText(object.getString("total_amount"));
                            txt_shiping.setText(object.getString("shipping_fee"));
                            txt_Gtotal.setText(object.getString("grand_total"));
                            txt_name.setText(object.getJSONObject("user_address").getString("name"));
                            txt_number.setText(object.getJSONObject("user_address").getString("mobile"));
                            txt_address.setText(object.getJSONObject("user_address").getString("address"));
                            txt_pType1.setText(object.getString("payment_method"));
                            txt_city.setText(object.getJSONObject("user_address").getString("city"));
                            txt_pincode.setText(object.getJSONObject("user_address").getString("zipcode"));
                            if(object.getString("status").equals("PN")){
                                linearLayout.setVisibility(View.VISIBLE);
                            }else{
                                linearLayout.setVisibility(View.GONE);
                            }

                           // Log.e("Data  ",jsonArray.toString());
                            for (int i =0 ; i<jsonArray.length(); i++){
                                orderListModel = new OrderDetailsModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                orderListModel.setTitle(jsonObject.getJSONObject("product_items").getString("title"));
                                orderListModel.setPrice(jsonObject.getString("price"));
                                orderListModel.setQty(jsonObject.getString("qty"));
                                orderListModel.setImage(jsonObject.getJSONObject("product_items").getJSONArray("photo").getJSONObject(0).getString("image"));
                                ordeListdata.add(orderListModel);
                            }
                             if(ordeListdata.size()>0){
                                 orderDetailsAdapter = new OrderDetailsAdapter(ordeListdata, baseActivity);
                                 rec_orderlist.setAdapter(orderDetailsAdapter);
                            }
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(PendingOrderActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(PendingOrderActivity.this,response.getString("message"));
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
    }

    public void getorderStatusUpdate(String status){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.OrderStatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("item_id",StaticVariables.O_Id)
                .addBodyParameter("item_type","Order")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("status",status)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Responce ",response.toString());
                        try {
                            if (response.getString("status").equals("200")) {
                                onBackPressed();
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(PendingOrderActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(PendingOrderActivity.this,response.getString("message"));
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}