package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Product.CatalogCategoryActivity;
import com.webzon.Model.CatLogListModel;

import java.util.ArrayList;
import com.webzon.R;

public class CatalogListAdapter extends RecyclerView.Adapter<CatalogListAdapter.MyViewHolder> {
    private ArrayList<CatLogListModel> data;
    Context context;
    CatalogCategoryActivity baseActivity;
     int type;


    public CatalogListAdapter(ArrayList<CatLogListModel> data, CatalogCategoryActivity mContext) {
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
                .inflate(R.layout.catalog_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position).getPhoto()).into(holder.img);
        holder.txt_title.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
