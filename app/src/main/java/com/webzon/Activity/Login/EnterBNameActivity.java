package com.webzon.Activity.Login;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.CategoryAdapter;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Map.LocationPickerActivity;
import com.webzon.Map.MapUtility;
import com.webzon.Model.CategoryModel;
import com.webzon.Model.GetCategoryModel;
import com.webzon.Model.categoryListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.RecyclerItemClickListener;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class EnterBNameActivity extends CustomActivity {
    @BindView(R.id.li_category) LinearLayout li_category;
    @BindView(R.id.btn_finish) Button btn_finish;
    @BindView(R.id.txt_catName) TextView txt_catName;
    @BindView(R.id.et_addesss) TextView et_addesss;
    @BindView(R.id.userIDTextInputEditText) TextInputEditText userIDTextInputEditText;
    private ArrayList<GetCategoryModel.Data> data1;
    private ArrayList<categoryListModel.Data.Datum> data2;
    CategoryAdapter categoryAdapter;
    EnterBNameActivity baseActivity;
    CategoryModel categoryModel;
    private ArrayList<CategoryModel> data3 =new ArrayList<>();
    String cId="";
    SessionManager sessionManager = new SessionManager();
    private static final int ADDRESS_PICKER_REQUEST = 1020;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_bname);
        ButterKnife.bind(this);
        setupActionBar("Enter Business Name",true);

        getcategoryList();

        li_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilog1();
            }
        });

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userIDTextInputEditText.getText().toString().equals("")){
                    userIDTextInputEditText.setError("Please Enter Business Name");
                }
                else if(cId.equals("")){
                    Toast.makeText(mContext, "Please Select Business Category", Toast.LENGTH_LONG).show();
                }else{
                    getupdateBussiness();
                }

            }
        });

        et_addesss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterBNameActivity.this, LocationPickerActivity.class);
                startActivityForResult(intent, ADDRESS_PICKER_REQUEST);
            }
        });
       // getBlog();
    }

    private void bottomdilog1() {
        ImageView close;
        RecyclerView rec_cat;
        View view = getLayoutInflater().inflate(R.layout.cat_dilog_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        rec_cat =view.findViewById(R.id.rec_cat);
        rec_cat.setHasFixedSize(true);
        rec_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rec_cat.setHasFixedSize(true);
        if(data3.size()>0){
            categoryAdapter = new CategoryAdapter(data3, baseActivity);
            rec_cat.setAdapter(categoryAdapter);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        rec_cat.addOnItemTouchListener(
                new RecyclerItemClickListener(EnterBNameActivity.this, rec_cat, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                       // String list = data1.get(position).getName();
                        txt_catName.setText(data3.get(position).getTitle());
                        cId =data3.get(position).getId()+"";
                        dialog.cancel();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADDRESS_PICKER_REQUEST) {
            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    // String address = data.getStringExtra(MapUtility.ADDRESS);
                    double currentLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    double currentLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);
                    Bundle completeAddress =data.getBundleExtra("fullAddress");
                    et_addesss.setText(new StringBuilder().append(completeAddress.getString("addressline2")));
                    StaticVariables.Address= String.valueOf((new StringBuilder().append(completeAddress.getString("addressline2"))));


                    StaticVariables.Lat=String.valueOf(currentLatitude);
                    StaticVariables.Lng=String.valueOf(currentLongitude);
                 /*   txtLatLong.setText(new StringBuilder().append("Lat:").append(currentLatitude).append
                            ("  Long:").append(currentLongitude).toString());
*/
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void getupdateBussiness() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("name", userIDTextInputEditText.getText().toString());
        hashMap.put("category_id", cId);
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("address", StaticVariables.Address);
        hashMap.put("lat", StaticVariables.Lat);
        hashMap.put("lng", StaticVariables.Lng);
        hashMap.put("photo", "");

        WebServiceHandler webServiceHandler = new WebServiceHandler(EnterBNameActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        sessionManager.setPreferences(EnterBNameActivity.this, "p_status", "0");
                        sessionManager.setPreferences(EnterBNameActivity.this,"loginStatus","1");
                        startActivity(new Intent(EnterBNameActivity.this, CongratulationActivity.class));
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        finish();
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(EnterBNameActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        SnackBar.returnFlashBar(EnterBNameActivity.this,"message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.updateBussiness);
    }


    /*private void getcategoryList1() {
        new ServerRequest<categoryListModel>(this, Consts.getcategoryList(sessionManager.getPreferences(EnterBNameActivity.this,"user_id"),"admin",StaticVariables.DeviceID,StaticVariables.Token,StaticVariables.DeviceType), true) {
            @Override
            public void onCompletion(Call<categoryListModel> call, Response<categoryListModel> response) {
                Log.e("Responce1",response.toString());
                categoryListModel modelRegister = response.body();
                if (modelRegister.getStatus()==200){
                    Log.e("Responce12",response.toString());
                    data2=modelRegister.getData().getData();
                }
                else if (modelRegister.getStatus()==403){
                    StaticVariables.LogoutMessage =response.message();
                    Intent intent=new Intent(EnterBNameActivity.this, DilogActivity.class);
                    startActivity(intent);
                }
            }
        };
    }*/

    private void getcategoryList() {
        data3.clear();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("type", "admin");
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);

        WebServiceHandler webServiceHandler = new WebServiceHandler(EnterBNameActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        //ArrayList<ProductCategoryModel> categoryData1;
                        //ProductCategoryModel productCategoryModel;

                        JSONObject object =new JSONObject(jsonObect.getString("data"));
                        JSONArray jsonArray = object.getJSONArray("data");

                        // Log.e("Data  ",jsonArray.toString());
                        for (int i =0 ; i<jsonArray.length(); i++){
                            categoryModel = new CategoryModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("jsonObject ",jsonObject+"");
                            categoryModel.setId(jsonObject.getString("id"));
                            categoryModel.setTitle(jsonObject.getString("slug"));
                            categoryModel.setImg(jsonObject.getString("title"));
                            data3.add(categoryModel);
                        }
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(EnterBNameActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                         SnackBar.returnFlashBar(EnterBNameActivity.this,jsonObect.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.categoryList);
    }
}