package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webzon.Activity.Product.EditProductActivity;
import com.webzon.Model.categoryShopListModel;

import java.util.ArrayList;

import webzon.R;

public class ChooseCAtAdapter1 extends RecyclerView.Adapter<ChooseCAtAdapter1.MyViewHolder> {
    private ArrayList<categoryShopListModel.Data.Datum> data;
    public static CardView cardview_cat;
    Context context;
    EditProductActivity baseActivity;
    int type;




    public ChooseCAtAdapter1(ArrayList<categoryShopListModel.Data.Datum> data, EditProductActivity mContext) {
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
            cardview_cat = itemView.findViewById(R.id.cardview_cat);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.choosecat_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.txt_cat.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
