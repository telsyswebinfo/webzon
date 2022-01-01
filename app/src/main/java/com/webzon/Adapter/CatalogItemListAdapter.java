package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Product.CatalogItemListActivity;
import com.webzon.Model.CatLogProductListModel;

import java.util.ArrayList;

import com.webzon.R;

public class CatalogItemListAdapter extends RecyclerView.Adapter<CatalogItemListAdapter.MyViewHolder> {
    private ArrayList<CatLogProductListModel> data;
    Context context;
    CatalogItemListActivity baseActivity;
     int type;


    public CatalogItemListAdapter(ArrayList<CatLogProductListModel> data, CatalogItemListActivity mContext) {
         this.data = data;
         this.context = context;
         this.baseActivity = mContext;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txt_title,txt_price;
        CheckBox chkbox;
        LinearLayout li_raw;

        public MyViewHolder(View view) {
            super(view);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.img);
            txt_title = itemView.findViewById(R.id.txt_title);
            txt_price = itemView.findViewById(R.id.txt_price);
            chkbox = itemView.findViewById(R.id.chkbox);
            li_raw = itemView.findViewById(R.id.li_raw);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.catalogitenlist_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
      /*  if(position==0){
            holder.viewtop.setVisibility(View.VISIBLE);
        }else{
            holder.viewtop.setVisibility(View.GONE);
        }*/
        Glide.with(context).load(data.get(position).getImg()).into(holder.img);
        holder.txt_title.setText(data.get(position).getTitle());
        holder.txt_price.setText("\u20B9 "+data.get(position).getPrice());
        if(data.get(position).getStatus().equals("1")){
            holder.chkbox.setChecked(true);
        }else{
            holder.chkbox.setChecked(false);
        }
        /*holder.li_raw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getStatus().equals("0")){
                    data.get(position).setStatus("1");
                    holder.chkbox.setChecked(true);
                  //  notifyItemInserted(position);
                }else{
                    data.set(position,"0");
                    holder.chkbox.setChecked(false);
                   // notifyItemInserted(position);
                }

            }
        });*/

        holder.chkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    data.get(position).setStatus("1");
                    holder.chkbox.setChecked(true);
                }else{
                    data.get(position).setStatus("0");
                    holder.chkbox.setChecked(false);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public ArrayList<String> arr(){
        ArrayList<String> list =new ArrayList<>();
       for (int i = 0; i <data.size(); i++) {
           String pp =data.get(i).getStatus();
           if(pp.equals("1")) {
               list.add(data.get(i).getId());
           }
       }
       return list;
   }

}
