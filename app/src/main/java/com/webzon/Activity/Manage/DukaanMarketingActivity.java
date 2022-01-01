package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.webzon.Activity.CreateCampaignActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;

public class DukaanMarketingActivity extends AppCompatActivity {
    @BindView(R.id.li_create_cam) LinearLayout li_create_cam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dukaan_marketing2);
        ButterKnife.bind(this);

        li_create_cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DukaanMarketingActivity.this, CreateCampaignActivity.class));
            }
        });
    }
}
