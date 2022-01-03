package com.webzon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.webzon.Adapter.CatalogListAdapter;
import com.webzon.Adapter.WebzonpcAdapter;
import com.webzon.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebzonpcActivity extends AppCompatActivity {
    @BindView(R.id.rec_pc)
    RecyclerView rec_pc;
    @BindView(R.id.back_arrow)
    ImageView back_arrow;
    WebzonpcAdapter webzonpcAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webzonpc);
        ButterKnife.bind(this);
        back_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rec_pc.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_pc.setHasFixedSize(true);
        webzonpcAdapter = new WebzonpcAdapter(this);
        rec_pc.setAdapter(webzonpcAdapter);
    }
}