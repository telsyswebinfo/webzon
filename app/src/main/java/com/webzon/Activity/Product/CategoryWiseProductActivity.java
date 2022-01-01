package com.webzon.Activity.Product;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.OrderProduct2Adapter;
import com.webzon.Model.ProductList;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;
import com.webzon.utils.productListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class CategoryWiseProductActivity extends CustomActivity {
    @BindView(R.id.txt_price) TextView txt_price;
    @BindView(R.id.txt_share) TextView txt_share;
    @BindView(R.id.img_menu) ImageView img_menu;
    @BindView(R.id.rev_procuct) RecyclerView rev_procuct;
    @BindView(R.id.li_addproduct) LinearLayout li_addproduct;
    @BindView(R.id.swipe) SwipeRefreshLayout swipe;
    SessionManager sessionManager = new SessionManager();
    private ArrayList<productListModel.Product.Datum> categoryData;
    private ArrayList<ProductList> categoryData1 = new ArrayList<>();
    ProductList productList;
    OrderProduct2Adapter orderCategoryAdapter1;
    CategoryWiseProductActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_product);
        ButterKnife.bind(this);

        setupActionBar(sessionManager.getPreferences(CategoryWiseProductActivity.this, "catw_name"),true);
        rev_procuct.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rev_procuct.setHasFixedSize(true);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getproductList1();
                swipe.setRefreshing(false);
            }
        });

        txt_price.setPaintFlags(txt_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilog();
            }
        });


        li_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CategoryWiseProductActivity.this, AddProductActivity.class));
            }
        });
        getproductList1();
    }

    private void bottomdilog() {
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.share_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void getproductList1(){
        categoryData1.clear();
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.productList)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("type","shop")
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("filter","LT")
                .addBodyParameter("start_date","")
                .addBodyParameter("end_date","")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(CategoryWiseProductActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                JSONObject object =new JSONObject(response.getString("product"));
                                JSONArray jsonArray = object.getJSONArray("data");
                               // Log.e("Data  ",jsonArray.toString());
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    productList = new ProductList();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        productList.setId(jsonObject.getString("id"));
                                        productList.setTitle(jsonObject.getString("title"));
                                        productList.setSlug(jsonObject.getString("slug"));
                                        productList.setDescription(jsonObject.getString("description"));
                                        productList.setPrice(jsonObject.getString("price"));
                                        productList.setDiscount(jsonObject.getString("discount"));
                                        productList.setCatId(jsonObject.getString("cat_id"));
                                        productList.setImg(jsonObject.getJSONArray("photo").getJSONObject(0).getString("image"));
                                        productList.setStatus(jsonObject.getString("status"));
                                    Log.e("idd ",sessionManager.getPreferences(CategoryWiseProductActivity.this, "catw_id")+"  "+jsonObject.getString("cat_id"));
                                    if(sessionManager.getPreferences(CategoryWiseProductActivity.this, "catw_id").equals(jsonObject.getString("cat_id"))) {
                                        categoryData1.add(productList);
                                    }
                                }
                                try{
                                    if(categoryData1.size()>0){
                                        Log.e("Data ",categoryData1.size()+"");
                                        orderCategoryAdapter1 = new OrderProduct2Adapter(categoryData1, baseActivity);
                                        rev_procuct.setAdapter(orderCategoryAdapter1);
                                        if(!object.getString("next_page_url").equals("null")){
                                            getproductList2(object.getString("next_page_url"));
                                        }
                                    }
                                }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(CategoryWiseProductActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(CategoryWiseProductActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(CategoryWiseProductActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void getproductList2(String page){
        categoryData1.clear();
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.productList)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("type","shop")
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("filter","LT")
                .addBodyParameter("start_date","")
                .addBodyParameter("end_date","")
                .addBodyParameter("page",page)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(CategoryWiseProductActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                JSONObject object =new JSONObject(response.getString("product"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                // Log.e("Data  ",jsonArray.toString());
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    productList = new ProductList();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    productList.setId(jsonObject.getString("id"));
                                    productList.setTitle(jsonObject.getString("title"));
                                    productList.setSlug(jsonObject.getString("slug"));
                                    productList.setDescription(jsonObject.getString("description"));
                                    productList.setPrice(jsonObject.getString("price"));
                                    productList.setDiscount(jsonObject.getString("discount"));
                                    productList.setCatId(jsonObject.getString("cat_id"));
                                    productList.setImg(jsonObject.getJSONArray("photo").getJSONObject(0).getString("image"));
                                    productList.setStatus(jsonObject.getString("status"));
                                    Log.e("idd ",sessionManager.getPreferences(CategoryWiseProductActivity.this, "catw_id")+"  "+jsonObject.getString("cat_id"));
                                    if(sessionManager.getPreferences(CategoryWiseProductActivity.this, "catw_id").equals(jsonObject.getString("cat_id"))) {
                                        categoryData1.add(productList);
                                    }
                                }
                                try{
                                    if(categoryData1.size()>0){
                                        Log.e("Data ",categoryData1.size()+"");
                                        orderCategoryAdapter1 = new OrderProduct2Adapter(categoryData1, baseActivity);
                                        rev_procuct.setAdapter(orderCategoryAdapter1);
                                        if(!object.getString("next_page_url").equals("null")){
                                            getproductList2(object.getString("next_page_url"));
                                        }
                                    }
                                }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(CategoryWiseProductActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(CategoryWiseProductActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(CategoryWiseProductActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }
}