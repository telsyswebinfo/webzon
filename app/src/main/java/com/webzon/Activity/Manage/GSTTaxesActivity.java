package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        btn_create_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GSTTaxesActivity.this, RequestForTaxActivity.class));
            }
        });
    }
}