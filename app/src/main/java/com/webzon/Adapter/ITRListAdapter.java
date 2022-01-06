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
import com.webzon.Activity.HomeActivity2;
import com.webzon.Activity.Manage.TaxesActivity;
import com.webzon.Activity.Product.EditProductActivity;
import com.webzon.Model.ModelITRList;
import com.webzon.Model.ProductList;
import com.webzon.R;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;

public class ITRListAdapter extends RecyclerView.Adapter<ITRListAdapter.MyViewHolder> {
    private ArrayList<ModelITRList> data;
    Context context;
    TaxesActivity baseActivity;

    SessionManager sessionManager = new SessionManager();
     int type;

    public ITRListAdapter(ArrayList<ModelITRList> data, TaxesActivity mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView home_tour_rewardView,home_tour_feeView,home_tour_enrollText;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            home_tour_rewardView = itemView.findViewById(R.id.home_tour_rewardView);
            home_tour_feeView = itemView.findViewById(R.id.home_tour_feeView);
            home_tour_enrollText = itemView.findViewById(R.id.home_tour_enrollText);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itr_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.setIsRecyclable(false);
        holder.home_tour_rewardView.setTag(position);
        holder.home_tour_feeView.setTag(position);
        holder.home_tour_enrollText.setTag(position);

        holder.home_tour_rewardView.setText(data.get(position).getFirst_name());
        holder.home_tour_feeView.setText(data.get(position).getMobile_number());
        holder.home_tour_enrollText.setText(data.get(position).getPan_nmuber());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
