package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.Product.EditProductActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Model.ProductList;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

import webzon.R;

public class OrderProduct1Adapter extends RecyclerView.Adapter<OrderProduct1Adapter.MyViewHolder> {
    private ArrayList<ProductList> data;
    Context context;
    HomeActivity2 baseActivity;
    Switch Sbtn;

    SessionManager sessionManager = new SessionManager();
     int type;

    public OrderProduct1Adapter(ArrayList<ProductList> data, HomeActivity2 mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img,img1;
        LinearLayout img_edit,li_raw;
        CardView card;
        Switch switch1;
        TextView txt_title,txt_count,txt_instock,txt_price,txt_dprice,txt_share;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.img);
            img_edit = itemView.findViewById(R.id.img_edit);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_instock = itemView.findViewById(R.id.txt_instock);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_dprice = itemView.findViewById(R.id.txt_dprice);
            switch1 = itemView.findViewById(R.id.switch1);
            txt_share = itemView.findViewById(R.id.txt_share);
            li_raw = itemView.findViewById(R.id.li_raw);
            card = itemView.findViewById(R.id.card);
            img1 = itemView.findViewById(R.id.img1);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productcategory_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);
        holder.img.setTag(position);
        holder.img1.setTag(position);
        holder.txt_title.setTag(position);
        holder.txt_instock.setTag(position);
        holder.switch1.setTag(position);
       // Glide.with(context).load(data.get(position).getPhoto()).into(holder.img);
        Glide.with(context).load(data.get(position).getImg()).placeholder(R.drawable.logo).dontAnimate().into(holder.img);
        Glide.with(context).load(data.get(position).getImg()).placeholder(R.drawable.logo).dontAnimate().into(holder.img1);
        holder.txt_title.setText(data.get(position).getTitle());
        holder.txt_count.setText("1 piece");

        if(data.get(position).getDiscount().equals("")){
            holder.txt_price.setText("\u20B9" +data.get(position).getPrice().toString());
        } else if(data.get(position).getDiscount().equals("0")){
            holder.txt_price.setText("\u20B9" +data.get(position).getPrice().toString());
        }else{
            holder.txt_price.setText("\u20B9" +data.get(position).getDiscount().toString());
        }
        holder.txt_dprice.setText("\u20B9" +data.get(position).getPrice().toString());
        holder.txt_dprice.setPaintFlags(holder.txt_dprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        Log.e("Data  ",data.get(position).getPrice());

        if(data.get(position).getStatus().equals("active")){
            holder.switch1.setChecked(true);
            holder.txt_instock.setText("in Stock");
        }else if(data.get(position).getStatus().equals("out")){
            holder.switch1.setChecked(false);
            holder.txt_instock.setTextColor(R.color.red);
            holder.txt_instock.setText("out of stock");
        }else if(data.get(position).getStatus().equals("inactive")){
            holder.switch1.setChecked(false);
            holder.txt_instock.setTextColor(R.color.red);
            holder.txt_instock.setText("hidden");
        }

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.img_edit);

                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        //Toast.makeText(context, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        if(menuItem.getTitle().equals("Delete product")){
                            bottomdilog(data.get(position).getId(),position);
                        }else if(menuItem.getTitle().equals("Move to top")){
                            swapeItem(position,0);
                        }else if(menuItem.getTitle().equals("Edit")){
                            sessionManager.setPreferences(context,"p_id",data.get(position).getId());
                            context.startActivity(new Intent(context, EditProductActivity.class));
                        }

                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });
        holder.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true) {
                    getUpdateStaus2(data.get(position).getId(),position,holder, "active");
                }else if (isChecked==false) {
                    Log.e("1","1");
                    statusUpdate(data.get(position).getId(),position,holder);

                }
            }
        });

        holder.txt_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(data.get(position).getTitle(),data.get(position).getDiscount(),data.get(position).getSlug(),holder);
            }
        });

        holder.li_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setPreferences(context,"p_id",data.get(position).getId());
                context.startActivity(new Intent(context, EditProductActivity.class));
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
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
                dialog.cancel();
            }
        });
        li_outofStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
               getUpdateStaus(id,pos,holder,"out");
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

    private void getUpdateStaus(String id, int pos, MyViewHolder holder,String out){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",id)
                .addBodyParameter("item_type","Product")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("status",out)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("200")){
                                holder.txt_instock.setText("Out of Stock");
                                Log.e("Responce  ",response+"");
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

    private void getUpdateStaus1(String id, int pos, MyViewHolder holder){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",id)
                .addBodyParameter("item_type","Product")
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
                                holder.txt_instock.setText("Hidden");
                                Log.e("Responce1  ",response+"");
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

    private void getUpdateStaus2(String id, int pos,MyViewHolder holder,String Status){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id",id)
                .addBodyParameter("item_type","Product")
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("status",Status)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("200")){
                                holder.txt_instock.setText("in Stock");
                                Log.e("Responce2  ",response+"");
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

    private  void share(String title, String price, String slug, MyViewHolder holder) {
        holder.card.setDrawingCacheEnabled(true);
        holder.card.buildDrawingCache();
        Bitmap b = holder.card.getDrawingCache();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), b, "Title", null);
        Uri imageUri =  Uri.parse(path);
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        String shareBody = "Item Name: "+title+"\n" +
                "\n" +
                "Price: ₹"+price+" \n" +
                "\n" +
                "Place your order here "+sessionManager.getPreferences(context, "d_url")+"/"+"product"+"/"+slug+". \n" +
                "\n" +
                "Feel free to call us on +91-"+sessionManager.getPreferences(context, "phone_number")+" if you need any help with ordering online.\n" +
                "\n" +
                "Thank you\n" +
                sessionManager.getPreferences(context, "b_name");
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hii");
        intent.putExtra(Intent.EXTRA_STREAM, imageUri);
        intent.setType("image/jpeg");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        context.startActivity(Intent.createChooser(intent, "Webzon"));
    }



}
