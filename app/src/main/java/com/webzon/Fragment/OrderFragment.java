package com.webzon.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.webzon.Activity.DilogActivity;
import com.webzon.Activity.Order.PendingOrderActivity;
import com.webzon.Adapter.OrderTypeListAdapter;
import com.webzon.Model.orderListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.RecyclerItemClickListener;
import com.webzon.utils.SessionManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import webzon.R;

public class OrderFragment extends Fragment{
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ArrayList<String> orderType=new ArrayList<>();
    public static String tab_pos = "All";
    RecyclerView rec_orders_tab,rec_orderslist;
    OrderFragment baseActivity;
    private ArrayList<orderListModel> ordeListdata = new ArrayList<>();
    OrderTypeAdapter orderTypeAdapter;
    SessionManager sessionManager = new SessionManager();
    orderListModel orderListModel1;
    LinearLayout lay_nodata;
    OrderTypeListAdapter orderTypeListAdapter;
    TextView title;
    SwipeRefreshLayout swipe;
    public String type ="all";


    public static OrderFragment newInstance(String param1, String param2) {
        OrderFragment fragment = new OrderFragment();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order, container, false);
        rec_orderslist =view.findViewById(R.id.rec_orderslist);
        lay_nodata =view.findViewById(R.id.lay_nodata);
        title =view.findViewById(R.id.title);
        swipe =view.findViewById(R.id.swipe);
        rec_orderslist.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rec_orderslist.setHasFixedSize(true);
        title.setText("Order");




