package com.webzon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.webzon.halper.StaticVariables;
import com.webzon.otptextview.OTPListener;
import com.webzon.otptextview.OtpTextView;
import com.webzon.smeRetriever.AutoDetectOTP;
import com.webzon.utils.Misc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import com.webzon.R;
public class DemoActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
   // AutoDetectOTP autoDetectOTP;
   // TextView textView;
   private SearchView searchView;
   private String otpnN0;
    private String ct, cc, pNumber, vId;
    TextView timer;
    AutoDetectOTP autoDetectOTP;
    private OtpTextView otpTextView;
    TextView ccVw;
    private ctryAdapter adapter;
    private Dialog ccDiag, loadingDiag, conDiag;
    CountDownTimer countDownTimer=   new CountDownTimer(180000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            timer.setText(millisecondsToTime(millisUntilFinished));
        }
        @Override
        public void onFinish() {
            timer.setText("");
        }
    };
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(otpnN0!=null&&otpnN0.equals("1234")){
                otpTextView.showSuccess();
                countDownTimer.cancel();
            }
            else {
                otpTextView.showError();
            }
        }
    };

    //*************************
    //*************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);


        //*******************************
        ccVw =findViewById(R.id.login_phone_cc);
        ccVw.setOnClickListener(view -> showCC());
        //*******************************
       /* autoDetectOTP = new AutoDetectOTP(this);
        textView =findViewById(R.id.textView);
        autoDetectOTP.startSmsRetriver(new AutoDetectOTP.SmsCallback() {
            @Override
            public void connectionfailed() {
                //do something here on failure
                Toast.makeText(DemoActivity.this,"Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionSuccess(Void aVoid) {
                //do something here on success
                Toast.makeText(DemoActivity.this,"Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void smsCallback(String sms) {
                if(sms.contains(":") && sms.contains(".")) {
                    String otp = sms.substring( sms.indexOf(":")+1 , sms.indexOf(".") ).trim();
                    textView.setText(otp);
                    Toast.makeText(DemoActivity.this,"The OTP is " + otp, Toast.LENGTH_SHORT).show();
                    Log.e("1  ",otp);
                }
            }
        });*/
        autoDetectOTP=new AutoDetectOTP(this);

        TextView phoneview=findViewById(R.id.phone_);
        timer=  findViewById(R.id.timer);

        String no=getIntent().getStringExtra("NO");
        if(no!=null){
            phoneview.append(no);
        }
        otpTextView = findViewById(R.id.otp_view);
        otpTextView.requestFocusOTP();
        otpTextView.setOtpListener(new OTPListener() {;

            @Override
            public void onInteractionListener() {

            }

            @Override
            public void onOTPComplete(String otp) {
                otpnN0=otp;
                Toast.makeText(DemoActivity.this,"The OTP is " + otp, Toast.LENGTH_SHORT).show();
                handler.postDelayed(runnable,100);

            }
        });
        countDownTimer.start();
        autoDetectOTP.startSmsRetriver(new AutoDetectOTP.SmsCallback() {
            @Override
            public void connectionfailed() {
                Toast.makeText(DemoActivity.this,"Failed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void connectionSuccess(Void aVoid) {
                Toast.makeText(DemoActivity.this,"Success", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void smsCallback(String sms) {
                if(sms.contains(":") && sms.contains(".")) {
                    String otp = sms.substring( sms.indexOf(":")+1 , sms.indexOf(".") ).trim();
                    otpTextView.setOTP(otp);
                    Toast.makeText(DemoActivity.this,"The OTP is " + otp, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    @Override
    protected void onDestroy() {

        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
        super.onDestroy();
    }
    @Override
    protected void onStop() {
        super.onStop();
        autoDetectOTP.stopSmsReciever();
        if (countDownTimer != null) {
            countDownTimer.onFinish();
            countDownTimer.cancel();
        }
    }
    private String millisecondsToTime(long milliseconds) {

        return "Time remaining " + String.format("%d : %d ",
                TimeUnit.MILLISECONDS.toMinutes(milliseconds),
                TimeUnit.MILLISECONDS.toSeconds(milliseconds) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(milliseconds)));
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
            searchView.setOnQueryTextListener(DemoActivity.this);
            searchView.setOnCloseListener(DemoActivity.this);
            listView.setOnItemClickListener((adapterView, view, i, l) -> {
                ct = "+" + adapter.cName.get(i).get("ct");
                cc = "+" + adapter.cName.get(i).get("cc");
                ccVw.setText(ct);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (ct != null && ccVw != null) ccVw.setText(ct);
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