package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Order.PendingOrderActivity;
import com.webzon.Model.OrderDetailsModel;

import java.util.ArrayList;

import webzon.R;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.MyViewHolder> {
    private ArrayList<OrderDetailsModel> data;
    Context context;
    PendingOrderActivity baseActivity;
     int type;




 /*   public OrderTypeListAdapter(ArrayList<orderListModel> data, HomeActivity2 mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }*/

    public OrderDetailsAdapter(ArrayList<OrderDetailsModel> data, PendingOrderActivity mContext) {
        this.data = data;
        this.context = context;
        this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_p_name,txt_price,txt_price1,txt_name;
        ImageView img;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();

            img = itemView.findViewById(R.id.img);
            txt_p_name = itemView.findViewById(R.id.txt_p_name);
            txt_price = itemView.findViewById(R.id.txt_price);
            txt_price1 = itemView.findViewById(R.id.txt_price1);
            txt_name = itemView.findViewById(R.id.txt_name);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderdetails_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getImage()).placeholder(R.drawable.logo).dontAnimate().into(holder.img);
       // holder.txt_p_name.setText("Order ID: "+data.get(position).getTitle());
        holder.txt_price.setText(data.get(position).getQty()+" X "+data.get(position).getPrice());
        holder.txt_name.setText(data.get(position).getTitle());
        holder.txt_price1.setText(String.valueOf(Integer.parseInt(data.get(position).getQty())*Integer.parseInt(data.get(position).getPrice())));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
