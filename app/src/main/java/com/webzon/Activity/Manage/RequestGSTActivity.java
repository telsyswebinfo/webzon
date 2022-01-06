package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.webzon.Activity.CustomActivity;
import com.webzon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestGSTActivity extends CustomActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_gstactivity);
        ButterKnife.bind(this);
        setupActionBar("GST",true);
    }
}