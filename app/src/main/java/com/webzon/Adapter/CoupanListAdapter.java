package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.Manage.DiscountCouponsActivity;
import com.webzon.Model.CoupanListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.webzon.R;

public class CoupanListAdapter extends RecyclerView.Adapter<CoupanListAdapter.MyViewHolder> {
    private ArrayList<CoupanListModel> data;
    Context context;
    DiscountCouponsActivity baseActivity;
    SessionManager sessionManager = new SessionManager();

    public CoupanListAdapter(ArrayList<CoupanListModel> data, DiscountCouponsActivity mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Switch switch1;
        TextView txt_code,txt_order,txt_tused,txt_share,txt_sg,txt_code1,txt_order1,txt_tused1,txt_sg1;
        CardView card;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txt_code = itemView.findViewById(R.id.txt_code);
            txt_order = itemView.findViewById(R.id.txt_order);
            txt_tused = itemView.findViewById(R.id.txt_tused);
            txt_sg = itemView.findViewById(R.id.txt_sg);
            txt_share = itemView.findViewById(R.id.txt_share);
            switch1 = itemView.findViewById(R.id.switch1);

            txt_code1 = itemView.findViewById(R.id.txt_code1);
            txt_order1 = itemView.findViewById(R.id.txt_order1);
            txt_tused1 = itemView.findViewById(R.id.txt_tused1);
            txt_sg1 = itemView.findViewById(R.id.txt_sg1);
            card = itemView.findViewById(R.id.card);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dis_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.txt_code.setText(data.get(position).getCode());
        if(data.get(position).getStatus().equals("percent")){
            holder.txt_order.setText(data.get(position).getValue()+" off on all orders above \u20B9"+data.get(position).getMin_order_amount());
        }else{
            holder.txt_order.setText("\u20B9"+data.get(position).getValue()+" off on all orders above \u20B9"+data.get(position).getMin_order_amount()+"upto"+data.get(position).getMin_discount_amount());
        }


        holder.txt_tused.setText(data.get(position).getMax_use_total());
        holder.txt_sg.setText( "\u20B9" +data.get(position).getMin_discount_amount().toString());
        if(data.get(position).getStatus().equals("active")){
            holder.switch1.setChecked(true);
        }else{
            holder.switch1.setChecked(false);
        }
        ////***************************************
        holder.txt_code1.setText(data.get(position).getCode());
        if(data.get(position).getStatus().equals("percent")){
            holder.txt_order1.setText(data.get(position).getValue()+" off on all orders above \u20B9"+data.get(position).getMin_order_amount());
        }else{
            holder.txt_order1.setText("\u20B9"+data.get(position).getValue()+" off on all orders above \u20B9"+data.get(position).getMin_order_amount()+"upto"+data.get(position).getMin_discount_amount());
        }


        holder.txt_tused1.setText(data.get(position).getMax_use_total());
        holder.txt_sg1.setText( "\u20B9" +data.get(position).getMin_discount_amount().toString());
        ////***************************************


        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                   // statusUpdate(data.get(position).getId(),position,holder);
                    getUpdateStaus(data.get(position).getId(),position,holder,"active");
                } else {
                    getUpdateStaus(data.get(position).getId(),position,holder,"inactive");
                }
            }
        });

        holder.txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareImage(holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void getDeleteProduct(String id, int pos){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",id)
                .addBodyParameter("item_type","Product")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("status","delete")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("200")){
                                data.remove(pos);
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }
    private void bottomdilog(String id, int pos) {
        ImageView close;
        LinearLayout li_rej;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.deletep_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close = view.findViewById(R.id.close);
        li_rej = view.findViewById(R.id.li_rej);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                getDeleteProduct(id,pos);
            }
        });
    }


    public void swapeItem(int fromPosition,int toPosition){
        Collections.swap(data, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }
    private void statusUpdate(String id, int pos, MyViewHolder holder) {
        ImageView close;
        LinearLayout li_hide,li_outofStock;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
       // View view = inflater.inflate(R.layout.deletep_bottom_sheet, null);
        View view = inflater.inflate(R.layout.stock_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(context,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        li_hide =view.findViewById(R.id.li_hide);
        li_outofStock =view.findViewById(R.id.li_outofStock);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.switch1.setChecked(false);
                data.get(pos).setStatus("inactive");
                notifyItemInserted(pos);
                dialog.cancel();
            }
        });
        li_outofStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
              // getUpdateStaus(id,pos,holder);
            }
        });
        li_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                getUpdateStaus1(id,pos,holder);
            }
        });
    }

    private void getUpdateStaus(String id, int pos, MyViewHolder holder,String status){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",id)
                .addBodyParameter("item_type","Coupon")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("type","shop")
                .addBodyParameter("status",status)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Responce  ",response+"");
                            if(response.getString("status").equals("200")){
                              //  holder.txt_instock.setText("Out of Stock");
                               // data.remove(0);
                                //data.get(pos).
                                //notifyDataSetChanged();
                                data.get(pos).setStatus(status);
                                notifyItemChanged(pos);
                                SnackBar.returnFlashBar((Activity) context,response.getString("message"));
                            }else {
                                SnackBar.returnFlashBar((Activity) context,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void getUpdateStaus1(String id, int pos, MyViewHolder holder){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",id)
                .addBodyParameter("item_type","Coupan")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                    .addBodyParameter("status","inactive")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("200")){
                                //holder.txt_instock.setText("Hidden");
                                // data.remove(0);
                                //data.get(pos).
                                //notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void shareImage(MyViewHolder holder) {
        try {
            holder.card.setDrawingCacheEnabled(true);
            holder.card.buildDrawingCache();
            Bitmap b = holder.card.getDrawingCache();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),
                    b, "Title", null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            context.startActivity(Intent.createChooser(share, "Select"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("Error ",e.toString());
        }
    }



}
