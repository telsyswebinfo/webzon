package com.webzon.ApiClass;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import com.webzon.R;


public class CustomProgressDialog {

    //TODO : VAriable Declaration
    public Context context;
    ImageView iv1;
    public Dialog dialogView;
    public static CustomProgressDialog customProgressDialog;
    AnimationDrawable Anim;


    public static CustomProgressDialog getCustomProgressDialog(Context context) {
        if (customProgressDialog == null) {
            customProgressDialog = new CustomProgressDialog(context);
        }
        return customProgressDialog;
    }

    public CustomProgressDialog(Context context) {
        this.context = context;
    }

    //TODO : Display Custom Dialog
    public void showCustomDialog() {
        dialogView = new Dialog(this.context);
        dialogView.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogView.setCancelable(false);
        dialogView.setContentView(R.layout.layout_progress_dialog);

        iv1 = dialogView.findViewById(R.id.iv1);
//        GlideDrawableImageViewTarget ivSmily = new GlideDrawableImageViewTarget(iv1);
        Glide.with(context).load(R.raw.loader_admin).into(iv1);


//        Picasso.with(this.context).load(R.raw.loader_fse1).into(iv1);
        dialogView.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogView.show();
    }

    //TODO : Dissmiss Dialog
    public void dissmissDialog() {
        try {
            if (dialogView.isShowing()) {
                dialogView.dismiss();
            }
        } catch (Exception e) {
            Log.e("Exception is ", e.getMessage());
        }
    }


    public void setCancelable(boolean flag) {
        dialogView.setCancelable(flag);
    }

    public boolean isDialogShowing() {
        return dialogView.isShowing();
    }

}
