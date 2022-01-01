package com.webzon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;

import com.webzon.R;

public class WebViewActivity extends CustomActivity {
    private WebView webview ;
    ProgressBar progressBar;
    ProgressDialog progressBar1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_web_view);
        webview =findViewById(R.id.webview);
        progressBar =findViewById(R.id.progress);
        setupActionBar(StaticVariables.WebPageType,true);
        Log.e("Url ",StaticVariables.WebPageType);

        progressBar1 = new ProgressDialog(this);
        progressBar1.setCancelable(true);
        progressBar1.setMessage(getResources().getString(R.string.loading));
        //webview.setWebViewClient(new MyWebViewClient());
       // webview.getSettings().setBuiltInZoomControls(true);
       // webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webview.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" +StaticVariables.WebPageType);

    }

    private class MyWebViewClient extends WebViewClient {
        boolean loadingFinished = true;
        boolean redirect = false;
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub
            // progressBar.setVisibility(View.VISIBLE);
            //progressBar1.show();
            if (!loadingFinished) {
                redirect = true;
                progressBar1.cancel();
            }

            loadingFinished = false;

            view.loadUrl(url);
            return true;

        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            //progressBar1.show();
            loadingFinished = false;
            progressBar1.cancel();
            // progressBar.setVisibility(View.VISIBLE);
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);

            if(!redirect){
                loadingFinished = true;

            }
            //call remove_splash in 500 miSec
            if(loadingFinished && !redirect){

            } else{
                redirect = false;

            }

            //  progressBar.setVisibility(View.GONE);

        }

    }
}