package com.webzon.fcm;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Activity.SplashActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.SessionManager;

import java.util.Map;
import java.util.Random;

import webzon.R;


public class MyFirebaseMessagingService extends FirebaseMessagingService {
    Intent intent;
    String title,message,type,date,pdflink,linktype;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    String channelId = "all_notifications";
    public static final String FCM_PARAM = "picture";
    SessionManager sessionManager = new SessionManager();
    Uri sound;
    Context context;
    @SuppressLint("WrongThread")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + this.getPackageName() + "/" + R.raw.oppo_notification);
        if (remoteMessage.getData()!=null){
            Log.e("Data  ",remoteMessage.getData().toString());
            title = remoteMessage.getData().get("title");
            // message = remoteMessage.getData().get("description");
            type = remoteMessage.getData().get("type");
            // date = remoteMessage.getData().get("date");
            //pdflink = remoteMessage.getData().get("pdflink");
            //linktype = remoteMessage.getData().get("linktype");
            StaticVariables.WebPageType ="logout";
            sessionManager.setPreferences(context,"logout","logout");
            RemoteMessage.Notification notification = remoteMessage.getNotification();
            Map<String, String> data = remoteMessage.getData();
            Log.e("mnotificatin",""+remoteMessage.getData().get("type"));

            getfirebasenotification(title,"message",remoteMessage.getData().get("type"),data,notification);
           // Log.e("Data  ",remoteMessage.getData().toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getfirebasenotification(String title, String body, String type,Map<String, String> data,RemoteMessage.Notification notification) {
        //unread++;
        Bundle bundle = new Bundle();
        bundle.putString(FCM_PARAM, data.get(FCM_PARAM));
        Log.e("Data ",title+"  "+body+" "+type);
        Ringtone r = RingtoneManager.getRingtone(this.getApplicationContext(), sound);
        r.play();
        @SuppressLint("ResourceAsColor") NotificationCompat.Builder builder = new NotificationCompat.Builder(MyFirebaseMessagingService.this,channelId)
                .setSmallIcon(R.drawable.logo)
                .setColor(R.color.purple_200)

                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(title))
                .setContentText(notification.getTitle())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.getTitle()))
                /* .setContentText(body)
                 .setStyle(new NotificationCompat.BigTextStyle().bigText(body))*/
                .setAutoCancel(true);

        if (type.equals("logout")) {
            try {
                intent = new Intent(MyFirebaseMessagingService.this, SplashActivity.class);
                intent.putExtra("log","logout");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }catch (NullPointerException e){e.printStackTrace();}
        }else{
            try{
                intent = new Intent(MyFirebaseMessagingService.this, HomeActivity2.class);
                intent.putExtra("log","logout");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }catch (NullPointerException e){e.printStackTrace();}
        }

        /*if (type.equals("InspectionActivity")){
            intent = new Intent(MyFirebaseMessagingService.this, InspectionActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else if (type.equals("CleaningMaintenanceActivity")) {
            intent = new Intent(MyFirebaseMessagingService.this,CleaningMaintenanceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else if (type.equals("FsmsdocumentActivity")) {
            intent = new Intent(MyFirebaseMessagingService.this, FsmsdocumentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else if (type.equals("NotificationActivity")) {
            intent = new Intent(MyFirebaseMessagingService.this, NotificationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else {
            if (Uerrolid.equalsIgnoreCase("2")){
                intent = new Intent(MyFirebaseMessagingService.this, AdminMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            }
            else if (Uerrolid.equalsIgnoreCase("3")){
                intent = new Intent(MyFirebaseMessagingService.this, UserMainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            }
        }*/
        final int not_nu=generateRandom();
        PendingIntent pendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this,
                not_nu,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        int importance = NotificationManager. IMPORTANCE_HIGH ;
        NotificationChannel notificationChannel = new
                NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
        builder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;

        notificationManager.createNotificationChannel(notificationChannel) ;

        notificationManager.notify(not_nu,builder.build());

    }
    public int generateRandom(){
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
