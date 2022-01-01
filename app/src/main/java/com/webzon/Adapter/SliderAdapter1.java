package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Model.GetSliderModel;

import java.util.ArrayList;

import com.webzon.R;

public class SliderAdapter1 extends RecyclerView.Adapter<SliderAdapter1.MyViewHolder> {
    private ArrayList<GetSliderModel.Data> data;
    Context context;
    HomeActivity2 baseActivity;
     int type;




    public SliderAdapter1(ArrayList<GetSliderModel.Data> data, HomeActivity2 mContext) {
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
        Glide.with(context).load(data.get(position).getImage()).into(holder.img_slider);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
