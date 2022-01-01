package com.webzon.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionCheck {

    public static boolean hasConnection(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

//        ConnectivityManager connectivityManager = (ConnectivityManager)
//                getSystemService(this.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//        if (null == ni)
//            return false;
//        return ni.isConnectedOrConnecting();

        if (wifiNetwork != null && wifiNetwork.isConnected()) {
            return true;
        }

        NetworkInfo mobileNetwork = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobileNetwork != null && mobileNetwork.isConnected()) {
            return true;
        }

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            return true;
        }

//        boolean success = false;
//        try {
//            URL url = new URL("https://google.com");
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(20000);
//            connection.connect();
//            success = connection.getResponseCode() == 200;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return success;

        return false;
    }

    public static boolean linkWorking(String link) {
        boolean success = false;
        if (!TextUtils.isEmpty(link)) {
            try {
                URL url = new URL(link);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(20000);
                connection.connect();
                success = connection.getResponseCode() == 200;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return success;
        } else {
            return false;
        }
    }

}