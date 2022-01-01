package com.webzon.Activity.Product;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.CatalogItemListAdapter;
import com.webzon.Model.CatLogProductListModel;
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

public class CatalogItemListActivity extends CustomActivity {
    @BindView(R.id.rec_catlist) RecyclerView rec_catlist;
    @BindView(R.id.btn_addproduct) Button btn_addproduct;
    SessionManager sessionManager = new SessionManager();
    CatLogProductListModel catLogProductListModel;
    ArrayList<CatLogProductListModel> list =new ArrayList<>();
    CatalogItemListAdapter catalogItemListAdapter;
    CatalogItemListActivity baseActivity;
    public static ArrayList<String>list1 =new ArrayList<>();
    String product_ids="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_item_list);
        ButterKnife.bind(this);

        rec_catlist.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_catlist.setHasFixedSize(true);
        setupActionBar(sessionManager.getPreferences(CatalogItemListActivity.this, "cat_title"),true);

        getCatLogProductList();

        btn_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 catalogItemListAdapter.arr();
                String currentString =String.valueOf(catalogItemListAdapter.arr());

                Log.e("currentString ",currentString);
                String text = currentString.toString().replace("[", "").replace("]", "");
                product_ids =text;
                getcatLogProductStore();
            }
        });

    }

    private void getCatLogProductList(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CatLogProductList)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("cat_id",sessionManager.getPreferences(this,"c_id"))
                .addBodyParameter("child_cat_id",sessionManager.getPreferences(this,"cat_logid"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Utlity.dismiss_dilog(CatalogItemListActivity.this);
                            if (response.getString("status").equals("200")) {
                                JSONObject object =new JSONObject(response.getString("product"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ",jsonArray.toString());
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    //  for (int i =0 ; i<jsonArray.length(); i++){
                                    catLogProductListModel = new CatLogProductListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    catLogProductListModel.setId(jsonObject.getString("id"));
                                    catLogProductListModel.setTitle(jsonObject.getString("title"));
                                    catLogProductListModel.setPrice(jsonObject.getString("price"));
                                    catLogProductListModel.setImg(jsonObject.getString("img"));
                                    catLogProductListModel.setStatus("0");
                                    list1.add("0");
                                    list.add(catLogProductListModel);
                                }
                                try{
                                    if(list.size()>0){
                                        Log.e("Data ",list.size()+"");
                                        catalogItemListAdapter = new CatalogItemListAdapter(list, baseActivity);
                                        rec_catlist.setAdapter(catalogItemListAdapter);
                                    }
                                }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(CatalogItemListActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(CatalogItemListActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Utlity.dismiss_dilog(CatalogItemListActivity.this);
                        Log.e("error  ",error.toString());
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void getcatLogProductStore(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CatLogProductStore)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("shop_id",sessionManager.getPreferences(this,"s_id"))
                .addBodyParameter("product_ids",product_ids)
                .addBodyParameter("type","shop")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Utlity.dismiss_dilog(CatalogItemListActivity.this);
                            if (response.getString("status").equals("200")) {
                                SnackBar.returnFlashBar(CatalogItemListActivity.this,response.getString("message"));
                                onBackPressed();
                            }else{
                                SnackBar.returnFlashBar(CatalogItemListActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Utlity.dismiss_dilog(CatalogItemListActivity.this);
                        Log.e("error  ",error.toString());
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


