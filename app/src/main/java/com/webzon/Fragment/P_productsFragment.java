package com.webzon.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.Product.AddProductActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.Adapter.OrderProduct1Adapter;
import com.webzon.Adapter.OrderProductAdapter;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Model.ProductList;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.Utlity;
import com.webzon.utils.productListModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import com.webzon.R;

public class P_productsFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rev_procuct;
    LinearLayout li_addproduct,lay_nodata;
    SessionManager sessionManager = new SessionManager();
    private ArrayList<productListModel.Product.Datum> categoryData;
    private ArrayList<ProductList> categoryData1 = new ArrayList<>();
    ProductList productList;
    SwipeRefreshLayout swipe;
    OrderProductAdapter OrderCategoryAdapter;
    OrderProduct1Adapter orderCategoryAdapter1;
    HomeActivity2 baseActivity;

    public static P_productsFragment newInstance(String param1, String param2) {
        P_productsFragment fragment = new P_productsFragment();
        try{
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        }catch (Exception e){e.printStackTrace();}
        return fragment;
    }
    public void FirstFragment(){
        // require a empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        view = inflater.inflate(R.layout.fragment_p_products, container, false);
        // firstButton = (Button) view.findViewById(R.id.firstButton);
        rev_procuct =view.findViewById(R.id.rev_procuct);
        lay_nodata =view.findViewById(R.id.lay_nodata);
        li_addproduct =view.findViewById(R.id.li_addproduct);
        swipe =view.findViewById(R.id.swipe);

        rev_procuct.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rev_procuct.setHasFixedSize(true);





        li_addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddProductActivity.class));
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


        //  getproductList();

        //getproductList();
        // getproductList1();
        return view;
    }

    private void bottomdilog() {
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.share_bottom_sheet, null);
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


    private void getproductList2(){
        categoryData1.clear();
        Utlity.show_progress(getActivity());
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.productList)
                .addBodyParameter("user_id",sessionManager.getPreferences(getActivity(),"user_id"))
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("type","shop")
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("filter","LT")
                .addBodyParameter("start_date","")
                .addBodyParameter("end_date","")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onResponse(JSONObject response) {
                         //Log.e("Data  ",response.toString());
                        Utlity.dismiss_dilog(getActivity());
                        try {
                            if (response.getString("status").equals("200")) {
                            String title,slug,description,price,discount,cat_id;
                            JSONObject object =new JSONObject(response.getString("product"));
                            JSONArray jsonArray = object.getJSONArray("data");
                           //  Log.e("Data  ",jsonArray.toString());
                            for (int i =0 ; i<jsonArray.length(); i++){
                                productList = new ProductList();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                productList.setId(jsonObject.getString("id"));
                                productList.setTitle(jsonObject.getString("title"));
                                productList.setSlug(jsonObject.getString("slug"));
                                productList.setDescription(jsonObject.getString("description"));
                                productList.setPrice(jsonObject.getString("price"));
                                productList.setDiscount(jsonObject.getString("discount"));
                                productList.setCatId(jsonObject.getString("cat_id"));
                                productList.setImg(jsonObject.getJSONArray("photo").getJSONObject(0).getString("image"));
                                productList.setStatus(jsonObject.getString("status"));
                                categoryData1.add(productList);
                            }
                            try{
                                if(categoryData1.size()>0){
                                    lay_nodata.setVisibility(View.GONE);
                                    rev_procuct.setVisibility(View.VISIBLE);
                                    Log.e("Data ",categoryData1.size()+"");
                                    orderCategoryAdapter1 = new OrderProduct1Adapter(categoryData1, baseActivity);
                                    rev_procuct.setAdapter(orderCategoryAdapter1);
                                    if(!object.getString("next_page_url").equals("null")){
                                        getproductList2(object.getString("next_page_url"));
                                    }
                                }
                            }catch (NullPointerException e){e.printStackTrace();}
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(getActivity(), DilogActivity.class);
                                startActivity(intent);
                            }else{
                                lay_nodata.setVisibility(View.VISIBLE);
                                rev_procuct.setVisibility(View.GONE);
                               // SnackBar.returnFlashBar(getActivity(),response.getString("message"));
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


    private void getproductList1() {
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
                        String title,slug,description,price,discount,cat_id;
                        JSONObject object =new JSONObject(jsonObect.getString("product"));
                        JSONArray jsonArray = object.getJSONArray("data");
                        //  Log.e("Data  ",jsonArray.toString());
                        for (int i =0 ; i<jsonArray.length(); i++){
                            productList = new ProductList();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            productList.setId(jsonObject.getString("id"));
                            productList.setTitle(jsonObject.getString("title"));
                            productList.setSlug(jsonObject.getString("slug"));
                            productList.setDescription(jsonObject.getString("description"));
                            productList.setPrice(jsonObject.getString("price"));
                            productList.setDiscount(jsonObject.getString("discount"));
                            productList.setCatId(jsonObject.getString("cat_id"));
                            productList.setImg(jsonObject.getJSONArray("photo").getJSONObject(0).getString("image"));
                            productList.setStatus(jsonObject.getString("status"));
                            categoryData1.add(productList);
                        }
                        try{
                            if(categoryData1.size()>0){
                                lay_nodata.setVisibility(View.GONE);
                                rev_procuct.setVisibility(View.VISIBLE);
                                Log.e("Data ",categoryData1.size()+"");
                                orderCategoryAdapter1 = new OrderProduct1Adapter(categoryData1, baseActivity);
                                rev_procuct.setAdapter(orderCategoryAdapter1);
                                if(!object.getString("next_page_url").equals("null")){
                                    getproductList2(object.getString("next_page_url"));
                                }
                            }
                        }catch (NullPointerException e){e.printStackTrace();}
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(getActivity(), DilogActivity.class);
                        startActivity(intent);
                    }else{
                        lay_nodata.setVisibility(View.VISIBLE);
                        rev_procuct.setVisibility(View.GONE);
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
                        String title,slug,description,price,discount,cat_id;
                        JSONObject object =new JSONObject(jsonObect.getString("product"));
                        JSONArray jsonArray = object.getJSONArray("data");
                        //  Log.e("Data  ",jsonArray.toString());
                        for (int i =0 ; i<jsonArray.length(); i++){
                            productList = new ProductList();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            productList.setId(jsonObject.getString("id"));
                            productList.setTitle(jsonObject.getString("title"));
                            productList.setSlug(jsonObject.getString("slug"));
                            productList.setDescription(jsonObject.getString("description"));
                            productList.setPrice(jsonObject.getString("price"));
                            productList.setDiscount(jsonObject.getString("discount"));
                            productList.setCatId(jsonObject.getString("cat_id"));
                            productList.setImg(jsonObject.getJSONArray("photo").getJSONObject(0).getString("image"));
                            productList.setStatus(jsonObject.getString("status"));
                            categoryData1.add(productList);
                        }
                        try{
                            if(categoryData1.size()>0){
                                orderCategoryAdapter1 = new OrderProduct1Adapter(categoryData1, baseActivity);
                                rev_procuct.setAdapter(orderCategoryAdapter1);
                                if(!object.getString("next_page_url").equals("null")){
                                    getproductList2(object.getString("next_page_url"));
                                }
                            }
                        }catch (NullPointerException e){e.printStackTrace();}
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(getActivity(), DilogActivity.class);
                        startActivity(intent);
                    }else{
                        lay_nodata.setVisibility(View.VISIBLE);
                        rev_procuct.setVisibility(View.GONE);
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
        // getproductList();
        getproductList1();
    }

}
