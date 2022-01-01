package com.webzon.Adapter;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.webzon.R;

public class MultipleImageAdapter1 extends RecyclerView.Adapter<MultipleImageAdapter1.MyViewHolder> {
    ArrayList<Uri> mArrayUri;
    Context context;
    SessionManager sessionManager = new SessionManager();
    ArrayList<String> bitmapArray;
    public MultipleImageAdapter1(ArrayList<String> bitmapArray, Context context) {
         this.bitmapArray = bitmapArray;
         this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img,img_cancel;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.Img);
            img_cancel = itemView.findViewById(R.id.ImgCancel);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.images_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        //holder.img.setImageBitmap(bitmapArray.get(position));
         Glide.with(context).load(bitmapArray.get(position)).placeholder(R.drawable.logo).dontAnimate().into(holder.img);
        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 //getdeleteImage(bitmapArray.get(position));
                 deleteItem(position);
            }
        });

    }
    private void deleteItem(int position) {
        bitmapArray.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bitmapArray.size());

    }
    @Override
    public int getItemCount() {
        return bitmapArray.size();
    }

    private void getdeleteImage(String url){
        Utlity.show_progress((Activity) context);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.DeleteImage)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",sessionManager.getPreferences(context,"p_id"))
                .addBodyParameter("item_type","Product")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("image_url",url)
                .addBodyParameter("image_url",url)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog((Activity) context);
                        try {
                            if (response.getString("status").equals("200")) {
                                SnackBar.returnFlashBar((Activity) context,response.getString("message"));
                            }else{
                                SnackBar.returnFlashBar((Activity) context,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog((Activity) context);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

}
