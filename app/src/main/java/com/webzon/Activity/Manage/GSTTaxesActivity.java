package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

import com.webzon.Activity.CustomActivity;
import com.webzon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GSTTaxesActivity extends CustomActivity {
    @BindView(R.id.btn_create_tax) Button btn_create_tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsttaxes);
        ButterKnife.bind(this);
        setupActionBar("Tax",true);
    }
}