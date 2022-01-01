package com.webzon.Activity.Product;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Adapter.OrderTypeListAdapter;
import com.webzon.Model.orderListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.webzon.R;

public class ProductDetailsActivity extends AppCompatActivity {
    SessionManager sessionManager = new SessionManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        getorderDetails();
    }

    public void getorderDetails(){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.OrderDetails)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("order_id","2")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Responce ",response.toString());
                      /*  try {
                            JSONObject object =new JSONObject(response.getString("data"));
                            JSONArray jsonArray = object.getJSONArray("data");
                            Log.e("Data  ",jsonArray.toString());
                            for (int i =0 ; i<jsonArray.length(); i++){
                                orderListModel1 = new orderListModel();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                orderListModel1.setOrder_no(jsonObject.getString("order_no"));
                                orderListModel1.setOrder_no(jsonObject.getString("order_no"));
                                orderListModel1.setOrder_time(jsonObject.getString("order_time"));
                                orderListModel1.setOrder_items(String.valueOf(jsonObject.getJSONArray("order_items").length()));
                                orderListModel1.setCurrency(jsonObject.getString("currency"));
                                orderListModel1.setGrand_total(jsonObject.getString("grand_total"));
                                orderListModel1.setPayment_method(jsonObject.getString("payment_method"));
                                orderListModel1.setStatus(jsonObject.getString("status"));
                                orderListModel1.setImage(String.valueOf(jsonObject.getJSONArray("order_items").getJSONObject(0).getJSONObject("product_items").getJSONArray("photo").getJSONObject(0).getString("image")));
                                JSONArray jsonArray1 = object.getJSONArray("data");
                                ordeListdata.add(orderListModel1);
                            }
                            if(ordeListdata.size()>0){
                                orderTypeListAdapter = new OrderTypeListAdapter(ordeListdata, baseActivity);
                                rec_orderslist.setAdapter(orderTypeListAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                     }
                });
    }
}