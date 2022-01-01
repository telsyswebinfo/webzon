package com.webzon.Fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.archit.calendardaterangepicker.customviews.CalendarListener;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.SliderAdapter;
import com.webzon.Activity.Product.AddProductActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Model.BannerModel;
import com.webzon.Model.homePageDataModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.SemiCircleProgressBarView;
import com.webzon.utils.SemiCircleProgressBarViewGray;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.webzon.R;

public class HomeFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // verifyUserModel.Banner banner;
    Button btn_add_products,btn_store;
    RecyclerView sliderRV;
    SliderAdapter blogListAdapter;
    private ArrayList<homePageDataModel.Banner> data1;
    private ArrayList<BannerModel> bannerlist = new ArrayList<>();
    BannerModel bannerModel;
    //  private ArrayList<verifyUserModel.Banner> data2;
    HomeActivity2 baseActivity;
    SessionManager sessionManager = new SessionManager();
    TextView txt_order,txt_sale,txt_sviews,txt_pviews,txt_durl,title;
    CardView card_profile,card_addproduct;
    LinearLayout scrollview;
    ImageView img_share;
    Spinner spinner;
    String status="0";
    String fliterType="LT";
    LabeledSwitch switch1;
    String currentDateandTime;
    String url,slug;
    Context context;
    SwipeRefreshLayout swipe;
    public String s_date1="",e_date1="";
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        view = inflater.inflate(R.layout.fragment_home, container, false);
        // firstButton = (Button) view.findViewById(R.id.firstButton);
        btn_add_products = view.findViewById(R.id.btn_add_products);
        txt_order = view.findViewById(R.id.txt_order);
        txt_sale = view.findViewById(R.id.txt_sale);
        txt_sviews = view.findViewById(R.id.txt_sviews);
        txt_pviews = view.findViewById(R.id.txt_pviews);
        card_profile = view.findViewById(R.id.card_profile);
        card_addproduct = view.findViewById(R.id.card_addproduct);
        scrollview = view.findViewById(R.id.scrollview);
        img_share = view.findViewById(R.id.img_share);
        txt_durl = view.findViewById(R.id.txt_durl);
        title = view.findViewById(R.id.title);
        btn_store = view.findViewById(R.id.btn_store);
        spinner = view.findViewById(R.id.spinner);
        switch1 = view.findViewById(R.id.switch1);
        swipe = view.findViewById(R.id.swipe);

        sliderRV = view.findViewById(R.id.sliderRV);
        sliderRV.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        sliderRV.setHasFixedSize(true);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        currentDateandTime = sdf.format(new Date());


        switch1.setOnToggledListener(new OnToggledListener() {
            @Override
            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
                if(isOn==true){
                    getshopNotificationsUpdate("Yes");
                }else{
                    getshopNotificationsUpdate("No");
                }
            }
        });

        btn_add_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProductActivity.class));
            }
        });

        img_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();

            }
        });

        txt_durl.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override

            public void onClick(View v) {
                StaticVariables.WebPageType =url;
                CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();
                //customIntent.setToolbarColor(context.getColor(R.color.colorPrimaryDark));
                openCustomTab(getActivity(), customIntent.build(), Uri.parse(url));
                /*startActivity(new Intent(getActivity(), WebViewActivity.class));*/

            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {
                String selected_val=spinner.getSelectedItem().toString();
                if(selected_val.equals("Life Time")){
                    fliterType="LT";
                    getproductList1();
                }else if(selected_val.equals("Today")){
                    fliterType="TD";
                    getproductList1();
                }else if(selected_val.equals("Yesterday")){
                    fliterType="YD";
                    getproductList1();
                }else if(selected_val.equals("This Week")){
                    fliterType="TW";
                    getproductList1();
                }else if(selected_val.equals("This Month")){
                    fliterType="TM";
                    getproductList1();
                }else if(selected_val.equals("Custom range")){
                    fliterType="CR";
                    show_date_picker();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });


        btn_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(sessionManager.getPreferences(getActivity(),"d_url")); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        if(sessionManager.getPreferences(getActivity(),"p_status").equals("1")){
            card_profile.setVisibility(View.GONE);
            card_addproduct.setVisibility(View.GONE);
            scrollview.setVisibility(View.VISIBLE);
        }else {
            card_profile.setVisibility(View.VISIBLE);
            card_addproduct.setVisibility(View.VISIBLE);
            scrollview.setVisibility(View.GONE);
        }

        SemiCircleProgressBarView semiCircleProgressBarView = (SemiCircleProgressBarView) view.findViewById(R.id.progress);
        semiCircleProgressBarView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        semiCircleProgressBarView.setClipping(70);

        SemiCircleProgressBarViewGray semiCircleProgressBarView1 = (SemiCircleProgressBarViewGray) view.findViewById(R.id.progress1);
        semiCircleProgressBarView1.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        semiCircleProgressBarView1.setClipping(100);

        //  getproductList1();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getproductList1();
                swipe.setRefreshing(false);
            }
        });
        return view;

    }

    private  void share() {
        Intent intent = new Intent(android.content.Intent.ACTION_SEND);
        String shareBody = "http://www.webzon.in/\n" +
                "\n" +
                "Hello!\n" +
                "\n" +
                "We have launched our online store and if you would like to check our online catalogue, please visit: \n" +
                sessionManager.getPreferences(getActivity(), "d_url")+
                "\n" +
                "If you have any questions about ordering online, please call us on +91-"+sessionManager.getPreferences(getActivity(), "phone_number")+" and we would be happy to help you.\n" +
                "\n" +
                "Thank you\n" +
                sessionManager.getPreferences(getActivity(), "b_name");
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hii");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(intent, "Webzon"));
    }



    private final CalendarListener calendarListener = new CalendarListener() {
        @Override
        public void onFirstDateSelected(@NonNull final Calendar startDate) {
            Toast.makeText(getActivity(), "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onDateRangeSelected(@NonNull final Calendar startDate, @NonNull final Calendar endDate) {
            Toast.makeText(getActivity(), "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getproductList1();
    }




    public static void openCustomTab(Activity activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

    private void getshopNotificationsUpdate(String status) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("user_id", sessionManager.getPreferences(getActivity(),"user_id"));
        hashMap.put("shop_id",sessionManager.getPreferences(getActivity(),"s_id"));
        hashMap.put("online",status);
        hashMap.put("lock_screen",sessionManager.getPreferences(getActivity(),"lock_screen"));
        hashMap.put("whatsapp_chat",sessionManager.getPreferences(getActivity(),"lock_screen"));
        hashMap.put("shop_open_time",currentDateandTime);

        WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        sessionManager.setPreferences(getActivity(),"whatappchat",jsonObect.getJSONObject("data").getJSONObject("business").getString("whatsapp_chat"));
                        sessionManager.setPreferences(getActivity(),"lock_screen",jsonObect.getJSONObject("data").getJSONObject("business").getString("lock_screen"));
                        sessionManager.setPreferences(getActivity(),"online",jsonObect.getJSONObject("data").getJSONObject("business").getString("online"));
                        if(status.equals("Yes")){
                            SnackBar.returnFlashBar(getActivity(),"Online");
                        }else{
                            SnackBar.returnFlashBar(getActivity(),"Offline");
                        }

                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(getActivity(), DilogActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.shopNotificationsUpdate);
    }

    private void getproductList1() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(getActivity(),"user_id"));
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("filter",fliterType);
        hashMap.put("start_date",s_date1);
        hashMap.put("end_date",e_date1);

        WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if(jsonObect.getString("status").equals("200")){
                        status="1";
                        SpannableString content = new SpannableString(jsonObect.getJSONObject("data").getJSONObject("business").getString("slug"));
                        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                        txt_durl.setText(content);
                        url= jsonObect.getJSONObject("data").getJSONObject("business").getString("store");
                        Log.e("Url  ",url);
                        slug= jsonObect.getJSONObject("data").getJSONObject("business").getString("slug");
                        //  txt_durl.setText(response.getJSONObject("data").getJSONObject("business").getString("store"));
                        sessionManager.setPreferences(getActivity(), "b_name", jsonObect.getJSONObject("data").getJSONObject("business").getString("name"));
                        sessionManager.setPreferences(getActivity(), "c_id", jsonObect.getJSONObject("data").getJSONObject("business").getString("category_id"));
                        sessionManager.setPreferences(getActivity(), "s_id", jsonObect.getJSONObject("data").getJSONObject("business").getString("id"));
                        sessionManager.setPreferences(getActivity(), "phone_number", jsonObect.getJSONObject("data").getString("phone_number"));
                        sessionManager.setPreferences(getActivity(), "photo", jsonObect.getJSONObject("data").getString("photo"));
                        title.setText(jsonObect.getJSONObject("data").getJSONObject("business").getString("name"));
                        sessionManager.setPreferences(getActivity(), "d_url", jsonObect.getJSONObject("data").getJSONObject("business").getString("store"));
                        sessionManager.setPreferences(getActivity(), "image", jsonObect.getJSONObject("data").getJSONObject("business").getString("photo"));
                        sessionManager.setPreferences(getActivity(), "address", jsonObect.getJSONObject("data").getJSONObject("business").getString("address"));
                        sessionManager.setPreferences(getActivity(), "b_category", jsonObect.getJSONObject("data").getJSONObject("business").getJSONObject("category").getString("title"));
                        sessionManager.setPreferences(getActivity(),"whatappchat",jsonObect.getJSONObject("data").getJSONObject("business").getString("whatsapp_chat"));
                        sessionManager.setPreferences(getActivity(),"online",jsonObect.getJSONObject("data").getJSONObject("business").getString("online"));
                        sessionManager.setPreferences(getActivity(),"lock_screen",jsonObect.getJSONObject("data").getJSONObject("business").getString("lock_screen"));

                        if(sessionManager.getPreferences(getActivity(),"online").equals("Yes")){
                            switch1.setOn(true);
                        }else{
                            switch1.setOn(false);
                        }

                        txt_order.setText(jsonObect.getString("order"));
                        txt_sale.setText("\u20B9 "+jsonObect.getString("sale"));
                        txt_sviews.setText(jsonObect.getString("view"));
                        txt_pviews.setText(jsonObect.getString("product"));
                        JSONArray jsonArray1 = jsonObect.getJSONArray("banner");
                        for (int i =0 ; i<jsonArray1.length(); i++){
                            bannerModel = new BannerModel();
                            JSONObject jsonObject = jsonArray1.getJSONObject(i);
                            bannerModel.setId(jsonObject.getString("id"));
                            bannerModel.setTitle(jsonObject.getString("title"));
                            bannerModel.setDescription(jsonObject.getString("description"));
                            bannerModel.setSlug(jsonObject.getString("slug"));
                            bannerModel.setPhoto(jsonObject.getString("photo"));
                            bannerlist.add(bannerModel);
                        }
                        try{
                            if(bannerlist.size()>0){
                                blogListAdapter = new SliderAdapter(bannerlist, baseActivity);
                                sliderRV.setAdapter(blogListAdapter);
                            }
                        }catch (NullPointerException e){e.printStackTrace();}

                        if(jsonObect.getString("products").equals("Yes")){
                            sessionManager.setPreferences(getActivity(), "p_status", "1");
                        }else{
                            sessionManager.setPreferences(getActivity(), "p_status", "0");
                        }

                        //  sessionManager.setPreferences(getActivity(), "p_status", modelSingleBlog.get());
                        if(jsonObect.getString("products").equals("Yes")){
                            card_profile.setVisibility(View.GONE);
                            card_addproduct.setVisibility(View.GONE);
                            scrollview.setVisibility(View.VISIBLE);
                        }else {
                            card_profile.setVisibility(View.VISIBLE);
                            card_addproduct.setVisibility(View.VISIBLE);
                            scrollview.setVisibility(View.GONE);
                        }
                    }
                    else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(getActivity(), DilogActivity.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.HomePageData);
    }

    public void show_date_picker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                s_date1=sdf.format(calendar.getTime());
                show_date_picker1();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 0);
        //dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide past date,month and year
        dialog.show();
    }

    public void show_date_picker1() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                e_date1=sdf.format(calendar.getTime());
                getproductList1();
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 0);
        //dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
        dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide past date,month and year
        dialog.show();
    }
}

//