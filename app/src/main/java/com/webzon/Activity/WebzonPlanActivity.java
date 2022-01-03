package com.webzon.Activity;



import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import com.webzon.Adapter.WebzonPlanAdapter;
import com.webzon.Adapter.WebzonpcAdapter;
import com.webzon.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebzonPlanActivity extends AppCompatActivity {
    @BindView(R.id.rec_plan)
    RecyclerView rec_plan;
    @BindView(R.id.btn_sw)
    Switch btn_sw;
    @BindView(R.id.tvquarterly)
    TextView tvquarterly;
    @BindView(R.id.tvyearly)
    TextView tvyearly;
    @BindView(R.id.cardviewLite)
    CardView cardviewLite;
    @BindView(R.id.tvlite)
    TextView tvlite;
    @BindView(R.id.tvliters)
    TextView tvliters;
    @BindView(R.id.tvliteyear)
    TextView tvliteyear;
    @BindView(R.id.img_lite_right)
    ImageView img_lite_right;
    @BindView(R.id.cardviewPremimum)
    CardView cardviewPremimum;
    @BindView(R.id.tvpremium)
    TextView tvpremium;
    @BindView(R.id.tvpremiumrs)
    TextView tvpremiumrs;
    @BindView(R.id.tvpremiumyear)
    TextView tvpremiumyear;
    @BindView(R.id.img_premium_right)
    ImageView img_premium_right;
    @BindView(R.id.tvLite)
    TextView tvLite;
    @BindView(R.id.tvPremium)
    TextView tvPremium;
    @BindView(R.id.btn_subscribe)
    TextView btn_subscribe;
    @BindView(R.id.cross_arrow)
    ImageView cross_arrow;
    WebzonPlanAdapter webzonPlanAdapter;
    public  static boolean Type = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webzon_plan);
        ButterKnife.bind(this);
        cardviewLite.setBackgroundResource(R.drawable.litepremimumround);
        tvlite.setTextColor(getResources().getColor(R.color.purple_200));
        tvliters.setTextColor(getResources().getColor(R.color.purple_200));
        tvliteyear.setTextColor(getResources().getColor(R.color.purple_200));
        btn_subscribe.setText("Subscribe to Premium for "+tvliters.getText().toString());
        img_lite_right.setVisibility(View.VISIBLE);
        cross_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        rec_plan.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rec_plan.setHasFixedSize(true);
        webzonPlanAdapter = new WebzonPlanAdapter(this);
        rec_plan.setAdapter(webzonPlanAdapter);
        btn_subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    tvyearly.setTextColor(getResources().getColor(R.color.black));
                    cardviewPremimum.setBackgroundResource(R.drawable.litepremimumround);
                    tvPremium.setBackgroundResource(R.color.bg_grey);
                    tvLite.setBackgroundResource(R.color.white);
                    tvpremium.setTextColor(getResources().getColor(R.color.purple_200));
                    tvpremiumrs.setTextColor(getResources().getColor(R.color.purple_200));
                    tvpremiumyear.setTextColor(getResources().getColor(R.color.purple_200));
                    img_premium_right.setVisibility(View.VISIBLE);
                    cardviewLite.setBackgroundResource(R.drawable.bg_otp_box_active);
                    tvlite.setTextColor(getResources().getColor(R.color.grey));
                    tvliters.setTextColor(getResources().getColor(R.color.grey));
                    tvliteyear.setTextColor(getResources().getColor(R.color.grey));
                    img_lite_right.setVisibility(View.GONE);
                    tvquarterly.setTextColor(getResources().getColor(R.color.grey));
                    btn_subscribe.setText("Subscribe to Premium for "+tvpremiumrs.getText().toString());
                    Type = true;
                    webzonPlanAdapter.notifyDataSetChanged();
                    Log.d("btn","yes");
                }else {
                    tvyearly.setTextColor(getResources().getColor(R.color.grey));
                    cardviewPremimum.setBackgroundResource(R.drawable.bg_otp_box_active);
                    tvPremium.setBackgroundResource(R.color.white);
                    tvLite.setBackgroundResource(R.color.bg_grey);
                    tvpremium.setTextColor(getResources().getColor(R.color.grey));
                    tvpremiumrs.setTextColor(getResources().getColor(R.color.grey));
                    tvpremiumyear.setTextColor(getResources().getColor(R.color.grey));
                    img_premium_right.setVisibility(View.GONE);
                    cardviewLite.setBackgroundResource(R.drawable.litepremimumround);
                    tvlite.setTextColor(getResources().getColor(R.color.purple_200));
                    tvliters.setTextColor(getResources().getColor(R.color.purple_200));
                    tvliteyear.setTextColor(getResources().getColor(R.color.purple_200));
                    img_lite_right.setVisibility(View.VISIBLE);
                    tvquarterly.setTextColor(getResources().getColor(R.color.black));
                    btn_subscribe.setText("Subscribe to Premium for "+tvliters.getText().toString());
                    Type = false;
                    webzonPlanAdapter.notifyDataSetChanged();
                    Log.d("btn","no");
                }
            }
        });
    }

}