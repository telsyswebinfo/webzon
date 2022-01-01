package com.webzon.Fragment;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.flyco.tablayout.SegmentTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.webzon.utils.ViewFindUtils;

import webzon.R;

public class ProductsFragment extends Fragment {
    View view;
    private String mParam1;
    private String mParam2;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String[] mTitles_2 = {"PRODUCTS", "CATEGORY"};

    public static ProductsFragment newInstance(String param1, String param2) {
        ProductsFragment fragment = new ProductsFragment();
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
        view = inflater.inflate(R.layout.fragment_products, container, false);
        SegmentTabLayout tabLayout_2 = ViewFindUtils.find(view, R.id.tl_2);
        tabLayout_2.setTabData(mTitles_2);

        tabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==0){
                    openFragment(P_productsFragment.newInstance("", ""));
                }
                else if(position==1){
                    openFragment(P_CategoryFragment.newInstance("", ""));
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });


        openFragment(P_productsFragment.newInstance("", ""));

        return view;
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container1, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
       // showPopup(view);
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
}