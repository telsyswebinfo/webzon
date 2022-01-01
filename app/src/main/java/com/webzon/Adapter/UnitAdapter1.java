package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.webzon.Activity.Product.EditProductActivity;
import com.webzon.Model.unitListModel;

import java.util.ArrayList;

import com.webzon.R;

public class UnitAdapter1 extends RecyclerView.Adapter<UnitAdapter1.MyViewHolder> {
    private ArrayList<unitListModel.Data.Datum> data;
    public static CardView cardview_cat;
    Context context;
    EditProductActivity baseActivity;
    int type;




    public UnitAdapter1(ArrayList<unitListModel.Data.Datum> data, EditProductActivity mContext) {
        this.data = data;
        this.context = context;
        this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        Chip ch_id;


        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            ch_id = itemView.findViewById(R.id.ch_id);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.unit_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.ch_id.setText(data.get(position).getFullname());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
