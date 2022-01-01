package com.webzon.Activity.Manage;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;


public class CreateCouponsActivity extends CustomActivity {
    @BindView(R.id.et_co_code) TextInputEditText et_co_code;
    @BindView(R.id.et_usees) TextInputEditText et_usees;
    @BindView(R.id.et_per) TextInputEditText et_per;
    @BindView(R.id.et_min_order) TextInputEditText et_min_order;
    @BindView(R.id.sw_show) Switch sw_show;
    @BindView(R.id.radioGroup1) RadioGroup radioGroup1;
    @BindView(R.id.btn_create) Button btn_create;
    @BindView(R.id.et_tusees) TextInputEditText et_tusees;
    @BindView(R.id.et_min_dis) TextInputEditText et_min_dis;
    @BindView(R.id.txt_sdate) TextView txt_sdate;
    @BindView(R.id.txt_edate) TextView txt_edate;
    @BindView(R.id.til_per) TextInputLayout til_per;
    Calendar myCalendar;
    String thisDate,thisDate1,dis_type="percent",c_show="inactive";
    String status;

    SessionManager sessionManager = new SessionManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_coupons);
        ButterKnife.bind(this);
        setupActionBar("Create Coupons",true);

        myCalendar = Calendar.getInstance();

        SimpleDateFormat curFormater = new SimpleDateFormat("dd-MM-yyyy");
        Date todayDate = new Date();
        thisDate = curFormater.format(todayDate);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(todayDate);
        calendar.add(Calendar.DAY_OF_YEAR, +15);
        Date newDate = calendar.getTime();
        thisDate1 = curFormater.format(newDate);
        txt_sdate.setText(thisDate);
        txt_edate.setText(thisDate);
        Log.e("S_Date ",thisDate);
        Log.e("E_Date ",thisDate1+"");

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.radioButton1) {
                    et_per.setHint("Percent");
                } else if (checkedId == R.id.radioButton2) {
                    dis_type="fixed";
                }

            }
        });

        sw_show.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    c_show ="active";
                } else {
                    c_show="inactive";
                }
            }
        });


                et_co_code.setText(createRandomCode(8));

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(et_min_order.getText().toString())>=Integer.parseInt(et_min_dis.getText().toString())){
                    getCreateCoupan();
                }else{
                    Toast.makeText(CreateCouponsActivity.this, "Please enter valid Amount", Toast.LENGTH_SHORT).show();
                }

            }
        });




        txt_sdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_date_picker(CreateCouponsActivity.this,txt_sdate);
            }
        });

        txt_edate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_date_picker1(CreateCouponsActivity.this,txt_edate);
            }
        });


    }

    private void getCreateCoupan(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CreateCoupan)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("code",et_co_code.getText().toString())
                .addBodyParameter("shop_id",sessionManager.getPreferences(this,"s_id"))
                .addBodyParameter("max_use",et_usees.getText().toString())
                .addBodyParameter("max_use_total",et_tusees.getText().toString())
                .addBodyParameter("min_order_amount",et_min_order.getText().toString())
                .addBodyParameter("min_discount_amount",et_min_dis.getText().toString())
                .addBodyParameter("start_date",thisDate)
                .addBodyParameter("end_date",thisDate1)
                .addBodyParameter("type",dis_type)
                .addBodyParameter("value",et_per.getText().toString())
                .addBodyParameter("status",c_show)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(CreateCouponsActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                onBackPressed();
                                SnackBar.returnFlashBar(CreateCouponsActivity.this,response.getString("message"));
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(CreateCouponsActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(CreateCouponsActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(CreateCouponsActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public String createRandomCode(int codeLength){
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new SecureRandom();
        for (int i = 0; i < codeLength; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        String output = sb.toString();
        System.out.println(output);
        return output ;
    }


    public static void show_date_picker(Activity activity, final TextView tv) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                tv.setText(sdf.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 0);
         dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide past date,month and year
        dialog.show();
    }
    public static void show_date_picker1(Activity activity, final TextView tv) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                tv.setText(sdf.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 0);
        //dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide past date,month and year
        dialog.show();
    }
}

