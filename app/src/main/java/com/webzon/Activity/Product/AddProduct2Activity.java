package com.webzon.Activity.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.ChooseCAtAdapter;
import com.webzon.Adapter.MultipleImageAdapter;
import com.webzon.Adapter.MultipleImageAdapter1;
import com.webzon.Adapter.UnitAdapter;
import com.webzon.Model.categoryShopListModel;
import com.webzon.Model.createCategoryModel;
import com.webzon.Model.createProductModel;
import com.webzon.Model.unitListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.Consts;
import com.webzon.utils.RecyclerItemClickListener;
import com.webzon.utils.ServerRequest;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONArray;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;
import webzon.R;
import org.json.JSONException;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddProduct2Activity extends AppCompatActivity {
    @BindView(R.id.txt_addVaiants) TextView txt_addVaiants;
    @BindView(R.id.btn_continue) Button btn_continue;
    @BindView(R.id.txt_pName) TextInputEditText txt_pName;
    @BindView(R.id.txt_bCategory) TextInputEditText txt_bCategory;
    @BindView(R.id.txt_price) TextInputEditText txt_price;
    @BindView(R.id.txt_dPrice) TextInputEditText txt_dPrice;
    @BindView(R.id.txt_pUnit) TextInputEditText txt_pUnit;
    @BindView(R.id.txt_unit) TextInputEditText txt_unit;
    @BindView(R.id.txt_pDetails) TextInputEditText txt_pDetails;
    @BindView(R.id.li_bCategory) LinearLayout li_bCategory;
    @BindView(R.id.li_disPrice) LinearLayout li_disPrice;
    @BindView(R.id.proPrice) TextView proPrice;
    @BindView(R.id.txt_per) TextView txt_per;
    @BindView(R.id.li_img) LinearLayout li_img;
    @BindView(R.id.img_bus) ImageView img_bus;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    Bitmap mImageBitmap;
    String status = "0";
    Uri resultUri;
    private MultipleImageAdapter multipleImageAdapter;
    private MultipleImageAdapter1 multipleImageAdapter1;
    ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
    ArrayList<String> imgUrl = new ArrayList<String>();
    ArrayList<File> inImage;
    ArrayList<File> inImgImage;
    public static int imgClick = 0;
    File FirstImage,FirstFirstImage;
    File CapGalImg=null,CapGalCompImg=null;
    String cId,ucId;
    private String mCurrentPhotoPath,spinnervalue="";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_CAPTURE2 = 2;
    SessionManager sessionManager = new SessionManager();
    private ArrayList<categoryShopListModel.Data.Datum> data2;
    private ArrayList<unitListModel.Data.Datum> unitData;

    static final int RESULT_LOAD_IMAGE1 = 9;
    static final int REQUEST_IMAGE_CAPTURE8 = 8;
    AddProduct2Activity baseActivity;
    ChooseCAtAdapter categoryAdapter;
    String imagePath = "";
    UnitAdapter unitAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product2);
        ButterKnife.bind(this);

        txt_addVaiants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddProduct2Activity.this, AddVariantsActivity.class));
            }
        });


        li_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgUrl.size()==8){
                    SnackBar.returnFlashBar(AddProduct2Activity.this,"max. image up to 8");
                }else{
                    selectImage(AddProduct2Activity.this);
                }

            }
        });

        getunitList();
        getcategoryList1();
        //getcreateCategory();
        //getcategoryList1();
        txt_bCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bottomdilog1();
                //bottomdilog2();

            }
        });

        txt_unit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdilogUnit();
            }
        });

        txt_price.addTextChangedListener(watcher);
        txt_dPrice.addTextChangedListener(watcher1);

        txt_pName.setText(StaticVariables.P_title);

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_pName.getText().toString().equals("")){
                    txt_pName.setError("Please Enter Product Name");
                }
                else if(txt_bCategory.getText().toString().equals("")){
                    txt_bCategory.setError("Please Enter Business Category");
                }
                else if(txt_price.getText().toString().equals("")){
                    txt_price.setError("Please Enter Price");
                }
                /*else if(txt_dPrice.getText().toString().equals("")){
                    txt_dPrice.setError("Please Enter Discounted Price");
                }*/
                else if(txt_pUnit.getText().toString().equals("")){
                    txt_pUnit.setError("Please Enter Product Unit");
                }
                else if(txt_unit.getText().toString().equals("")){
                    txt_unit.setError("Select Product unit Type");
                }
                else if(txt_pDetails.getText().toString().equals("")){
                    txt_pDetails.setError("Please Enter Product Details");
                }
                else{
                    if(imgUrl.size()>0){
                        getcreateProduct();
                    }else{
                        SnackBar.returnFlashBar(AddProduct2Activity.this,"Please Add Procuct imagge");
                    }


                    //getcreateCategory();
                }
            }
        });

    }



    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String editableString = s.toString();
            if (editableString.contains("")) {
                li_disPrice.setVisibility(View.GONE);
                final String spaceFreeString = editableString.replaceAll(" ", "");
                txt_dPrice.setText("");
                // txtInputApo.setText(spaceFreeString);
                //.setSelection(spaceFreeString.length());
            }else{
                li_disPrice.setVisibility(View.VISIBLE);
                proPrice.setText("Price :"+txt_price.getText().toString());
                Log.e("1234567  ",txt_price.getText().toString());
            }
        }
    };

    TextWatcher watcher1 = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.e("Per  ","1");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.e("Per  ","2");
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.e("Per  ","3");
            String editableString = s.toString();
            proPrice.setText("Price :"+txt_price.getText().toString());
            try {
                li_disPrice.setVisibility(View.VISIBLE);
                if (editableString.contains(" ")) {
                    Log.e("Per  ","33");
                    final String spaceFreeString = editableString.replaceAll(" ", "");
                }
                else if(Integer.parseInt(txt_price.getText().toString())>Integer.parseInt(txt_dPrice.getText().toString())){
                    int pp = Integer.parseInt(txt_price.getText().toString())-Integer.parseInt(txt_dPrice.getText().toString());
                    int per= (pp*100)/Integer.parseInt(txt_price.getText().toString());
                    txt_per.setText(String.valueOf(per)+"%");
                    proPrice.setText("Price :"+txt_dPrice.getText().toString());
                }
                else if(Integer.parseInt(txt_price.getText().toString())<Integer.parseInt(txt_dPrice.getText().toString())){
                    txt_per.setText("0%");
                    txt_dPrice.setText("");
                    proPrice.setText("Price :"+txt_price.getText().toString());
                }
                else if(Integer.parseInt(txt_price.getText().toString())==Integer.parseInt(txt_dPrice.getText().toString())){
                    txt_dPrice.setText("");
                    txt_per.setText("0%");
                    proPrice.setText("Price :"+txt_price.getText().toString());
                }
                else{

                    // li_disPrice.setVisibility(View.GONE);
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }

        }
    };


    private void getcreateProduct() {
        Log.e("Unit ",txt_pUnit.getText().toString());
        Log.e("Unit1 ",ucId.toString());
        Log.e("Data ",sessionManager.getPreferences(AddProduct2Activity.this,"user_id")+"  "+ txt_pName.getText().toString()+"  "+txt_pUnit.getText().toString()+"  "+ucId+"  "+cId+"  "+""+"  "+""+"  "+"shop"+"  "+txt_price.getText().toString()+"  "+txt_dPrice.getText().toString()+"  "+txt_pDetails.getText().toString()+"  "+ StaticVariables.DeviceID+"  "+StaticVariables.Token+"  "+StaticVariables.DeviceType+"  "+"2"+"  ");
        JSONArray jsArray = new JSONArray(imgUrl);
         new ServerRequest<createProductModel>(this, Consts.getcreateProduct(sessionManager.getPreferences(AddProduct2Activity.this,"user_id"), txt_pName.getText().toString(),txt_pUnit.getText().toString(),ucId,cId,StaticVariables.colorId1, String.valueOf(StaticVariables.jsonArray),"shop",txt_price.getText().toString(),txt_dPrice.getText().toString(),txt_pDetails.getText().toString(), StaticVariables.DeviceID,StaticVariables.Token,StaticVariables.DeviceType,sessionManager.getPreferences(AddProduct2Activity.this,"s_id"), jsArray+""), true) {
            @Override
            public void onCompletion(Call<createProductModel> call, Response<createProductModel> response) {
                createProductModel modelSingleBlog = response.body();
                if (modelSingleBlog.getStatus()==200){
                    Log.e("Data1",modelSingleBlog.getStatus()+"");
                    // sessionManager.setPreferences(AddProduct2Activity.this, "p_status", "1");
                    //Toast.makeText(AddProduct2Activity.this, modelSingleBlog.getMessage(), Toast.LENGTH_SHORT).show();
                    onBackPressed();
                } else if (modelSingleBlog.getStatus()==403) {
                    StaticVariables.LogoutMessage =modelSingleBlog.getMessage();
                    Intent intent=new Intent(AddProduct2Activity.this, DilogActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AddProduct2Activity.this, modelSingleBlog.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void getcreateCategory(String title) {
        Log.e("Data  ",StaticVariables.P_title);
        new ServerRequest<createCategoryModel>(this, Consts.getcreateCategory(sessionManager.getPreferences(AddProduct2Activity.this,"user_id"),title,"shop",StaticVariables.DeviceID,StaticVariables.Token,StaticVariables.DeviceType,"https://telsyswebinfotech.com/wp-content/uploads/20"), true) {
            @Override
            public void onCompletion(Call<createCategoryModel> call, Response<createCategoryModel> response) {
                Log.e("Responce1",response.toString());
                createCategoryModel modelRegister = response.body();
                if (modelRegister.getStatus()==200){
                    status ="1";
                    getcategoryList1();
                    Log.e("Responce12",response.toString());
                    //   data2=modelRegister.getData();
                }else if (modelRegister.getStatus()==403) {
                    StaticVariables.LogoutMessage =modelRegister.getMessage();
                    Intent intent=new Intent(AddProduct2Activity.this, DilogActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    private void getcategoryList1() {
        new ServerRequest<categoryShopListModel>(this, Consts.getcategoryList1(sessionManager.getPreferences(AddProduct2Activity.this,"user_id"),"shop",StaticVariables.DeviceID,StaticVariables.Token,StaticVariables.DeviceType), true) {
            @Override
            public void onCompletion(Call<categoryShopListModel> call, Response<categoryShopListModel> response) {
                Log.e("Responce1",response.toString());
                categoryShopListModel modelRegister = response.body();
                if (modelRegister.getStatus()==200){
                    Log.e("Responce12",response.toString());
                    data2=modelRegister.getData().getData();
                    if(status.equals("1")){
                        bottomdilog1();
                    }
                }
                else if (modelRegister.getStatus()==403) {
                    StaticVariables.LogoutMessage =modelRegister.getMessage();
                    Intent intent=new Intent(AddProduct2Activity.this, DilogActivity.class);
                    startActivity(intent);
                }
                else {
                }
            }
        };
    }
    private void bottomdilog1() {
        ImageView close;
        RecyclerView rec_cat;
        Button btn_add;
        View view = getLayoutInflater().inflate(R.layout.cat_dilog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        btn_add =view.findViewById(R.id.btn_add);
        rec_cat =view.findViewById(R.id.rec_cat);
        rec_cat.setHasFixedSize(true);
        rec_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rec_cat.setHasFixedSize(true);
        try{
        if(data2.size()>0){
            categoryAdapter = new ChooseCAtAdapter(data2, baseActivity);
            rec_cat.setAdapter(categoryAdapter);
        }
        }catch (NullPointerException e){e.printStackTrace();}

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                bottomdilog2();
            }
        });

        rec_cat.addOnItemTouchListener(
                new RecyclerItemClickListener(AddProduct2Activity.this, rec_cat, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String list = data2.get(position).getId().toString();
                        //txt_catName.setText(data2.get(position).getTitle());
                        cId =data2.get(position).getId()+"";
                        StaticVariables.Position =position;
                        txt_bCategory.setText(data2.get(position).getTitle().toString());
                        dialog.cancel();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    private void bottomdilog2() {
        ImageView close;
        Button btn_save;
        TextInputEditText txt_cate;
        View view = getLayoutInflater().inflate(R.layout.cr_cat_dilog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        txt_cate =view.findViewById(R.id.txt_cate);
        btn_save =view.findViewById(R.id.btn_save);
        close =view.findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txt_cate.getText().toString().equals("")){
                    dialog.cancel();
                    getcreateCategory(txt_cate.getText().toString());
                }else{
                    txt_cate.setError("Please Enter Product Category");
                }
            }
        });


    }


    private void bottomdilogUnit() {
        ImageView close;
        RecyclerView rec_cat;
        View view = getLayoutInflater().inflate(R.layout.unit_dilog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        rec_cat =view.findViewById(R.id.rec_cat);
        /*rec_cat.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rec_cat.setHasFixedSize(true);*/

        rec_cat.setLayoutManager(new GridLayoutManager(this, 4));
        rec_cat.setHasFixedSize(true);

        if(unitData.size()>0){
            unitAdapter = new UnitAdapter(unitData, baseActivity);
            rec_cat.setAdapter(unitAdapter);
        }

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });



        rec_cat.addOnItemTouchListener(
                new RecyclerItemClickListener(AddProduct2Activity.this, rec_cat, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String list = unitData.get(position).getId().toString();
                        //txt_catName.setText(data2.get(position).getTitle());
                        ucId =unitData.get(position).getId()+"";
                        txt_unit.setText(unitData.get(position).getUnit().toString());
                        Log.e("Unit Id",ucId);
                        dialog.cancel();
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }



    private void imageUpload() {
        MultipartBody.Builder builder = new MultipartBody.Builder();

        // MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        // Gson gson = new Gson();
        builder.setType(MultipartBody.FORM);
        builder.addFormDataPart("user_id", sessionManager.getPreferences(AddProduct2Activity.this,"user_id"));
        builder.addFormDataPart("upload_type", "product");
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
                                imgUrl.add(object.getString("data"));

                                recyclerView.setLayoutManager(new LinearLayoutManager(AddProduct2Activity.this, RecyclerView.HORIZONTAL, false));
                                multipleImageAdapter1 = new MultipleImageAdapter1(imgUrl, AddProduct2Activity.this);
                                recyclerView.setAdapter(multipleImageAdapter1);
                                Log.d("ImageURL",imgUrl+"");
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
                    bitmapArray.add(MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath)));
                    mImageBitmap= (MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath)));
                    Log.d("images",String.valueOf(bitmapArray.size()));
                   /* recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                    multipleImageAdapter = new MultipleImageAdapter(bitmapArray, this);
                    recyclerView.setAdapter(multipleImageAdapter);*/
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
                //startActivityForResult(new Intent(this, EditImageActivity.class).putExtra("image",picturePath),REQUEST_IMAGE_CAPTURE2);
                c.close();
                bitmapArray.add((BitmapFactory.decodeFile(picturePath)));
                Log.d("images",String.valueOf(bitmapArray.size()));
                /*recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
                multipleImageAdapter = new MultipleImageAdapter(bitmapArray, this);
                recyclerView.setAdapter(multipleImageAdapter);*/

                mImageBitmap= (BitmapFactory.decodeFile(picturePath));
                FirstImage = bitmapToFile(mImageBitmap);
                FirstFirstImage = saveBitmapToFile(FirstImage);
                imageUpload();



            }

        }
    }

    private void getunitList() {
        new ServerRequest<unitListModel>(this, Consts.getunitList(sessionManager.getPreferences(AddProduct2Activity.this,"user_id"),StaticVariables.DeviceID,StaticVariables.Token,StaticVariables.DeviceType), false) {
            @Override
            public void onCompletion(Call<unitListModel> call, Response<unitListModel> response) {
                Log.e("Responce1",response.toString());
                unitListModel modelRegister = response.body();
                if (modelRegister.getStatus()==200){
                    Log.e("Responce12",response.toString());
                    unitData=modelRegister.getData().getData();
                }else if (modelRegister.getStatus()==403) {
                    StaticVariables.LogoutMessage =modelRegister.getMessage();
                    Intent intent=new Intent(AddProduct2Activity.this, DilogActivity.class);
                    startActivity(intent);
                }
                else {
                }
            }
        };
    }




}

