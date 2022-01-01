package com.webzon.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.regex.Pattern;

import webzon.R;


public class CustomActivity extends AppCompatActivity {
    public Toolbar toolbar;

    public CustomActivity mContext;
    //private KProgressHUD dlg;

    public ProgressDialog progressDoalog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        overridePendingTransition(R.anim.left_in, R.anim.left_out);
    }

    @Override
    public void setContentView(int id) {
        super.setContentView(id);
    }

    public void setupActionBar(String title) {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!TextUtils.isEmpty(title)) {
            ActionBar bar = getSupportActionBar();
            assert bar != null;
            bar.setDisplayUseLogoEnabled(false);
            bar.setDisplayShowTitleEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeButtonEnabled(true);
            bar.setTitle(title);
        }
    }

    public void setupActionBar(String title, boolean showBack) {
        try{
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        if (showBack) {
            bar.setDisplayUseLogoEnabled(false);
            bar.setDisplayShowTitleEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
        bar.setTitle(title);
        }catch (IllegalStateException e){e.printStackTrace();}
    }


    public void restartActivity(Class<?> cls) {
        finish();
        Intent intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void restartActivity(Intent intent) {
        finish();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void makeToast(String string) {
        if (TextUtils.isEmpty(string)) return;
        Toast.makeText(mContext, string, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        try{
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
        }catch (IllegalArgumentException e){e.printStackTrace();}
    }


    public void showProgress(String message) {
        progressDoalog = new ProgressDialog(mContext);
        progressDoalog.setMessage("Processing...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    public void showProgress() {
        hideProgress();
        progressDoalog = new ProgressDialog(mContext);
        progressDoalog.setMessage("Processing...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    public void hideProgress() {
        try {
            if (progressDoalog!=null) progressDoalog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void checkString(String value, String simpleMessage) throws Exception {
        if (TextUtils.isEmpty(value) || "0".equals(value)) {
            makeToast("Please input " + simpleMessage);
            throw new Exception();
        }
    }

    protected void checkString2(String value, String simpleMessage, EditText editText) throws Exception {
        if (TextUtils.isEmpty(value) || "0".equals(value)) {
            //makeToast("Please input " + simpleMessage);
            editText.setError(simpleMessage);
            throw new Exception();
        }
    }

    protected void isValidEmail(CharSequence value,String simpleMessage) throws Exception{
        if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            makeToast("Please enter valid " + simpleMessage);
            throw new Exception();
        }
    }

    protected void checkPhoneNumber(String value, String simpleMessage) throws Exception {
        if (TextUtils.isEmpty(value) || "0".equals(value) || value.length()<10) {
            makeToast("Please input " + simpleMessage);
            throw new Exception();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static boolean isValidMobile(String phone) {
        boolean check = false;
        if (phone.length() < 10 || phone.length() > 10) {
            if (phone.length() < 10 || phone.length() > 10) {
                // if(phone.length() != 10) {
                check = false;
                // txtPhone.setError("Not Valid Number");
            } else {
                check = android.util.Patterns.PHONE.matcher(phone).matches();
            }
        } else {
            check = false;
        }
        return check;
    }
}
