package com.webzon.utils;

import android.app.Activity;

import com.andrognito.flashbar.Flashbar;

import webzon.R;


public class SnackBar {

    public static Flashbar returnFlashBar(Activity context, String message){

        Flashbar FlashBar = null;
        if (FlashBar == null) {
            FlashBar = new Flashbar.Builder(context)
                    .gravity(Flashbar.Gravity.BOTTOM)
                    .message(message)
                    .backgroundDrawable(R.color.black)
                    .messageSizeInSp(16)
                    .messageColorRes(R.color.white)
                    .duration(3000)
                    .build();
        }
        FlashBar.show();
        return FlashBar;
    }

}