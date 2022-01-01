package com.webzon.Activity.Account;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Map.LocationPickerActivity;
import com.webzon.Map.MapUtility;
import com.webzon.Model.categoryShopListModel;
import com.webzon.Model.unitListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import webzon.R;

public class EditBusinessActivity extends CustomActivity {
    @BindView(R.id.li_img) LinearLayout li_img;
    @BindView(R.id.img_bus) ImageView img_bus;
    @BindView(R.id.et_stlink) TextInputEditText et_stlink;
    @BindView(R.id.et_bname) TextInputEditText et_bname;
    @BindView(R.id.et_bcategory) TextInputEditText et_bcategory;
    @BindView(R.id.et_phonenumber) TextInputEditText et_phonenumber;
    @BindView(R.id.et_address) TextInputEditText et_address;
    @BindView(R.id.btn_continue) Button btn_continue;
    @BindView(R.id.txt_photo) TextView txt_photo;
    @BindView(R.id.swipe) SwipeRefreshLayout swipe;


    private Context getContext;
    SessionManager sessionManager = new SessionManager();
    Bitmap mImageBitmap;
    File FirstImage,FirstFirstImage;
    private String mCurrentPhotoPath,spinnervalue="";
    private static final int ADDRESS_PICKER_REQUEST = 1020;

