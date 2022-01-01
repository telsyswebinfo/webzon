package com.webzon.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.Login.EnterWhatupNoActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import com.webzon.R;

public class DilogActivity extends Activity {
    SessionManager sessionManager = new SessionManager();
    TextView message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_dilog);
        message =findViewById(R.id.message);
        message.setText(StaticVariables.LogoutMessage);
        getLogout();


    }
    private void getLogout(){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.Logout)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(DilogActivity.this,"user_id"))


                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            if(response.getString("status").equals("200")){
                               // SnackBar.returnFlashBar(AdditionalInformationActivity.this, response.getString("message"));
                                sessionManager.setPreferences(DilogActivity.this,"user_id","");
                                sessionManager.setPreferences(DilogActivity.this,"loginStatus","");
                                sessionManager.setPreferences(DilogActivity.this,"p_status","");
                                Intent intent = new Intent(DilogActivity.this, EnterWhatupNoActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                overridePendingTransition(R.anim.left_in, R.anim.left_out);
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
}