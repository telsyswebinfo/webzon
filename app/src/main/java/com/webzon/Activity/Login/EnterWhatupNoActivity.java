package com.webzon.Activity.Login;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.truecaller.android.sdk.ITrueCallback;
import com.truecaller.android.sdk.TrueError;
import com.truecaller.android.sdk.TrueProfile;
import com.truecaller.android.sdk.TruecallerSDK;
import com.truecaller.android.sdk.TruecallerSdkScope;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ConnectionCheck;
import com.webzon.utils.Misc;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import webzon.R;


public class EnterWhatupNoActivity extends CustomActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    @BindView(R.id.btn_start) Button btn_start;
    @BindView(R.id.et_number) EditText et_number;
    @BindView(R.id.login_phone_cc) TextView login_phone_cc;
    public static String otp,number;
    private static final int RC_SIGN_IN = 235;
    private CallbackManager callbackManager;
    private SearchView searchView;
    JSONObject obj;
    private String ct, cc, pNumber, vId;
    SessionManager sessionManager = new SessionManager();
    private static final int PERMISSION_REQUEST_CODE = 10;
    private ctryAdapter adapter;
    private Dialog ccDiag, loadingDiag, conDiag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_whatup_no);
        ButterKnife.bind(this);
        setupActionBar("Enter Whatup Number",false);
        // bottomdilog();
       // login_phone_cc.setOnClickListener(view -> showCC());
        login_phone_cc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCC();
            }
        });
        requestPermission();
        findViewById(R.id.login_goog_btn).setOnClickListener(view -> gooLogin());
        findViewById(R.id.login_fb_btn).setOnClickListener(v -> fbLogin());
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_number.getText().toString().length()==10) {
                        String number = et_number.getText().toString();
                    String initialPart = number.substring(0, 4);
                    Log.e("Number  ",initialPart+"");
                    if(Integer.parseInt(initialPart)>=6000){
                        if (ConnectionCheck.hasConnection(EnterWhatupNoActivity.this)) {
                           // getlogin();
                            apiData();
                            Log.e("1","1");
                        }
                        else {
                            SnackBar.returnFlashBar(EnterWhatupNoActivity.this, "Please check internet connection...");
                        }

                    }else{
                        et_number.setError("Please Enter Valid Number");
                    }

                }else{
                    et_number.setError("Please Enter Valid Number");
                }

            }
        });



        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(TruecallerSDK.getInstance().isUsable()){
                    TruecallerSDK.getInstance().getUserProfile( EnterWhatupNoActivity.this);
                }else{
                }
            }
        }, 100);


        // customisation of TrueCaller function like color , text can be done here
        TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallback)
                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_CONTINUE_WITH)
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .privacyPolicyUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .termsOfServiceUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .footerType(TruecallerSdkScope.FOOTER_TYPE_CONTINUE)
                .build();
        TruecallerSDK.init(trueScope);
        /*TruecallerSdkScope trueScope = new TruecallerSdkScope.Builder(this, sdkCallback)
                .consentMode(TruecallerSdkScope.CONSENT_MODE_BOTTOMSHEET)
                .loginTextPrefix(TruecallerSdkScope.LOGIN_TEXT_PREFIX_TO_GET_STARTED)
                .loginTextSuffix(TruecallerSdkScope.LOGIN_TEXT_SUFFIX_PLEASE_VERIFY_MOBILE_NO)
                .ctaTextPrefix(TruecallerSdkScope.CTA_TEXT_PREFIX_USE)
                .buttonShapeOptions(TruecallerSdkScope.BUTTON_SHAPE_ROUNDED)
                .privacyPolicyUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .termsOfServiceUrl("<<YOUR_PRIVACY_POLICY_LINK>>")
                .footerType(TruecallerSdkScope.FOOTER_TYPE_SKIP)
                .consentTitleOption(TruecallerSdkScope.SDK_CONSENT_TITLE_LOG_IN)
                .build();
        TruecallerSDK.init(trueScope);*/


    }
    private final ITrueCallback sdkCallback = new ITrueCallback() {

        @Override
        public void onSuccessProfileShared(@NonNull final TrueProfile trueProfile) {
            Log.i("TAG",trueProfile.phoneNumber);
            Log.e("1   ",trueProfile.phoneNumber);
            //  launchHome(trueProfile);
            String string = trueProfile.phoneNumber;
            String[] parts = string.split("\\+91");
            String part2 = parts[1]; // 034556
            et_number.setText(part2);
        }

        @Override
        public void onFailureProfileShared(@NonNull final TrueError trueError) {
            Log.i("TAG", trueError.toString());
            Log.e("2   ", trueError.toString());
        }

        @Override
        public void onVerificationRequired(@Nullable final TrueError trueError) {
            Log.i("TAG", "onVerificationRequired");
            Log.e("3   ", "onVerificationRequired");
        }

    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TruecallerSDK.SHARE_PROFILE_REQUEST_CODE) {
            TruecallerSDK.getInstance().onActivityResultObtained(this, requestCode, resultCode, data);
        }
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void bottomdilog() {
        Button btn_start;
        View view = getLayoutInflater().inflate(R.layout.wndilog_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        btn_start = view.findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(EnterWhatupNoActivity.this,EnterOTPActivity.class));

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        TruecallerSDK.clear();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }



    private void requestPermission() {
        if (SDK_INT >= Build.VERSION_CODES.R) {

            if ((checkSelfPermission(CAMERA)
                    == PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(WRITE_EXTERNAL_STORAGE)
                    //  == PackageManager.PERMISSION_GRANTED)&&(checkSelfPermission(MANAGE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{CAMERA, READ_EXTERNAL_STORAGE,WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            }


            if (Environment.isExternalStorageManager()) {
                //todo when permission is granted
            } else {
               /* try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s",getApplicationContext().getPackageName())));
                    startActivityForResult(intent, 2296);
                } catch (Exception e) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivityForResult(intent, 2296);
                }
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);*/

            }

        } else {
            //below android 11
            ActivityCompat.requestPermissions(EnterWhatupNoActivity.this, new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean CAMERA = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean READ_EXTERNAL_STORAGE = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean WRITE_EXTERNAL_STORAGE = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    if (CAMERA && READ_EXTERNAL_STORAGE && WRITE_EXTERNAL_STORAGE) {
                        //log("Update flow Result code: " + requestCode);
                        //filesvlue=intentpref.getString("values",null);
                        // perform action when allow permission success
                    } else {
                        Toast.makeText(this, "Allow permission for storage access!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    private void apiData() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("phone_number", et_number.getText().toString());
        hashMap.put("role", StaticVariables.Role);
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("phone_code", "+91");

        WebServiceHandler webServiceHandler = new WebServiceHandler(EnterWhatupNoActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        StaticVariables.Timer=jsonObect.getInt("timer");
                        StaticVariables.Number=et_number.getText().toString();
                        sessionManager.setPreferences(EnterWhatupNoActivity.this,"user_id",jsonObect.getString("user_id"));
                        sessionManager.setPreferences(EnterWhatupNoActivity.this,"m_number",et_number.getText().toString());
                        StaticVariables.Status="0";
                        startActivity(new Intent(EnterWhatupNoActivity.this, EnterOTPActivity.class));
                        overridePendingTransition(R.anim.left_in, R.anim.left_out);
                        finish();
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(EnterWhatupNoActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        SnackBar.returnFlashBar(EnterWhatupNoActivity.this,"message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.login);
    }

    private void gooLogin() {
      /*  GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.g_server_client_id))
                .requestEmail()
                .build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(EnterWhatupNoActivity.this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);*/
        signIn();
    }

    private void fbLogin() {
        LoginManager.getInstance().logInWithReadPermissions(EnterWhatupNoActivity.this, Arrays.asList("email", "public_profile"));

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        String fbToken = loginResult.getAccessToken().getToken();
                      //  if (fbToken != null) cLoginCall("f", fbToken);
                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(EnterWhatupNoActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void signIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Log.w("signInResult1", "signInResult:failed code=" + account.getDisplayName()+"");

            // Signed in successfully, show authenticated UI.
            //updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("signInResult", "signInResult:failed code=" + e.getStatusCode());
           // updateUI(null);
        }
    }

    private void showCC() {
        if (ccDiag == null) {
            ccDiag = Misc.decoratedDiag(this, R.layout.dialog_cc, 0.5f);
            ccDiag.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            searchView = ccDiag.findViewById(R.id.dialog_cc_searchView);
            ListView listView = ccDiag.findViewById(R.id.dialog_cc_listView);
            ccDiag.findViewById(R.id.dialog_cc_close).setOnClickListener(view -> ccDiag.dismiss());
            adapter = new ctryAdapter();
            listView.setAdapter(adapter);
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setIconifiedByDefault(false);
            searchView.setOnQueryTextListener(EnterWhatupNoActivity.this);
            searchView.setOnCloseListener(EnterWhatupNoActivity.this);
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                ct = "+" + adapter.cName.get(i).get("ct");
                cc = "+" + adapter.cName.get(i).get("cc");
                login_phone_cc.setText(ct);
                ccDiag.dismiss();
            });
        } else {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            adapter.filterData("");
        }
        ccDiag.show();
    }

    @Override
    public boolean onClose() {
        adapter.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filterData(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filterData(newText);
        return false;
    }


    private class ctryAdapter extends BaseAdapter {
        private final ArrayList<HashMap<String, String>> cName, original;

        ctryAdapter() {
            cName = new ArrayList<>();
            original = new ArrayList<>();
            HashMap<String, String> data;
            for (int i = 0; i < StaticVariables.countryNames.length; i++) {
                data = new HashMap<>();
                data.put("c", StaticVariables.countryNames[i]);
                data.put("ct", StaticVariables.countryAreaCodes[i]);
                data.put("cc", StaticVariables.countryCodes[i]);
                cName.add(data);
                original.add(data);
            }
        }

        @Override
        public int getCount() {
            return cName.size();
        }

        @Override
        public Object getItem(int i) {
            return cName.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.dialog_cc_list, viewGroup, false);
            }
            TextView nameView = view.findViewById(R.id.dialog_cc_list_name);
            nameView.setText(cName.get(i).get("c"));
            TextView numView = view.findViewById(R.id.dialog_cc_list_number);
            numView.setText(("+" + cName.get(i).get("ct")));
            return view;
        }

        public void filterData(String query) {
            query = query.toLowerCase();
            cName.clear();
            if (query.isEmpty()) {
                cName.addAll(original);
            } else {
                for (HashMap<String, String> hashMap : original) {
                    if (Objects.requireNonNull(hashMap.get("c")).toLowerCase().contains(query))
                        cName.add(hashMap);
                }
            }
            notifyDataSetChanged();
        }
    }
}