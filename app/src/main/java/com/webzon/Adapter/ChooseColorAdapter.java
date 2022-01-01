package com.webzon.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webzon.Activity.Product.AddVariantsActivity;
import com.webzon.Model.colorListModel;

import java.util.ArrayList;

import com.webzon.R;

public class ChooseColorAdapter extends RecyclerView.Adapter<ChooseColorAdapter.MyViewHolder> {
    private ArrayList<colorListModel> data;
    Context context;
    AddVariantsActivity baseActivity;
    int type;




    public ChooseColorAdapter(ArrayList<colorListModel> data, AddVariantsActivity mContext) {
        this.data = data;
        this.context = context;
        this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_cname;
        CardView cardview_cat;


        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            cardview_cat = itemView.findViewById(R.id.cardview_cat);
            txt_cname = itemView.findViewById(R.id.txt_cname);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.color_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.cardview_cat.setBackgroundColor(Color.parseColor(data.get(position).getCode()));
        holder.txt_cname.setText(data.get(position).getColorname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
