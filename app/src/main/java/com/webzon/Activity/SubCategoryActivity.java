package com.webzon.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.webzon.halper.StaticVariables;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class SubCategoryActivity extends CustomActivity {
    @BindView(R.id.btn_category) Button btn_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        ButterKnife.bind(this);
        setupActionBar("Sub Category",true);
        btn_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariables.Status="1";
                startActivity(new Intent(SubCategoryActivity.this,HomeActivity2.class));
                finish();
            }
        });
    }
}