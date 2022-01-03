package com.webzon.Adapter;

import static com.webzon.Activity.WebzonPlanActivity.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.webzon.R;

public class WebzonPlanAdapter extends RecyclerView.Adapter<WebzonPlanAdapter.MyViewHolder> {

    Context context;

    public WebzonPlanAdapter(Context context) {

         this.context = context;
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder {
        public  LinearLayout linearLite;
        public  LinearLayout linearPremium;
        public MyViewHolder(View view) {
            super(view);
            linearLite = view.findViewById(R.id.linearLite);
            linearPremium = view.findViewById(R.id.linearPremium);

        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.webzonplanitems, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
       if (Type==true){
           holder.linearPremium.setBackgroundResource(R.color.bg_grey);
           holder.linearLite.setBackgroundResource(R.color.white);
       }else {
           holder.linearPremium.setBackgroundResource(R.color.white);
           holder.linearLite.setBackgroundResource(R.color.bg_grey);
       }
    }

    @Override
    public int getItemCount() {
        return 15;
    }



}
