package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Product.CatalogCategoryActivity;
import com.webzon.Model.CatLogListModel;
import com.webzon.Model.ModelCategory;
import com.webzon.Yplayer.FullScreenLessonPlayerActivity;
import com.webzon.Yplayer.TutorialsActivity;

import java.util.ArrayList;

import com.webzon.R;

public class TutoricalCategoryAdapter extends RecyclerView.Adapter<TutoricalCategoryAdapter.MyViewHolder> {
    private ArrayList<ModelCategory> data;
    Context context;
    TutorialsActivity baseActivity;
     int type;


    public TutoricalCategoryAdapter(ArrayList<ModelCategory> data, TutorialsActivity mContext) {
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
                .inflate(R.layout.tuto_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load("http://img.youtube.com/vi/"+data.get(position).getVid()+"/0.jpg").into(holder.img);
        holder.txt_title.setText(data.get(position).getTitle()+"");
        Log.e("Url  ","http://img.youtube.com/vi/"+data.get(position).getVid()+"/0.jpg");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullScreenLessonPlayerActivity.class);
                intent.putExtra("videoUrl",data.get(position).getYoutube_link());
                context.startActivity(intent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
