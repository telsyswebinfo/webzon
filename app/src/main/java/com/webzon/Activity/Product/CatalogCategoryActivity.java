package com.webzon.Activity.Product;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.CatalogListAdapter;
import com.webzon.Model.CatLogListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.RecyclerItemClickListener;
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

public class CatalogCategoryActivity extends CustomActivity {
    SessionManager sessionManager = new SessionManager();
    CatalogCategoryActivity baseActivity;
    ArrayList<CatLogListModel> list= new ArrayList<>();
    CatLogListModel catLogListModel;
    @BindView(R.id.rec_cat) RecyclerView rec_cat;
    CatalogListAdapter catalogListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog_category);
        ButterKnife.bind(this);
        setupActionBar("Choose Category",true);
        rec_cat.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_cat.setHasFixedSize(true);
        getcatLogList();

        rec_cat.addOnItemTouchListener(
                new RecyclerItemClickListener(this, rec_cat, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String catlogid = list.get(position).getId();
                        String cattitle = list.get(position).getTitle();
                        sessionManager.setPreferences(CatalogCategoryActivity.this, "cat_logid", catlogid);
                        sessionManager.setPreferences(CatalogCategoryActivity.this, "cat_title", cattitle);
                        startActivity(new Intent(CatalogCategoryActivity.this, CatalogItemListActivity.class));
                        finish();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    private void getcatLogList(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CatLogList)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("type","admin")
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("category_id",sessionManager.getPreferences(this,"c_id"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Utlity.dismiss_dilog(CatalogCategoryActivity.this);
                            if (response.getString("status").equals("200")) {
                                JSONObject object =new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ",jsonArray.toString());
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    catLogListModel = new CatLogListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    catLogListModel.setId(jsonObject.getString("id"));
                                    catLogListModel.setTitle(jsonObject.getString("title"));
                                    catLogListModel.setPhoto(jsonObject.getString("photo"));
                                    list.add(catLogListModel);
                                }
                                try{
                                    if(list.size()>0){
                                        Log.e("Data ",list.size()+"");
                                        catalogListAdapter = new CatalogListAdapter(list, baseActivity);

                                        rec_cat.setAdapter(catalogListAdapter);
                                    }
                                }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(CatalogCategoryActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(CatalogCategoryActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        Utlity.dismiss_dilog(CatalogCategoryActivity.this);
                        Log.e("error  ",error.toString());
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }
}