package com.webzon.Activity.Account;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;

import com.webzon.Activity.CustomActivity;
import com.webzon.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PrivacyPolicyActivity extends CustomActivity {
    @BindView(R.id.webview) WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        setupActionBar("Privacy Policy",true);
        ButterKnife.bind(this);
    }
    private void handleNewUrl(String url) {
        Uri uri = Uri.parse(url);

        if (uri.getScheme().equals("http") || uri.getScheme().equals("https"))
            openExternalWebsite(url);
        else if (uri.getScheme().equals("intent")) {
            String appPackage = getAppPackageFromUri(uri);

            if (appPackage != null) {
                PackageManager manager =  getPackageManager();
                Intent appIntent = manager.getLaunchIntentForPackage(appPackage);

                if (appIntent != null) {
                    startActivity(appIntent);
                } else {
                    openExternalWebsite("https://play.google.com/store/apps/details?id=" + appPackage);
                }
            }
        }
    }

    private String getAppPackageFromUri(Uri intentUri) {
        Pattern pattern = Pattern.compile("package=(.*?);");
        Matcher matcher = pattern.matcher(intentUri.getFragment());

        if (matcher.find())
            return matcher.group(1);

        return null;
    }

    private void openExternalWebsite(String url) {
        Intent webeIntent = new Intent(Intent.ACTION_VIEW);
        webeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        webeIntent.setData(Uri.parse(url));
        startActivity(webeIntent);
    }
}

