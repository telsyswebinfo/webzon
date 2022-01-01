package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.webzon.Activity.Login.EnterBNameActivity;
import com.webzon.utils.productListModel;

import java.util.ArrayList;

import webzon.R;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {
    private ArrayList<productListModel.Category> data;
    Context context;
    EnterBNameActivity baseActivity;
     int type;




    public ProductListAdapter(ArrayList<productListModel.Category> data, EnterBNameActivity mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_cat;
        TextView txt_cat;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img_cat = itemView.findViewById(R.id.img_cat);
            txt_cat = itemView.findViewById(R.id.txt_cat);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
    /*    Glide.with(context).load(data.get(position).getPhoto()).into(holder.img_cat);
        holder.txt_cat.setText(data.get(position).getTitle());*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
