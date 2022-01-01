package com.webzon.Activity.Product;

import androidx.appcompat.app.AppCompatActivity;

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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.webzon.Activity.DilogActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import com.webzon.R;

public class EditCategoryActivity extends AppCompatActivity {
    SessionManager sessionManager = new SessionManager();
    File FirstImage,FirstFirstImage;
    Bitmap mImageBitmap;
    private String mCurrentPhotoPath,spinnervalue="";
    static final int RESULT_LOAD_IMAGE1 = 9;
    static final int REQUEST_IMAGE_CAPTURE8 = 8;
    String imgUrl;
    @BindView(R.id.txt_cate) TextView txt_cate;
    @BindView(R.id.img_bus) ImageView img_bus;
    @BindView(R.id.btn_create) Button btn_create;
    @BindView(R.id.li_img) LinearLayout li_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);
        ButterKnife.bind(this);
        getproductList1();

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_cate.getText().toString().equals("")){
                    getCreateCategory();
                }else{
                    SnackBar.returnFlashBar(EditCategoryActivity.this,"Please Enter Product Category");
                }
            }
        });
        li_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage(EditCategoryActivity.this);
            }
        });
    }

    private void getproductList1(){
        Utlity.show_progress(this);
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.CategoryDetails)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("category_id",sessionManager.getPreferences(this, "cat_id"))
                .addBodyParameter("type","shop")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data11  ",response.toString());
                        Utlity.dismiss_dilog(EditCategoryActivity.this);
                        try {
                            if (response.getString("status").equals("200")) {
                                imgUrl = response.getJSONObject("data").getString("photo");
                                Glide.with(EditCategoryActivity.this).load(response.getJSONObject("data").getString("photo")).placeholder(R.drawable.logo).dontAnimate().into(img_bus);
                                txt_cate.setText(response.getJSONObject("data").getString("title"));
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(EditCategoryActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(EditCategoryActivity.this,response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(EditCategoryActivity.this);
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    private void imageUpload() {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // Gson gson = new Gson();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", sessionManager.getPreferences(EditCategoryActivity.this,"user_id"));
        builder.addFormDataPart("upload_type", "category");
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
                .url(ApiUrl.BaseUrl +"api/storeImage")
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
                                imgUrl = object.getString("data");
                                Glide.with(EditCategoryActivity.this).load(imgUrl).placeholder(R.drawable.logo).dontAnimate().into(img_bus);
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
    }


    private void getCreateCategory(){

        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.EditCategory)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("title",txt_cate.getText().toString())
                .addBodyParameter("type","shop")
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("photo",imgUrl)
                .addBodyParameter("cat_id",sessionManager.getPreferences(this, "cat_id"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.e("Data  ",response.toString());
                        try {
                            if(response.getString("status").equals("200")){
                                SnackBar.returnFlashBar(EditCategoryActivity.this, response.getString("message"));
                                onBackPressed();
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(EditCategoryActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}