package com.webzon.Activity.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.webzon.Activity.CustomActivity;
import com.webzon.BuildConfig;
import com.webzon.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ShareAppActivity extends CustomActivity {
    @BindView(R.id.btn_share) Button btn_share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_app);
        ButterKnife.bind(this);
        setupActionBar("Share App",true);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage= "\nHello"+"\nWe have launched our online store and if you would like to check our online catalougue, please visit\nhttp://webzon.in/" +
                            "\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
    }



}