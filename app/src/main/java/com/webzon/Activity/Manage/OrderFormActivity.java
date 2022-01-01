package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;

public class OrderFormActivity extends AppCompatActivity {
    @BindView(R.id.img_work) ImageView img_work;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_form);
        ButterKnife.bind(this);

        img_work.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticVariables.WebPageType = ApiUrl.Order_form;
                String url =ApiUrl.BaseUrl+ApiUrl.Order_form;
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                openCustomTab(OrderFormActivity.this, customIntent.build(), Uri.parse(url));
               // startActivity(new Intent(OrderFormActivity.this,WebViewActivity.class));
            }
        });
    }

    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
}