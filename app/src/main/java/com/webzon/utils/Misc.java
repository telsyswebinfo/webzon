package com.webzon.utils;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import java.util.HashMap;

import com.webzon.R;

public class Misc {
    public interface yesNo {
        void yes();

        void no();
    }

    public interface resp {
        void clicked();
    }

    public static void setLogo(Context context, TextView textView) {
        if (context.getString(R.string.app_name).equals("Mintly")) {
            Spannable word1 = new SpannableString("Mint");
            word1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.green)), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            word1.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(word1);
            Spannable word2 = new SpannableString("ly");
            word2.setSpan(new ForegroundColorSpan(Color.WHITE), 0, word2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.append(word2);
        }
    }

    public static Spanned html(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }






    public static Dialog decoratedDiag(Context context, int layout, float opacity) {
        Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent_NoTitleBar);
        View view = LayoutInflater.from(context).inflate(layout, null);
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window w = dialog.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.dimAmount = opacity;
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        w.setAttributes(lp);
        w.setGravity(Gravity.CENTER);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            w.setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            w.setNavigationBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        }
        return dialog;
    }

    public static Animation alphaAnim() {
        Animation anim = new AlphaAnimation(0.5f, 1.0f);
        anim.setDuration(800);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        return anim;
    }

    private static Dialog diag;


}