package com.webzon.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.Product.CreateCategoryActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Adapter.OrderCategoryAdapter;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Model.ProductCategoryModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.SessionManager;
import com.webzon.utils.productListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import webzon.R;

public class P_CategoryFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Switch btn_stock;
    SessionManager sessionManager = new SessionManager();
    RecyclerView rev_cate;
    LinearLayout li_addcategory,lay_nodata;
    OrderCategoryAdapter categoryAdapter;
    private ArrayList<productListModel.Category.Datum__1> categoryData;
    private ArrayList<ProductCategoryModel> categoryData1 = new ArrayList<>();
    HomeActivity2 baseActivity;
    TextView txt_loadMore;
    SwipeRefreshLayout swipe;
    ProductCategoryModel productCategoryModel;
    public static P_CategoryFragment newInstance(String param1, String param2) {
        P_CategoryFragment fragment = new P_CategoryFragment();
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
        view = inflater.inflate(R.layout.fragment_p_cat, container, false);
        // firstButton = (Button) view.findViewById(R.id.firstButton);
         li_addcategory =view.findViewById(R.id.li_addcategory);
        lay_nodata =view.findViewById(R.id.lay_nodata);
        swipe =view.findViewById(R.id.swipe);


        rev_cate =view.findViewById(R.id.rev_cate);
        rev_cate.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rev_cate.setHasFixedSize(true);


       // getproductList1();

        li_addcategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CreateCategoryActivity.class));
            }
        });

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

    private void bottomdilog() {
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.stock_bottom_sheet, null);
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity(),R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    private void getproductList1() {
        categoryData1.clear();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(getActivity(),"user_id"));
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("type","shop");
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("filter","LT");
        hashMap.put("start_date","");
        hashMap.put("end_date","");

        WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        //ArrayList<ProductCategoryModel> categoryData1;
                        //ProductCategoryModel productCategoryModel;

                        JSONObject object =new JSONObject(jsonObect.getString("category"));
                        Log.e("jsonObject123 ",object.getString("next_page_url")+"Hello");
                        JSONArray jsonArray = object.getJSONArray("data");

                        // Log.e("Data  ",jsonArray.toString());
                        for (int i =0 ; i<jsonArray.length(); i++){
                            productCategoryModel = new ProductCategoryModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("jsonObject ",jsonObject+"");
                            productCategoryModel.setId(jsonObject.getString("id"));
                            productCategoryModel.setSlug(jsonObject.getString("slug"));
                            productCategoryModel.setTitlle(jsonObject.getString("title"));
                            productCategoryModel.setPhoto(jsonObject.getString("photo"));
                            productCategoryModel.setStatus(jsonObject.getString("status"));
                            categoryData1.add(productCategoryModel);
                        }

                        try {
                            if(categoryData1.size()>0){
                                lay_nodata.setVisibility(View.GONE);
                                rev_cate.setVisibility(View.VISIBLE);
                                categoryAdapter = new OrderCategoryAdapter(categoryData1, baseActivity);
                                rev_cate.setAdapter(categoryAdapter);
                                if(!object.getString("next_page_url").equals("null")){
                                    getproductList2(object.getString("next_page_url"));
                                }
                                //
                            }
                        }catch (NullPointerException e){e.printStackTrace();}
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(getActivity(), DilogActivity.class);
                        startActivity(intent);
                    }else{
                        lay_nodata.setVisibility(View.VISIBLE);
                        rev_cate.setVisibility(View.GONE);
                        // SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.productList);
    }

    private void getproductList2(String page) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(getActivity(),"user_id"));
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("type","shop");
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);
        hashMap.put("filter","LT");
        hashMap.put("start_date","");
        hashMap.put("end_date","");
        hashMap.put("page",page);

        WebServiceHandler webServiceHandler = new WebServiceHandler(getActivity());
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        //ArrayList<ProductCategoryModel> categoryData1;
                        //ProductCategoryModel productCategoryModel;

                        JSONObject object =new JSONObject(jsonObect.getString("category"));
                        Log.e("jsonObject123 ",object.getString("next_page_url")+"Hello");
                        JSONArray jsonArray = object.getJSONArray("data");

                        // Log.e("Data  ",jsonArray.toString());
                        for (int i =0 ; i<jsonArray.length(); i++){
                            productCategoryModel = new ProductCategoryModel();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Log.e("jsonObject ",jsonObject+"");
                            productCategoryModel.setId(jsonObject.getString("id"));
                            productCategoryModel.setSlug(jsonObject.getString("slug"));
                            productCategoryModel.setTitlle(jsonObject.getString("title"));
                            productCategoryModel.setPhoto(jsonObject.getString("photo"));
                            productCategoryModel.setStatus(jsonObject.getString("status"));
                            categoryData1.add(productCategoryModel);
                        }

                        try {
                            if(categoryData1.size()>0){
                                if(!object.getString("next_page_url").equals("null")){
                                    getproductList2(object.getString("next_page_url"));
                                }
                                //
                            }
                        }catch (NullPointerException e){e.printStackTrace();}
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(getActivity(), DilogActivity.class);
                        startActivity(intent);
                    }else{
                        lay_nodata.setVisibility(View.VISIBLE);
                        rev_cate.setVisibility(View.GONE);
                        // SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.NewPost(hashMap, ServicesUtils.productList);
    }

    @Override
    public void onResume() {
        super.onResume();
        getproductList1();
    }


}