package com.webzon.Activity.Product;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.Utlity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;

public class AddProductActivity extends CustomActivity {
    @BindView(R.id.btn_continue) Button btn_continue;
    @BindView(R.id.txt_product) TextInputEditText txt_product;
    @BindView(R.id.card_catlog) CardView card_catlog;
    @BindView(R.id.li_layout) LinearLayout li_layout;
    SessionManager sessionManager = new SessionManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ButterKnife.bind(this);
        setupActionBar("Add Product",true);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(!txt_product.getText().toString().equals("")){
                  //startActivity(new Intent(AddProductActivity.this,ChooseCategoryActivity.class));
                  StaticVariables.P_title =txt_product.getText().toString();
                  startActivity(new Intent(AddProductActivity.this, AddProduct2Activity.class));
                  overridePendingTransition(R.anim.left_in, R.anim.left_out);
                  finish();

              }else{
                  Toast.makeText(AddProductActivity.this, "Please Enter Product Name", Toast.LENGTH_SHORT).show();
              }

            }
        });

        card_catlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProductActivity.this, CatalogCategoryActivity.class));
                finish();
            }
        });

        getcatLogList();
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
                             Utlity.dismiss_dilog(AddProductActivity.this);
                            Log.e("Responce  ",response.toString());
                            if(response.getString("status").equals("200")){
                                li_layout.setVisibility(View.VISIBLE);
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(AddProductActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }
                            else{
                                li_layout.setVisibility(View.GONE);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                         Utlity.dismiss_dilog(AddProductActivity.this);
                        Log.e("error  ",error.toString());
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }
}