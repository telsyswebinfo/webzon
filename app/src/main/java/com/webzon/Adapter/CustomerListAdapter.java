package com.webzon.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.webzon.Activity.Manage.MyCustomersActivity;
import com.webzon.Model.CustomerListModel;

import java.util.ArrayList;

import webzon.R;

public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.MyViewHolder> {
    private ArrayList<CustomerListModel> data;
    public static CardView cardview_cat;
    Context context;
    MyCustomersActivity baseActivity;
    int type;




    public CustomerListAdapter(ArrayList<CustomerListModel> data, MyCustomersActivity mContext) {
        this.data = data;
        this.context = context;
        this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name,txt_address,txt_order,txt_sale;


        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_address = itemView.findViewById(R.id.txt_address);
            txt_order = itemView.findViewById(R.id.txt_order);
            txt_sale = itemView.findViewById(R.id.txt_sale);
         }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        TextView txt_name,txt_address,txt_order,txt_sale;
        holder.txt_name.setText(data.get(position).getName());
        holder.txt_address.setText(data.get(position).getAddress());
        holder.txt_order.setText("TOTAL ORDERS\n"+data.get(position).getPrice());
        holder.txt_sale.setText("TOTAL SALES\n"+data.get(position).getTotal());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}
