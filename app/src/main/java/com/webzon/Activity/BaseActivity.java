package com.webzon.Activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.webzon.utils.Consts;
import com.webzon.utils.ImageUtils;
import com.webzon.utils.PrefStore;
import com.webzon.utils.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import webzon.R;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    public Context mContext;
    private Toolbar toolbar;
    private TextView titleTV;
    private ImageView backIV;
    public PrefStore store;
    public PermCallback permCallback;
    private int reqCode;
    private Dialog dialog;
    private TextView txtMsgTV;
    public CountDownTimer yourCountDownTimer;
    public MediaPlayer mediaPlayer;
    public MediaRecorder recorder;
    public MediaRecorder mMediaRecorder;
    public int category = 0;
    SessionManager sessionManager = new SessionManager();
    public ProgressDialog progressDoalog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = BaseActivity.this;
        store = new PrefStore(this);
        // keyHash();

    }



    @Override
    protected void onStart() {
        super.onStart();
        // setOnline(1);
    }



    public void setupActionBar(String title) {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (!TextUtils.isEmpty(title)) {
            ActionBar bar = getSupportActionBar();
            assert bar != null;
            bar.setDisplayUseLogoEnabled(false);
            bar.setDisplayShowTitleEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeButtonEnabled(true);
            bar.setTitle(title);
        }
    }

    public void setupActionBar(String title, boolean showBack) {
        androidx.appcompat.widget.Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar bar = getSupportActionBar();
        assert bar != null;
        if (showBack) {
            bar.setDisplayUseLogoEnabled(false);
            bar.setDisplayShowTitleEnabled(true);
            bar.setDisplayShowHomeEnabled(true);
            bar.setDisplayHomeAsUpEnabled(true);
            bar.setHomeButtonEnabled(true);
        }
        bar.setTitle(title);
    }

    public void startActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    public void startProgressDialog() {
        if (dialog != null && !dialog.isShowing()) {
            try {
                dialog.show();
            } catch (Exception e) {
            }
        }
    }

    public void stopProgressDialog() {
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
        }
    }

    public String changeDateFormatFromDate(Date sourceDate, String targetDateFormat) {
        SimpleDateFormat outputDateFormat = new SimpleDateFormat(targetDateFormat, Locale.getDefault());
        return outputDateFormat.format(sourceDate);
    }





    public String parseDateToddMMyyyy(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "dd/MM/yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String parseTime(String time) {
        String inputPattern = "yyyy-MM-dd HH:mm:ss";
        String outputPattern = "hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public String parsemin(String time) {
        String inputPattern = "HH:mm:ss";
        String outputPattern = "mm";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public void keyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.orem.gallery.bazarapp", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:>>>>>>>>>>>>>>>", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public void onClick(View v) {

    }

    public void showToast(String message) {
       /* DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        dialog.dismiss();
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setPositiveButton("OK", dialogClickListener)
                .show();*/
        // Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

        final Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_LONG);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 20000);
    }

    public void log(String message) {
        Log.e(getString(R.string.app_name), message);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void onStop() {
        super.onStop();


    }


    public interface PermCallback {
        void permGranted(int resultCode);
        void permDenied(int resultCode);
    }

    public boolean emailValidator(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*?[a-z])(?=.*?[0-9]).{8,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageUtils.activityResult(requestCode, resultCode, data);
    }*/

    /* Description: Get Device token & Check device token is updated or not previous one
          Check whether we already login than it takes us on MainAgentActivity
          otherwise it takes us on login Screen
      * */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void gotoLoginActivity() {
        store.setBoolean(Consts.ISLOGIN,false);
       /* Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);*/
        finish();
        finishAffinity();
    }


/*    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, Consts.PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                showToast(getString(R.string.this_device_not_supported));
                finish();
            }
            return false;
        }
        return true;
    }*/

   /* @SuppressLint("NewApi")
    public void initFCM() {
        if (checkPlayServices()) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            if (refreshedToken == null) {
                if (store.getString(Consts.DEVICE_TOKEN) == null) {
                    if (store.getBoolean(Consts.ISLOGIN) == true) {
                        setRefreshDeviceToken();
                    } else {
                        gotoLoginActivity();
                    }

                } else {
                    store.saveString(Consts.DEVICE_TOKEN, refreshedToken);
                    if (store.getBoolean(Consts.ISLOGIN) == true) {
                        setRefreshDeviceToken();
                    } else {
                        gotoLoginActivity();
                    }

                }
            } else {
                if (!refreshedToken.equalsIgnoreCase(store.getString(Consts.DEVICE_TOKEN))) {
                    store.saveString(Consts.DEVICE_TOKEN, refreshedToken);
                    if (store.getBoolean(Consts.ISLOGIN) == true) {
                        setRefreshDeviceToken();
                    } else {
                        gotoLoginActivity();
                    }
                } else {
                    try {
                        if (refreshedToken != null) {
                            refreshedToken = new JSONObject(refreshedToken).getString("token");
                            store.saveString(Consts.DEVICE_TOKEN, refreshedToken);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (refreshedToken != null) {
                        store.saveString(Consts.DEVICE_TOKEN, refreshedToken);
                        if (store.getBoolean(Consts.ISLOGIN) == true) {
                            setRefreshDeviceToken();
                        } else {
                            gotoLoginActivity();
                        }

                    } else {
                        if (store.getString(Consts.DEVICE_TOKEN) == null) {
                            if (store.getBoolean(Consts.ISLOGIN) == true) {
                                setRefreshDeviceToken();
                            } else {
                                gotoLoginActivity();
                            }

                        } else {
                            if (store.getBoolean(Consts.ISLOGIN) == true) {
                                setRefreshDeviceToken();
                            } else {
                                gotoLoginActivity();
                            }

                        }
                    }
                }
            }
        }
    }*/

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void gotoMainActivity() {
        sessionManager.setPreferences(BaseActivity.this,"type","User");
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finishAffinity();
    }
   /* @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageUtils.activityResult(requestCode, resultCode, data);
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImageUtils.activityResult(requestCode, resultCode, data);
    }

    public void gotoIntroActivity() {
        /*Intent intent = new Intent(this, WalkThroughActivity.class);
        startActivity(intent);*/
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setRefreshDeviceToken() {
        if (store.getString(Consts.LOGINAS).equalsIgnoreCase("2")) {
            gotoMainActivity();
        } else {
            // gotoMainSpeActivity();
        }


    }



    ///------------------------------------------------------LANGUAGE CHANGE---------------------------------------//


    public void checkLanguage() {
        String language = "English";
        try {
            language = store.getString("languages");
            if (language != null) {
                if (language.equals("Arabic")) {
                    String languageToLoad = "ar";
                    Locale locale = new Locale(languageToLoad);
                    Locale.setDefault(locale);
                    Configuration config = new Configuration();
                    config.locale = locale;
                    getBaseContext().getResources().updateConfiguration(config,
                            getBaseContext().getResources().getDisplayMetrics());

                } else if (language.equals("English")) {
                    String languageToLoad2 = "en";
                    Locale locale2 = new Locale(languageToLoad2);
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getBaseContext().getResources().updateConfiguration(config2,
                            getBaseContext().getResources().getDisplayMetrics());
                } else if (language.equals("Kurdish")) {
                    String languageToLoad2 = "ar";
                    Locale locale2 = new Locale(languageToLoad2);
                    Locale.setDefault(locale2);
                    Configuration config2 = new Configuration();
                    config2.locale = locale2;
                    getBaseContext().getResources().updateConfiguration(config2,
                            getBaseContext().getResources().getDisplayMetrics());
                }
            } else {
                String languageToLoad2 = "ar";
                Locale locale2 = new Locale(languageToLoad2);
                Locale.setDefault(locale2);
                Configuration config2 = new Configuration();
                config2.locale = locale2;
                getBaseContext().getResources().updateConfiguration(config2,
                        getBaseContext().getResources().getDisplayMetrics());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
        public void logout() {
            new ServerRequest<RegisterData>(mContext, Conts.logout(store.getString(Conts.USER_ID), store.getString(Conts.LANGUAGE)), true) {

                @Override
                public void onCompletion(Call<RegisterData> call, Response<RegisterData> response) {

                    store.setBoolean(Conts.isRatingLogin, false);
                    showToast(response.raw().message());
                    store.setBoolean(Conts.ISFACEBOOKLOGIN, false);
                    store.setBoolean(Conts.IS_LOGIN, false);
                    //   store.setInt(Conts.CHECK_USER, Conts.USER);
                    store.saveString(Conts.CITY, null);
                    Intent intent = new Intent(BaseActivity.this, SplashActivity.class);
                    startActivity(intent);
                    finish();

                }
            };
        }*/
    public void stopRecording() {


        if (null != recorder) {
            recorder.stop();
            recorder.reset();
            recorder.release();
            recorder = null;
        }
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
        }
    }

    public String formatValue(double value) {
        int power;
        String suffix = " kmbt";
        String formattedNumber = "";

        NumberFormat formatter = new DecimalFormat("#,###.#");
        power = (int) StrictMath.log10(value);
        value = value / (Math.pow(10, (power / 3) * 3));
        formattedNumber = formatter.format(value);
        formattedNumber = formattedNumber + suffix.charAt(power / 3);
        return formattedNumber.length() > 4 ? formattedNumber.replaceAll("\\.[0-9]+", "") : formattedNumber;
    }

    public int getCountOfDays(String createdDateString) {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date1 = null, date2 = null;
        try {
            date1 = dateFormat.parse(createdDateString);
            date2 = dateFormat.parse(dateFormat.format(c));

        } catch (Exception e) {

        }
        long diff = date2.getTime() - date1.getTime();
        long day = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return (int) day;

    }

    public boolean checkPermissions(String[] perms, int requestCode, PermCallback permCallback) {
        this.permCallback = permCallback;
        this.reqCode = requestCode;
        ArrayList<String> permsArray = new ArrayList<>();
        boolean hasPerms = true;
        for (String perm : perms) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                permsArray.add(perm);
                hasPerms = false;
            }
        }
        if (!hasPerms) {
            String[] permsString = new String[permsArray.size()];
            for (int i = 0; i < permsArray.size(); i++) {
                permsString[i] = permsArray.get(i);
            }
            ActivityCompat.requestPermissions(BaseActivity.this, permsString, 99);
            return false;
        } else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permGrantedBool = false;
        switch (requestCode) {
            case 99:
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        showToast(getString(R.string.not_sufficient_permissions)
                                + getString(R.string.app_name)
                                + getString(R.string.permissionss));
                        permGrantedBool = false;
                        break;
                    } else {
                        permGrantedBool = true;
                    }
                }
                if (permCallback != null) {
                    if (permGrantedBool) {
                        permCallback.permGranted(reqCode);
                    } else {
                        permCallback.permDenied(reqCode);
                    }
                }
                break;
        }
    }


    public void logout() {
        store.setBoolean(Consts.ISLOGIN, false);
        gotoLoginActivity();


    };
       /* UserData.Data data = new Gson().fromJson(store.getString(Consts.USERADATA), UserData.Data.class);
        new ServerRequest<ResponseData>(mContext, Consts.logout(data.id), true) {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCompletion(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().response == Consts.STTAUSOK) {
                    store.setBoolean(Consts.ISLOGIN, false);
                    gotoLoginActivity();
                } else if (response.body().response == Consts.Forbidden) {
                    gotoLoginActivity();
                } else {
                    showToast(response.body().mesg);
                    gotoLoginActivity();
                }

            }
        };*/


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Consts.handler.removeCallbacksAndMessages(null);
        overridePendingTransition(R.anim.right_in, R.anim.right_out);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void showProgress(String message) {
        progressDoalog = new ProgressDialog(mContext, R.style.TransparentDialogTheme);
        progressDoalog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDoalog = new ProgressDialog(mContext);
        progressDoalog.setMessage("Processing...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    public void showProgress() {
        hideProgress();
        progressDoalog = new ProgressDialog(mContext, R.style.TransparentDialogTheme);
        progressDoalog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progressDoalog = new ProgressDialog(mContext);
        progressDoalog.setMessage("Processing...");
        progressDoalog.setCancelable(false);
        progressDoalog.show();
    }

    public void hideProgress() {
        try {
            if (progressDoalog!=null) progressDoalog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gotoForgotActivity() {
      /*  Intent intent = new Intent(this, ForgotPasswordUsernameActivity.class);
        startActivity(intent);*/
    }

}
