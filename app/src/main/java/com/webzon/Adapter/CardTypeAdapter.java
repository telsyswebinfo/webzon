package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Manage.MarketingsActivity;
import com.webzon.Model.PromotionalListModel;

import java.util.ArrayList;

import com.webzon.R;

public class CardTypeAdapter extends RecyclerView.Adapter<CardTypeAdapter.MyViewHolder> {

    private ArrayList<PromotionalListModel> data;
    Context context;
    MarketingsActivity baseActivity;
     int type;




    public CardTypeAdapter(ArrayList<PromotionalListModel> data, MarketingsActivity mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_title;
        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.img);
            txt_title = itemView.findViewById(R.id.txt_title);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.citem_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(data.get(position).getPhoto()).into(holder.img);
        holder.txt_title.setText(data.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
