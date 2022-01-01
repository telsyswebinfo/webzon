package com.webzon.Activity.Manage;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.CardTypeAdapter;
import com.webzon.Model.PromotionalListModel;
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
import com.webzon.R;

public class MarketingsActivity extends CustomActivity {
    @BindView(R.id.rev_card) RecyclerView rev_card;
    SessionManager sessionManager = new SessionManager();
    PromotionalListModel productlist;
    ArrayList<PromotionalListModel> list =new ArrayList<>();
    CardTypeAdapter cardTypeAdapter;
    MarketingsActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketings);
        ButterKnife.bind(this);
        setupActionBar("Marketings",true);

        rev_card.setLayoutManager(new GridLayoutManager(this, 2));
        rev_card.setHasFixedSize(true);
        getPromotionalList();

        rev_card.addOnItemTouchListener(
                new RecyclerItemClickListener(MarketingsActivity.this, rev_card, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        sessionManager.setPreferences(MarketingsActivity.this,"promotional_id",list.get(position).getId());
                        sessionManager.setPreferences(MarketingsActivity.this,"promotional_title",list.get(position).getTitle());
                        startActivity(new Intent(MarketingsActivity.this, BusinesCardActivity.class));
                    }
                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }
    private void getPromotionalList(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.PromotionalList)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("page","1")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(MarketingsActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                JSONObject object = new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ",jsonArray.toString());
                               /* PromotionalListModel productlist;
                                BusinesCardAdapter businesCardAdapter;*/
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    productlist = new PromotionalListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    productlist.setId(jsonObject.getString("id"));
                                    productlist.setTitle(jsonObject.getString("title"));
                                    productlist.setPhoto(jsonObject.getString("photo"));
                                    list.add(productlist);
                                }
                                try{
                                    if(list.size()>0){
                                        Log.e("Data ",list.size()+"");
                                        cardTypeAdapter = new CardTypeAdapter(list, baseActivity);
                                        rev_card.setAdapter(cardTypeAdapter);
                                    }

                                }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(MarketingsActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(MarketingsActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(MarketingsActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }
}