        rec_orders_tab =view.findViewById(R.id.rec_orders_tab);
        rec_orders_tab.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rec_orders_tab.setHasFixedSize(true);
        orderType.clear();
        orderTypeAdapter = new OrderTypeAdapter(orderType, baseActivity);
        rec_orders_tab.setAdapter(orderTypeAdapter);

/*        rec_orders_tab.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), rec_orders_tab, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String list = orderType.get(position);
                        orderTypeAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );*/


        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getproductList1(type);
                swipe.setRefreshing(false);
            }
        });

        rec_orderslist.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), rec_orderslist, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try {
                            String list = ordeListdata.get(position).getCurrency();
                            StaticVariables.O_Id=ordeListdata.get(position).getId();
                            startActivity(new Intent(getActivity(), PendingOrderActivity.class));
                        }catch (IndexOutOfBoundsException e){e.printStackTrace();}
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );





        orderType.add("All");
        orderType.add("Pending");
        orderType.add("Accepted");
        orderType.add("Shipped");
        orderType.add("Delivered");
        orderType.add("Rejected");
        orderType.add("Cancelled");

        // getproductList1("all");
        return view;
    }



    public void showPopup(View v) {


        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup_filter_layout, null);

        PopupWindow popupWindow = new PopupWindow(
                popupView,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                //TODO do sth here on dismiss
            }
        });

        popupWindow.showAsDropDown(v);
    }

    public void getproductList1(String type){
        ordeListdata.clear();
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.OrderList)
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(getActivity(),"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(getActivity(),"s_id"))
                .addBodyParameter("type","shop")
                .addBodyParameter("order_type",type)
                .addBodyParameter("filter","LT")
                .addBodyParameter("start_date","")
                .addBodyParameter("end_date","")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Res  ", response + "");
                            if (response.getString("status").equals("200")) {
                                JSONObject object = new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data123  ", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    orderListModel1 = new orderListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    orderListModel1.setId(jsonObject.getString("id"));
                                    orderListModel1.setOrder_no(jsonObject.getString("order_no"));
                                    orderListModel1.setOrder_no(jsonObject.getString("order_no"));
                                    orderListModel1.setOrder_time(jsonObject.getString("order_time"));
                                    orderListModel1.setOrder_items(String.valueOf(jsonObject.getJSONArray("order_items").length()));
                                    orderListModel1.setCurrency(jsonObject.getString("currency"));
                                    orderListModel1.setGrand_total(jsonObject.getString("grand_total"));
                                    orderListModel1.setPayment_method(jsonObject.getString("payment_method"));
                                    orderListModel1.setStatus(jsonObject.getString("status"));
                                    orderListModel1.setImage(String.valueOf(jsonObject.getJSONArray("order_items").getJSONObject(0).getJSONObject("product_items").getJSONArray("photo").getJSONObject(0).getString("image")));
                                    JSONArray jsonArray1 = object.getJSONArray("data");
                                    ordeListdata.add(orderListModel1);
                                }
                                try {
                                    if (ordeListdata.size() > 0) {
                                        lay_nodata.setVisibility(View.GONE);
                                        rec_orderslist.setVisibility(View.VISIBLE);
                                        orderTypeListAdapter = new OrderTypeListAdapter(ordeListdata, baseActivity);
                                        rec_orderslist.setAdapter(orderTypeListAdapter);
                                        if(!object.getString("next_page_url").equals("null")){
                                            getproductList2(type,object.getString("next_page_url"));
                                        }
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(getActivity(), DilogActivity.class);
                                startActivity(intent);
                            }else{
                                lay_nodata.setVisibility(View.VISIBLE);
                                rec_orderslist.setVisibility(View.GONE);
                                //  SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                                orderTypeListAdapter = new OrderTypeListAdapter(ordeListdata, baseActivity);
                                rec_orderslist.setAdapter(orderTypeListAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }



    public void getproductList2(String type,String page){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.OrderList)
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("user_id",sessionManager.getPreferences(getActivity(),"user_id"))
                .addBodyParameter("shop_id",sessionManager.getPreferences(getActivity(),"s_id"))
                .addBodyParameter("type","shop")
                .addBodyParameter("order_type",type)
                .addBodyParameter("filter","LT")
                .addBodyParameter("start_date","")
                .addBodyParameter("end_date","")
                .addBodyParameter("page",page)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.e("Res  ", response + "");
                            if (response.getString("status").equals("200")) {
                                JSONObject object = new JSONObject(response.getString("data"));
                                JSONArray jsonArray = object.getJSONArray("data");
                                Log.e("Data123  ", jsonArray.toString());
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    orderListModel1 = new orderListModel();
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    orderListModel1.setId(jsonObject.getString("id"));
                                    orderListModel1.setOrder_no(jsonObject.getString("order_no"));
                                    orderListModel1.setOrder_no(jsonObject.getString("order_no"));
                                    orderListModel1.setOrder_time(jsonObject.getString("order_time"));
                                    orderListModel1.setOrder_items(String.valueOf(jsonObject.getJSONArray("order_items").length()));
                                    orderListModel1.setCurrency(jsonObject.getString("currency"));
                                    orderListModel1.setGrand_total(jsonObject.getString("grand_total"));
                                    orderListModel1.setPayment_method(jsonObject.getString("payment_method"));
                                    orderListModel1.setStatus(jsonObject.getString("status"));
                                    orderListModel1.setImage(String.valueOf(jsonObject.getJSONArray("order_items").getJSONObject(0).getJSONObject("product_items").getJSONArray("photo").getJSONObject(0).getString("image")));
                                    JSONArray jsonArray1 = object.getJSONArray("data");
                                    ordeListdata.add(orderListModel1);
                                }
                                try {
                                    if (ordeListdata.size() > 0) {
                                        orderTypeListAdapter = new OrderTypeListAdapter(ordeListdata, baseActivity);
                                        rec_orderslist.setAdapter(orderTypeListAdapter);
                                        if(!object.getString("next_page_url").equals("null")){
                                            getproductList2(type,object.getString("next_page_url"));
                                        }
                                    }
                                } catch (NullPointerException e) {
                                    e.printStackTrace();
                                }
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(getActivity(), DilogActivity.class);
                                startActivity(intent);
                            }else{
                                lay_nodata.setVisibility(View.VISIBLE);
                                rec_orderslist.setVisibility(View.GONE);
                                //  SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                                orderTypeListAdapter = new OrderTypeListAdapter(ordeListdata, baseActivity);
                                rec_orderslist.setAdapter(orderTypeListAdapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Data1  ",error.toString());
                    }
                });
    }




    public class OrderTypeAdapter extends RecyclerView.Adapter<OrderTypeAdapter.MyViewHolder> {
        private ArrayList<String> data;
        Context context;
        OrderFragment baseActivity;
        int lastClickedPosition = -1;
        private int selectedItem;


        public OrderTypeAdapter(ArrayList<String> data, OrderFragment mContext) {
            this.data = data;
            this.context = context;
            this.baseActivity = mContext;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt_cat;
            CardView cardview;

            public MyViewHolder(View view) {
                super(view);
                context = itemView.getContext();
                txt_cat = itemView.findViewById(R.id.txt_cat);
                cardview = itemView.findViewById(R.id.cardview);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.ordertype_row, parent, false);
            return new MyViewHolder(itemView);
        }

        @SuppressLint("RecyclerView")
        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {

            holder.txt_cat.setText(data.get(position));
            holder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.purple_200));
            //  holder.li_raw.setBackgroundColor(context.getResources().getColor(R.color.black));


            if (selectedItem == position) {
                holder.cardview.setCardBackgroundColor(context.getResources().getColor(R.color.colorPrimaryDark));
                //  holder.li_raw.setBackgroundColor(context.getResources().getColor(R.color.purple_700));
            }

            holder.cardview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int previousItem = selectedItem;
                    selectedItem = position;
                    notifyItemChanged(previousItem);
                    notifyItemChanged(position);
                    if(data.get(position).equals("All")){
                        getproductList1("all");
                        type="all";
                    }else if(data.get(position).equals("Pending")){
                        getproductList1("PN");
                        type="PN";
                    }else if(data.get(position).equals("Accepted")){
                        getproductList1("AC");
                        type="AC";
                    }else if(data.get(position).equals("Shipped")){
                        getproductList1("SP");
                        type="SP";
                    }else if(data.get(position).equals("Delivered")){
                        getproductList1("DL");
                        type="DL";
                    }else if(data.get(position).equals("Cancelled")){
                        getproductList1("CL");
                        type="CL";
                    }else if(data.get(position).equals("Rejected")){
                        getproductList1("RN");
                        type="RN";
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        getproductList1("all");
    }
}