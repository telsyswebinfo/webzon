package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Fragment.OrderFragment;
import com.webzon.Model.orderListModel;

import java.util.ArrayList;

import webzon.R;

public class OrderTypeListAdapter extends RecyclerView.Adapter<OrderTypeListAdapter.MyViewHolder> {
    private ArrayList<orderListModel> data;
    Context context;
    OrderFragment baseActivity;
     int type;




 /*   public OrderTypeListAdapter(ArrayList<orderListModel> data, HomeActivity2 mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }*/

    public OrderTypeListAdapter(ArrayList<orderListModel> data, OrderFragment mContext) {
        this.data = data;
        this.context = context;
        this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_orderid,txt_Ptype,txt_date,txt_count,txt_amt,order_Status;
        ImageView img,img_status;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txt_orderid = itemView.findViewById(R.id.txt_orderid);
            txt_Ptype = itemView.findViewById(R.id.txt_Ptype);
            txt_date = itemView.findViewById(R.id.txt_date);
            txt_count = itemView.findViewById(R.id.txt_count);
            txt_amt = itemView.findViewById(R.id.txt_amt);
            order_Status = itemView.findViewById(R.id.order_Status);
            img = itemView.findViewById(R.id.img);
            img_status = itemView.findViewById(R.id.img_status);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderlist_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        Glide.with(context).load(data.get(position).getImage()).placeholder(R.drawable.logo).dontAnimate().into(holder.img);
        holder.txt_orderid.setText("Order ID: "+data.get(position).getOrder_no());
        holder.txt_Ptype.setText(data.get(position).getPayment_method());
        holder.txt_date.setText(data.get(position).getOrder_time());
        holder.txt_count.setText(data.get(position).getOrder_items());
        holder.txt_amt.setText(data.get(position).getCurrency()+data.get(position).getGrand_total());

        if(data.get(position).getStatus().equals("PN")){
            holder.img_status.setBackgroundResource(R.drawable.yellow_dot);
            holder.order_Status.setText("Order Pending");
        }else if(data.get(position).getStatus().equals("AC")){
            holder.img_status.setBackgroundResource(R.drawable.green_dot);
            holder.order_Status.setText("Order Accepted");
        }else if(data.get(position).getStatus().equals("SP")){
            holder.img_status.setBackgroundResource(R.drawable.blue_dot);
            holder.order_Status.setText("Order Shipped");
        }else if(data.get(position).getStatus().equals("DL")){
            holder.img_status.setBackgroundResource(R.drawable.blue_dot);
            holder.order_Status.setText("Order Delivered");
        }else if(data.get(position).getStatus().equals("CL")){
            holder.img_status.setBackgroundResource(R.drawable.red_dot);
            holder.order_Status.setText("Order Cancelled");
        }else if(data.get(position).getStatus().equals("RN")){
            holder.img_status.setBackgroundResource(R.drawable.red_dot);
            holder.order_Status.setText("Order Rejected");
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
