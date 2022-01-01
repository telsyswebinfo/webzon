package com.webzon.Activity.Product;

import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.SubCategoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;

public class ChooseCategoryActivity extends CustomActivity {
    @BindView(R.id.card_grocery) CardView card_grocery;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);
        ButterKnife.bind(this);
        setupActionBar("Choose Category",true);
        card_grocery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChooseCategoryActivity.this, SubCategoryActivity.class));
            }
        });
    }
}