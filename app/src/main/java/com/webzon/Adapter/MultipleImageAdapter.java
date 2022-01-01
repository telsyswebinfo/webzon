package com.webzon.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import webzon.R;

public class MultipleImageAdapter extends RecyclerView.Adapter<MultipleImageAdapter.MyViewHolder> {
    ArrayList<Uri> mArrayUri;
    Context context;
    ArrayList<Bitmap> bitmapArray;
    public MultipleImageAdapter(ArrayList<Bitmap> bitmapArray,Context context) {
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
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.images_items, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        holder.img.setImageBitmap(bitmapArray.get(position));
        holder.img_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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



}
