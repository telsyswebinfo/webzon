package com.webzon.Adapter;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.webzon.Activity.Manage.BusinesCardActivity;
import com.webzon.Model.PromotionalListModel;
import com.webzon.utils.Layout_to_Image;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import webzon.R;

public class BusinesCardAdapter extends RecyclerView.Adapter<BusinesCardAdapter.MyViewHolder> {
    Layout_to_Image layout_to_image;
    LinearLayout layout_raw;
    Bitmap bitmap;

    ImageView imageView;
    private ArrayList<PromotionalListModel> data;
    Context context;
    BusinesCardActivity baseActivity;
     int type;




    public BusinesCardAdapter(ArrayList<PromotionalListModel> data, BusinesCardActivity mContext) {
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
            layout_raw = itemView.findViewById(R.id.layout_raw);
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        Glide.with(context).load(data.get(position).getPhoto()).into(holder.img);
        holder.txt_title.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void share(){
        try {
            layout_to_image=new Layout_to_Image(context,layout_raw);
            bitmap=layout_to_image.convert_layout();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            context.startActivity(Intent.createChooser(share, "Select"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No App Available", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("Error ",e.toString());
        }
    }

}