    private ArrayList<categoryShopListModel.Data.Datum> data2;
    private ArrayList<unitListModel.Data.Datum> unitData;
    static final int RESULT_LOAD_IMAGE1 = 9;
    static final int REQUEST_IMAGE_CAPTURE8 = 8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business);
        ButterKnife.bind(this);
        setupActionBar("Edit Business",true);
        Glide.with(this).load(sessionManager.getPreferences(EditBusinessActivity.this, "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(img_bus);
        et_stlink.setText(sessionManager.getPreferences(EditBusinessActivity.this, "d_url"));
        et_bname.setText(sessionManager.getPreferences(EditBusinessActivity.this, "b_name"));
        et_phonenumber.setText(sessionManager.getPreferences(EditBusinessActivity.this, "phone_number"));
        et_address.setText(sessionManager.getPreferences(EditBusinessActivity.this, "address"));
        et_bcategory.setText(sessionManager.getPreferences(EditBusinessActivity.this, "b_category"));

        li_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(EditBusinessActivity.this);
            }
        });
        txt_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(EditBusinessActivity.this);
            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Glide.with(EditBusinessActivity.this).load(sessionManager.getPreferences(EditBusinessActivity.this, "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(img_bus);
                et_stlink.setText(sessionManager.getPreferences(EditBusinessActivity.this, "d_url"));
                et_bname.setText(sessionManager.getPreferences(EditBusinessActivity.this, "b_name"));
                et_phonenumber.setText(sessionManager.getPreferences(EditBusinessActivity.this, "phone_number"));
                et_address.setText(sessionManager.getPreferences(EditBusinessActivity.this, "address"));
                et_bcategory.setText(sessionManager.getPreferences(EditBusinessActivity.this, "b_category"));
                swipe.setRefreshing(false);
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditBusinessActivity.this, LocationPickerActivity.class);
                startActivityForResult(intent, ADDRESS_PICKER_REQUEST);
            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_bname.getText().toString().equals("")){
                    et_bname.setError("Please Enter Business Name");
                }
                else{
                  //  getupdateBussiness();
                    apiData();
                }

            }
        });
    }


    private void imageUpload() {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // Gson gson = new Gson();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", sessionManager.getPreferences(EditBusinessActivity.this,"user_id"));
        builder.addFormDataPart("upload_type", "business");
        builder.addFormDataPart("device_id", StaticVariables.DeviceID);
        builder.addFormDataPart("device_token", StaticVariables.Token);
        builder.addFormDataPart("device_type", StaticVariables.DeviceType);
        if (FirstFirstImage != null) {
            builder.addFormDataPart("photo", FirstFirstImage.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), FirstFirstImage));
        } else {
            builder.addFormDataPart("photo", "", RequestBody.create(MediaType.parse("multipart/form-data"), ""));
        }
        // builder.addFormDataPart("photo", String.valueOf(FirstFirstImage));
        MultipartBody body = builder.build();
        final Request request = new Request.Builder()
                .url(ApiUrl.BaseUrl+"api/storeImage")
                .post(body)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.retryOnConnectionFailure();
        okHttpClient.connectTimeoutMillis();
        okHttpClient.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.d("failure", "fails");
            }

            @Override
            public void onResponse(okhttp3.Call call, final okhttp3.Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String apidata = null;
                        try {
                            apidata = response.body().string();
                            JSONObject object = new JSONObject(apidata);
                            if (response.isSuccessful()) {
                                object.getString("data");
                                Log.e("Image url ",apidata+"");
                                //imgUrl.add(object.getString("data"));
                                sessionManager.setPreferences(EditBusinessActivity.this, "image", object.getString("data"));
                                Glide.with(EditBusinessActivity.this).load(sessionManager.getPreferences(EditBusinessActivity.this, "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(img_bus);
                            }
                        } catch (IOException | JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("responce>>>>>", apidata);

                    }
                });

            }
        });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");

        builder.setItems(options, new DialogInterface.OnClickListener() {

            private static final String TAG = "tag";

            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (options[item].equals("Take Photo")) {
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePicture.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            Log.i(TAG, "IOException");
                        }

                        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                        StrictMode.setVmPolicy(builder.build());
                        builder.detectFileUriExposure();
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            takePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            startActivityForResult(takePicture, REQUEST_IMAGE_CAPTURE8);

                        }
                    }

                } else if (options[item].equals("Choose from Gallery")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , RESULT_LOAD_IMAGE1);

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public File bitmapToFile(Bitmap bmp) {
        try {
            int size = 4000;
            String name = System.currentTimeMillis() + ".jpeg";
            ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = openFileOutput(name, Context.MODE_APPEND);
            fos.write(bArr);
            fos.flush();
            fos.close();

            File mFile = new File(getFilesDir().getAbsolutePath(), name);
            return mFile;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public File saveBitmapToFile(File file) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 75;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);

            return file;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE8) {
                try {
                     mImageBitmap= (MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath)));
                    FirstImage = bitmapToFile(mImageBitmap);
                    FirstFirstImage = saveBitmapToFile(FirstImage);

                    imageUpload();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (requestCode == RESULT_LOAD_IMAGE1) {
                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage,filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();
                mImageBitmap= (BitmapFactory.decodeFile(picturePath));
                FirstImage = bitmapToFile(mImageBitmap);
                FirstFirstImage = saveBitmapToFile(FirstImage);
                imageUpload();



            }

        }
        if (requestCode == ADDRESS_PICKER_REQUEST) {
            try {
                if (data != null && data.getStringExtra(MapUtility.ADDRESS) != null) {
                    // String address = data.getStringExtra(MapUtility.ADDRESS);
                    double currentLatitude = data.getDoubleExtra(MapUtility.LATITUDE, 0.0);
                    double currentLongitude = data.getDoubleExtra(MapUtility.LONGITUDE, 0.0);
                    Bundle completeAddress =data.getBundleExtra("fullAddress");
                    et_address.setText(new StringBuilder().append(completeAddress.getString("addressline2")));
                    StaticVariables.Address= String.valueOf((new StringBuilder().append(completeAddress.getString("addressline2"))));


                    StaticVariables.Lat=String.valueOf(currentLatitude);
                    StaticVariables.Lng=String.valueOf(currentLongitude);
                 /*   txtLatLong.setText(new StringBuilder().append("Lat:").append(currentLatitude).append
                            ("  Long:").append(currentLongitude).toString());
*/
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private void apiData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("name", et_bname.getText().toString());
        hashMap.put("category_id", sessionManager.getPreferences(this,"c_id"));
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("address", et_address.getText().toString());
        hashMap.put("lat", StaticVariables.Lat);
        hashMap.put("lng", StaticVariables.Lng);
        hashMap.put("photo", sessionManager.getPreferences(EditBusinessActivity.this, "image"));

        WebServiceHandler webServiceHandler = new WebServiceHandler(EditBusinessActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        sessionManager.setPreferences(EditBusinessActivity.this, "b_name",et_bname.getText().toString());
                        sessionManager.setPreferences(EditBusinessActivity.this, "address",et_address.getText().toString());
                        SnackBar.returnFlashBar(EditBusinessActivity.this,jsonObect.getString("message"));
                        onBackPressed();
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(EditBusinessActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        SnackBar.returnFlashBar(EditBusinessActivity.this,"message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.updateBussiness);
    }

}