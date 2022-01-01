package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Model.ProductList;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.productListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import webzon.R;

public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyViewHolder> {
    private ArrayList<productListModel.Product.Datum> data;
    Context context;
    HomeActivity2 baseActivity;
    SessionManager sessionManager = new SessionManager();
     int type;




    public OrderProductAdapter(ArrayList<productListModel.Product.Datum> data, HomeActivity2 mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img,img_edit;
        TextView txt_title,txt_count,txt_instock,txt_price,txt_dprice;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img_edit = itemView.findViewById(R.id.img_edit);
            img = itemView.findViewById(R.id.img);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_instock = itemView.findViewById(R.id.txt_instock);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_dprice = itemView.findViewById(R.id.txt_dprice);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.productcategory_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       // Glide.with(context).load(data.get(position).getPhoto()).into(holder.img);
        Glide.with(context).load(data.get(position).getPhoto()).placeholder(R.drawable.logo).dontAnimate().into(holder.img);
        holder.txt_title.setText(data.get(position).getTitle());
        holder.txt_count.setText("1 piece");

        holder.txt_price.setText( "\u20B9" +data.get(position).getDiscount().toString());
        holder.txt_dprice.setText( "\u20B9" +data.get(position).getPrice().toString());
        holder.txt_dprice.setPaintFlags(holder.txt_dprice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.img_edit);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if(menuItem.getTitle().equals("Delete product")){
                          //  bottomdilog(data.get(position).get);
                        }
                       // Toast.makeText(context, "You Clicked " + menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
                // Showing the popup menu
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    private void getStatusUpdate(){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.StatusUpdate)
                .addBodyParameter("user_id",sessionManager.getPreferences(context,"user_id"))
                .addBodyParameter("item_id", StaticVariables.DeviceID)
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
                                data.remove(0);
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
    private void bottomdilog() {
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
                getStatusUpdate();
            }
        });
    }

}
