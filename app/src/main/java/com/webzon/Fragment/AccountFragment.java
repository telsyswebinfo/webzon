package com.webzon.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.webzon.Activity.Account.AdditionalInformationActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Activity.Account.EditBusinessActivity;
import com.webzon.Activity.WebzonpcActivity;
import com.webzon.Yplayer.TutorialsActivity;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utlity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.webzon.R;

public class AccountFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    LinearLayout li_add_info,li_tutorials;
    public static ImageView img_bus;
    TextView txt_edit_details;
    LinearLayout li_support,li_webzon;
    String currentDateandTime;
    public static TextView txt_name;
    SessionManager sessionManager = new SessionManager();
    Switch btn_sw;
    SwipeRefreshLayout swipe;

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public void FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        view = inflater.inflate(R.layout.fragment_account, container, false);
       // firstButton = (Button) view.findViewById(R.id.firstButton);
        li_add_info = view.findViewById(R.id.li_add_info);
        txt_name = view.findViewById(R.id.txt_name);
        txt_edit_details = view.findViewById(R.id.txt_edit_details);
        img_bus = view.findViewById(R.id.img_bus);
        li_support = view.findViewById(R.id.li_support);
        li_webzon = view.findViewById(R.id.li_webzonpc);
        li_tutorials = view.findViewById(R.id.li_tutorials);
        swipe = view.findViewById(R.id.swipe);
        btn_sw = view.findViewById(R.id.btn_sw);
        Glide.with(this).load(sessionManager.getPreferences(getActivity(), "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(img_bus);
        txt_name.setText(sessionManager.getPreferences(getActivity(), "b_name"));
        li_add_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AdditionalInformationActivity.class));
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());
        txt_edit_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EditBusinessActivity.class));
            }
        });

        li_tutorials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TutorialsActivity.class));
            }
        });

        li_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                Glide.with(getActivity()).load(sessionManager.getPreferences(getActivity(), "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(img_bus);
                txt_name.setText(sessionManager.getPreferences(getActivity(), "b_name"));
                swipe.setRefreshing(false);
            }
        });


        li_webzon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WebzonpcActivity.class));
                //Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://webzon.in/"));
               // startActivity(viewIntent);
            }
        });

        if(sessionManager.getPreferences(getActivity(),"lock_screen").equals("Yes")){
            btn_sw.setChecked(true);
        }else{
            btn_sw.setChecked(false);
        }

        btn_sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    getshopNotificationsUpdate("Yes");
                } else {
                    getshopNotificationsUpdate("No");
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(sessionManager.getPreferences(getActivity(), "image")).placeholder(R.drawable.camera_gray).dontAnimate().into(img_bus);
    }

    private void getshopNotificationsUpdate(String status){
        Utlity.show_progress(getActivity());
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.shopNotificationsUpdate)
                .addBodyParameter("device_id", StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(getActivity(),"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(getActivity(),"s_id"))
                .addBodyParameter("online", sessionManager.getPreferences(getActivity(),"online"))
                .addBodyParameter("lock_screen",status)
                .addBodyParameter("whatsapp_chat",sessionManager.getPreferences(getActivity(),"whatappchat"))
                .addBodyParameter("shop_open_time",currentDateandTime)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(getActivity());
                        try {
                            if (response.getString("status").equals("200")) {
                                sessionManager.setPreferences(getActivity(),"whatappchat",response.getJSONObject("data").getJSONObject("business").getString("whatsapp_chat"));
                                sessionManager.setPreferences(getActivity(),"lock_screen",response.getJSONObject("data").getJSONObject("business").getString("lock_screen"));
                                sessionManager.setPreferences(getActivity(),"online",response.getJSONObject("data").getJSONObject("business").getString("online"));
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(getActivity(), DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Utlity.dismiss_dilog(getActivity());
                        Log.e("Data1  ",error.toString());
                    }
                });
    }


}