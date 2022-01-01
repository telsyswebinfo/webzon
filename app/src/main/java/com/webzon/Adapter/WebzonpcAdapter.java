package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Login.EnterBNameActivity;
import com.webzon.Model.CategoryModel;
import com.webzon.R;

import java.util.ArrayList;

public class WebzonpcAdapter extends RecyclerView.Adapter<WebzonpcAdapter.MyViewHolder> {

    Context context;

    public WebzonpcAdapter(Context context) {

         this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View view) {
            super(view);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.webzonpcitems, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

    }

    @Override
    public int getItemCount() {
        return 15;
    }



}
