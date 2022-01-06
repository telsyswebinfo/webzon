package com.webzon.Activity.Manage;



import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Adapter.ITRListAdapter;
import com.webzon.Adapter.OrderCategoryAdapter;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Model.ModelITRList;
import com.webzon.Model.ProductCategoryModel;
import com.webzon.R;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TaxesActivity extends CustomActivity {
    @BindView(R.id.li_btn_create_tax) LinearLayout li_btn_create_tax;
    @BindView(R.id.lay_nodata) LinearLayout lay_nodata;
    @BindView(R.id.rev_itr) RecyclerView rev_itr;
    SessionManager sessionManager = new SessionManager();
    ArrayList<ModelITRList> list =new ArrayList<ModelITRList>();
    ModelITRList modelITRList;
    ITRListAdapter itrListAdapter;
    TaxesActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes);
        ButterKnife.bind(this);
        setupActionBar("Tax",true);

        rev_itr.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rev_itr.setHasFixedSize(true);

        li_btn_create_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TaxesActivity.this, RequestForTaxActivity.class));
            }
        });
        getitrList();

    }

    private void bottomdilogbox() {
        Button btn_submit;
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.taxes_items, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        btn_submit =view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void getitrList() {
        list.clear();

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(TaxesActivity.this,"user_id"));
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("type","shop");
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("filter","LT");
        hashMap.put("start_date","");
        hashMap.put("end_date","");

        WebServiceHandler webServiceHandler = new WebServiceHandler(this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        //ArrayList<ProductCategoryModel> categoryData1;
                        //ProductCategoryModel productCategoryModel;

                        JSONObject object =new JSONObject(jsonObect.getString("itr"));
                        Log.e("jsonObject123 ",object.getString("next_page_url")+"Hello");
                        JSONArray jsonArray = object.getJSONArray("data");

                        for (int i =0 ; i<jsonArray.length(); i++){
                            modelITRList = new ModelITRList();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("jsonObject ",jsonObject+"");
                            modelITRList.setPan_nmuber(jsonObject.getString("pan_nmuber"));
                            modelITRList.setAadhar_card_number(jsonObject.getString("aadhar_card_number"));
                            modelITRList.setFirst_name(jsonObject.getString("first_name")+" "+jsonObject.getString("middle_name")+" "+jsonObject.getString("last_name"));
                            modelITRList.setFather_name(jsonObject.getString("father_name"));
                            modelITRList.setMobile_number(jsonObject.getString("mobile_number"));
                            modelITRList.setStatus(jsonObject.getString("status"));
                            modelITRList.setReason(jsonObject.getString("reason"));
                            list.add(modelITRList);
                        }

                        try {
                            if(list.size()>0){
                                lay_nodata.setVisibility(View.GONE);
                                rev_itr.setVisibility(View.VISIBLE);
                                itrListAdapter = new ITRListAdapter(list, baseActivity);
                                rev_itr.setAdapter(itrListAdapter);
                               /* if(!object.getString("next_page_url").equals("null")){
                                    getproductList2(object.getString("next_page_url"));
                                }*/
                                //
                            }
                        }catch (NullPointerException e){e.printStackTrace();}
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(TaxesActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        lay_nodata.setVisibility(View.VISIBLE);
                        rev_itr.setVisibility(View.GONE);
                        // SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.itrList);
    }



}