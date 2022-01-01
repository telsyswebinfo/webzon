package com.webzon.Activity.Manage;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.BusinesCardAdapter;
import com.webzon.Model.PromotionalListModel;
import com.webzon.carousellayoutmanager.CarouselLayoutManager;
import com.webzon.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.webzon.carousellayoutmanager.CenterScrollListener;
import com.webzon.carousellayoutmanager.DefaultChildSelectionListener;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.Layout_to_Image;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;


public class BusinesCardActivity extends CustomActivity {
    SessionManager sessionManager = new SessionManager();
    // @BindView(R.id.rev_bCard) RecyclerView rev_bCard;
    @BindView(R.id.list_vertical) RecyclerView list_vertical;
    @BindView(R.id.btn_edittext) Button btn_edittext;
    @BindView(R.id.btn_share) Button btn_share;
    @BindView(R.id.img) ImageView img;
    @BindView(R.id.txt_title) TextView txt_title;
    @BindView(R.id.cardview) CardView cardview;
    @BindView(R.id.li_share) RelativeLayout li_share;
    @BindView(R.id.lay_nodata) LinearLayout lay_nodata;
    @BindView(R.id.li_button) LinearLayout li_button;
    ArrayList<PromotionalListModel> list =new ArrayList<>();
    PromotionalListModel productlist;
    BusinesCardAdapter businesCardAdapter;
    BusinesCardActivity baseActivity;
    Layout_to_Image layout_to_image;
    private String mCurrentPhotoPath;
    int pos=0;
    Bitmap bitmap;
    Bitmap mImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busines_card);
        ButterKnife.bind(this);
        list_vertical.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        list_vertical.setHasFixedSize(true);
        getPromotionalList();
        setupActionBar(sessionManager.getPreferences(BusinesCardActivity.this,"promotional_title"),true);
        try{
            initRecyclerView(list_vertical, new CarouselLayoutManager(CarouselLayoutManager.HORIZONTAL, false), businesCardAdapter);
        }catch (Exception e){

        }

        btn_edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    bottomdilog(pos);
                }catch (IndexOutOfBoundsException e){e.printStackTrace();}
            }
        });
        verifyStoragePermission(BusinesCardActivity.this);
        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(BusinesCardActivity.this, "Hello", Toast.LENGTH_SHORT).show();
                //takeScreenShot(getWindow().getDecorView());
                shareImage();
            }
        });

    }


    private void getPromotionalList(){
        list.clear();
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.PromotionalDetails)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("promotional_id",sessionManager.getPreferences(this,"promotional_id"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(BusinesCardActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                JSONObject object = new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ",jsonArray.toString());
                               /* PromotionalListModel productlist;
                                BusinesCardAdapter businesCardAdapter;*/
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    productlist = new PromotionalListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    productlist.setId(jsonObject.getString("id"));
                                    productlist.setTitle(jsonObject.getString("title"));
                                    productlist.setPhoto(jsonObject.getString("photo"));
                                    list.add(productlist);
                                }
                                try{
                                    if(list.size()>0){
                                        lay_nodata.setVisibility(View.GONE);
                                        li_button.setVisibility(View.VISIBLE);
                                        list_vertical.setVisibility(View.VISIBLE);
                                        Log.e("Data ",list.size()+"");
                                        businesCardAdapter = new BusinesCardAdapter(list, baseActivity);
                                        list_vertical.setAdapter(businesCardAdapter);
                                    }else{
                                        lay_nodata.setVisibility(View.VISIBLE);
                                        li_button.setVisibility(View.GONE);
                                        list_vertical.setVisibility(View.GONE);
                                    }

                                }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(BusinesCardActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                lay_nodata.setVisibility(View.VISIBLE);
                                li_button.setVisibility(View.GONE);
                                list_vertical.setVisibility(View.GONE);
                                // SnackBar.returnFlashBar(BusinesCardActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(BusinesCardActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void getPromotionalList1(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.PromotionalDetails)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("promotional_id",sessionManager.getPreferences(this,"promotional_id"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(BusinesCardActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                JSONObject object = new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data  ",jsonArray.toString());
                               /* PromotionalListModel productlist;
                                BusinesCardAdapter businesCardAdapter;*/
                                for (int i =0 ; i<jsonArray.length(); i++){
                                    productlist = new PromotionalListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    productlist.setId(jsonObject.getString("id"));
                                    productlist.setTitle(jsonObject.getString("title")+"1");
                                    productlist.setPhoto(jsonObject.getString("photo"));
                                    list.add(productlist);
                                }
                                try{
                                    if(list.size()>0){
                                        Log.e("Data ",list.size()+"");
                                        businesCardAdapter = new BusinesCardAdapter(list, baseActivity);
                                        list_vertical.setAdapter(businesCardAdapter);
                                    }
                                }catch (NullPointerException e){e.printStackTrace();}
                            }else{
                                SnackBar.returnFlashBar(BusinesCardActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(BusinesCardActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void initRecyclerView(final RecyclerView recyclerView, final CarouselLayoutManager layoutManager, final BusinesCardAdapter adapter) {
        // enable zoom effect. this line can be customized
        layoutManager.setPostLayoutListener(new CarouselZoomPostLayoutListener());
        layoutManager.setMaxVisibleItems(3);

        recyclerView.setLayoutManager(layoutManager);
        // we expect only fixed sized item for now
        recyclerView.setHasFixedSize(true);
        // sample adapter with random data
        recyclerView.setAdapter(adapter);
        // enable center post scrolling
        recyclerView.addOnScrollListener(new CenterScrollListener());
        // enable center post touching on item and item click listener
        DefaultChildSelectionListener.initCenterItemListener(new DefaultChildSelectionListener.OnCenterItemClickListener() {
            @Override
            public void onCenterItemClicked(@NonNull final RecyclerView recyclerView, @NonNull final CarouselLayoutManager carouselLayoutManager, @NonNull final View v) {
                final int position = recyclerView.getChildLayoutPosition(v);
                final String msg = String.format(Locale.US, "Item %1$d was clicked", position);
                // Toast.makeText(BusinesCardActivity.this, msg, Toast.LENGTH_SHORT).show();

                Log.e("pos ",pos+"");
            }
        }, recyclerView, layoutManager);

        layoutManager.addOnItemSelectionListener(new CarouselLayoutManager.OnCenterItemSelectionListener() {

            @Override
            public void onCenterItemChanged(final int adapterPosition) {
                if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                    Log.e("Pos122 ",adapterPosition+"");
                    pos=adapterPosition;
                    Glide.with(BusinesCardActivity.this).load(list.get(pos).getPhoto()).into(img);
                    txt_title.setText(list.get(pos).getTitle());
                    if(pos==1){
                       /* if(list.size()!=4){
                            getPromotionalList1();
                        }*/

                    }
                    /*list.get(pos).setTitle();
                    adapter.notifyItemInserted(pos);*/
                    // final int value = adapter.mPosition[adapterPosition];
/*
                    adapter.mPosition[adapterPosition] = (value % 10) + (value / 10 + 1) * 10;
                    adapter.notifyItemChanged(adapterPosition);
*/
                }
            }
        });
    }

    public void bottomdilog(int pos) {
        ImageView close;
        LinearLayout li_rej;
        TextInputEditText txt_cate;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.card_design_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        txt_cate = view.findViewById(R.id.txt_cate);
        close = view.findViewById(R.id.close);
        li_rej = view.findViewById(R.id.li_rej);

        txt_cate.setText(list.get(pos).getTitle());
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        li_rej.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                list.get(pos).setTitle(txt_cate.getText().toString());
                txt_title.setText(list.get(pos).getTitle());
                businesCardAdapter.notifyItemChanged(pos);
            }
        });
    }




    //Permissions Check

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }

    private void shareImage() {
        try {
            cardview.setDrawingCacheEnabled(true);
            cardview.buildDrawingCache();
            Bitmap b = cardview.getDrawingCache();
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/jpeg");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(getContentResolver(),
                    b, "Title", null);
            Uri imageUri =  Uri.parse(path);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("Error ",e.toString());
        }
    }

    public static Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap( v.getLayoutParams().width, v.getLayoutParams().height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

}
