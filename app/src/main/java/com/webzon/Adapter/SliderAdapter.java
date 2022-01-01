package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Model.BannerModel;
import com.webzon.Model.GetSliderModel;
import com.webzon.Model.homePageDataModel;


import java.util.ArrayList;

import webzon.R;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.MyViewHolder> {
    private ArrayList<BannerModel> data;
    Context context;
    HomeActivity2 baseActivity;
     int type;




    public SliderAdapter(ArrayList<BannerModel> data, HomeActivity2 mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_slider;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img_slider = itemView.findViewById(R.id.img_slider);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_slider, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getPhoto()).into(holder.img_slider);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
