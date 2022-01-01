package com.webzon.Activity.Manage;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.zxing.WriterException;
import com.webzon.utils.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.webzon.R;
//https://medium.com/agora-io/agora-cloud-recording-quickstart-guide-with-postman-demo-c4a6b824e708
public class QRCodeActivity extends AppCompatActivity {
    @BindView(R.id.img_qrcode) ImageView img_qrcode;
    @BindView(R.id.txt_name) TextView txt_name;
    @BindView(R.id.txt_durl) TextView txt_durl;
    @BindView(R.id.li_qrlayout) LinearLayout li_qrlayout;
    @BindView(R.id.card_print) CardView card_print;
    @BindView(R.id.btn_create_coupons) Button btn_create_coupons;
    SessionManager sessionManager = new SessionManager();
    PdfDocument pdfDocument;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        permissionCheck();
        generateQRCode();
        txt_durl.setText(sessionManager.getPreferences(QRCodeActivity.this, "d_url"));
        txt_name.setText(sessionManager.getPreferences(QRCodeActivity.this, "b_name"));

     /*   li_qrlayout.setDrawingCacheEnabled(true);
        li_qrlayout.buildDrawingCache();
        Bitmap b = li_qrlayout.getDrawingCache();*/
        btn_create_coupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // showPDF(String.valueOf(uri));
                shareImage();
            }
        });
        //makePDF();

    }
    private void permissionCheck(){
        if (ContextCompat.checkSelfPermission(QRCodeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(QRCodeActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},123);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            if(grantResults.length > 0){
                for(int i=0;i<grantResults.length;i++)
                    if(grantResults[0] != PackageManager.PERMISSION_GRANTED)
                        finish();
            }else{
                finish();
            }
        }
    }

    private void generateQRCode(){
        try {
            QRGEncoder qrgEncoder = new QRGEncoder(sessionManager.getPreferences(QRCodeActivity.this, "d_url"),null, QRGContents.Type.TEXT,150);
            Bitmap bitmap = qrgEncoder.encodeAsBitmap();
            img_qrcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void sharePdf(){
        File outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "example.pdf");
        Uri uri = Uri.fromFile(outputFile);

        Intent share = new Intent();
        share.setAction(Intent.ACTION_SEND);
        share.setType("application/pdf");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.setPackage("com.whatsapp");
        startActivity(share);
    }

    public void makePDF() {
        li_qrlayout.setDrawingCacheEnabled(true);
        li_qrlayout.buildDrawingCache();
        Bitmap bitmap = li_qrlayout.getDrawingCache();
        pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(), bitmap.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#FFFFFF"));
        canvas.drawBitmap(bitmap, 0, 0, null);
        pdfDocument.finishPage(page);
        saveFile();

    }


    public void saveFile() {
        if (pdfDocument == null) {
            Log.i("local-dev", "pdfDocument in 'saveFile' function is null");
            return;
        }
        File root = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "ImgToPDF");
        boolean isDirectoryCreated = root.exists();
        if (!isDirectoryCreated) {
            isDirectoryCreated = root.mkdir();
        }

            String userInputFileName = "QrCode.pdf";
            File file = new File(root, userInputFileName);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                pdfDocument.writeTo(fileOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();

        Log.i("local-dev", "'saveFile' function successfully done");
      //  Toast.makeText(this, "Successful! PATH:\n" + "Internal Storage/" + Environment.DIRECTORY_DOWNLOADS, Toast.LENGTH_SHORT).show();
        uri = Uri.fromFile(file);

      //  showPDF(String.valueOf(uri));
    }

    public void showPDF(String uri) {

        /*Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, 123);*/
        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("application/pdf");
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            Uri imageUri =  Uri.parse(String.valueOf(uri));
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            startActivity(Intent.createChooser(share, "Select"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){
            Log.e("Error ",e.toString());
        }
    }

    private void shareImage() {
        try {
            card_print.setDrawingCacheEnabled(true);
            card_print.buildDrawingCache();
            Bitmap b = card_print.getDrawingCache();
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
}