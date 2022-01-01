package com.webzon.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;


public class SessionManager extends Application {
    public String username = "";
    public void setPreferences(Context context, String key, String value) {

        if (context!=null){
            SharedPreferences.Editor editor = context.getSharedPreferences("webzon", Context.MODE_PRIVATE).edit();
            editor.putString(key, value);
            editor.commit();
        }
    }

    public String getPreferences(Context context, String key) {

        if (context!=null){
            SharedPreferences prefs = context.getSharedPreferences("webzon",	Context.MODE_PRIVATE);
            username = prefs.getString(key, "");

        }
        return username;
    }

}
