package com.webzon.Activity.Product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.DilogActivity;
import com.webzon.Adapter.ChooseColorAdapter;
import com.webzon.Model.Cricketer;
import com.webzon.Model.Cricketer1;
import com.webzon.Model.colorListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.RecyclerItemClickListener;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.webzon.R;

public class AddVariantsActivity extends AppCompatActivity implements View.OnClickListener  {
    LinearLayout layoutList;
    LinearLayout layoutList1;
    Button buttonAdd,button_clour;
    Button buttonSubmitList;
    SessionManager sessionManager = new SessionManager();
    List<String> teamList = new ArrayList<>();
    ArrayList<Cricketer> cricketersList = new ArrayList<>();
    ArrayList<Cricketer1> cricketersList1 = new ArrayList<>();
    ArrayList<colorListModel> clist = new ArrayList<>();
    ArrayList<String> colorId = new ArrayList<>();
    colorListModel cListModel;
    ChooseColorAdapter chooseColorAdapter;
    AddVariantsActivity baseActivity;
    Button button_submit_list;
    JSONObject object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_variants);

        button_submit_list = findViewById(R.id.button_submit_list);
        layoutList1 = findViewById(R.id.layoutList1);
        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        button_clour = findViewById(R.id.button_clour);
        buttonSubmitList = findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        button_clour.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

       getcolorList();


        button_submit_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfValidAndRead();
                checkIfValidAndRead1();
                Log.e("Data  ",StaticVariables.jsonArray+"");
                Log.e("Data  ",StaticVariables.colorId1+"");
                onBackPressed();
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:
                if(cricketersList.size()==0){
                    addView();
                }else{
                    if(checkIfValidAndRead()){
                        addView();
                    }
                }
                break;

            case  R.id.button_clour:
                if(cricketersList1.size()==0){
                    //addView1();
                    bottomdilog();
                }else{
                    if(checkIfValidAndRead1()){
                       // addView1();
                        bottomdilog();
                    }
                }
                break;

            case R.id.button_submit_list:

                if(checkIfValidAndRead()){

                   /* Intent intent = new Intent(AddVariantsActivity.this,ActivityCricketers.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("list",cricketersList);
                    intent.putExtras(bundle);
                    startActivity(intent);*/
                }
                break;
        }


    }

    private boolean checkIfValidAndRead() {
        cricketersList.clear();
        boolean result = true;
        StaticVariables.jsonArray = new JSONArray();
        for(int i=0;i<layoutList.getChildCount();i++){
            object = new JSONObject();
            View cricketerView = layoutList.getChildAt(i);

            TextInputEditText txt_size,txt_price,txt_dPrice;
            txt_size = cricketerView.findViewById(R.id.txt_size);
            txt_price = cricketerView.findViewById(R.id.txt_price);
            txt_dPrice = cricketerView.findViewById(R.id.txt_dPrice);


           /* EditText editTextName = (EditText)cricketerView.findViewById(R.id.edit_cricketer_name);
            AppCompatSpinner spinnerTeam = (AppCompatSpinner)cricketerView.findViewById(R.id.spinner_team);*/

            Cricketer cricketer = new Cricketer();

            if(!txt_size.getText().toString().equals("")){
                cricketer.setTxt_size(txt_size.getText().toString());
            }
            else if(!txt_price.getText().toString().equals("")){
                cricketer.setTxt_price(txt_price.getText().toString());
            }
            else if(!txt_dPrice.getText().toString().equals("")){
                cricketer.setTxt_dPrice(txt_dPrice.getText().toString());
            }
            else {
               result = false;
                break;
            }


            try {
                if(!txt_size.getText().toString().equals("")){
                    if(!txt_price.getText().toString().equals("")){
                        if(!txt_dPrice.getText().toString().equals("")){
                            object.put("size_name", txt_size.getText().toString());
                            object.put("price", txt_price.getText().toString());
                            object.put("special_price", txt_dPrice.getText().toString());
                            StaticVariables.jsonArray.put(object);
                        }
                    }
                }

            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            cricketersList.add(cricketer);

        }

        if(cricketersList.size()==0){
            result = false;
           // Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
           // Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private void addView() {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_size,null,false);
        TextInputEditText txt_size,txt_price,txt_dPrice;
        TextView txt_remove;
        txt_remove = cricketerView.findViewById(R.id.txt_remove);
        txt_size = cricketerView.findViewById(R.id.txt_size);
        txt_price = cricketerView.findViewById(R.id.txt_price);
        txt_dPrice = cricketerView.findViewById(R.id.txt_dPrice);
        /*EditText editText = (EditText)cricketerView.findViewById(R.id.edit_cricketer_name);
        AppCompatSpinner spinnerTeam = (AppCompatSpinner)cricketerView.findViewById(R.id.spinner_team);
        ImageView imageClose = (ImageView)cricketerView.findViewById(R.id.image_remove);*/

        /*ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,teamList);
        spinnerTeam.setAdapter(arrayAdapter);*/

        txt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(cricketerView);
            }
        });

        layoutList.addView(cricketerView);
    }

    private void removeView(View view){
        layoutList.removeView(view);
    }

    private boolean checkIfValidAndRead1() {
        cricketersList1.clear();
        colorId.clear();
        boolean result = true;
        JSONObject student = new JSONObject();
        for(int i=0;i<layoutList1.getChildCount();i++){
            TextView txt_remove,txt_Cname,txt_id;
            CardView card_ui;
            View cricketerView = layoutList1.getChildAt(i);
            txt_Cname = cricketerView.findViewById(R.id.txt_Cname);
            txt_id = cricketerView.findViewById(R.id.txt_id);
            Cricketer1 cricketer1 = new Cricketer1();
            cricketer1.setCname(txt_Cname.getText().toString());
            /*



            if(!txt_id.getText().toString().equals("")){

               // colorId.add(txt_Cname.getText().toString());
            }
            else {
                result = false;
                break;
            }
*/

            cricketersList1.add(cricketer1);
            colorId.add(txt_id.getText().toString());

        }

        String text = colorId.toString().replace("[", "").replace("]", "");
        StaticVariables.colorId1 =text;

        if(cricketersList1.size()==0){
            result = false;
         //   Toast.makeText(this, "Add Cricketers First!", Toast.LENGTH_SHORT).show();
        }else if(!result){
          //  Toast.makeText(this, "Enter All Details Correctly!", Toast.LENGTH_SHORT).show();
        }


        return result;
    }

    private void addView1(String name,String code,String id) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_colour,null,false);

        TextView txt_remove,txt_Cname,txt_id;
        CardView card_ui;
        txt_remove = cricketerView.findViewById(R.id.txt_remove);
        txt_Cname = cricketerView.findViewById(R.id.txt_Cname);
        txt_id = cricketerView.findViewById(R.id.txt_id);
        card_ui = cricketerView.findViewById(R.id.card_ui);
        txt_Cname.setText(name);
        txt_id.setText(id);
        card_ui.setBackgroundColor(Color.parseColor(code));



        txt_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView1(cricketerView);
            }
        });

        layoutList1.addView(cricketerView);

    }

    private void removeView1(View view){
        layoutList1.removeView(view);

    }


    private void getcolorList(){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.ColorList)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Data  ",response.toString());
                        try {
                            if (response.getString("status").equals("200")) {
                            JSONObject object =new JSONObject(response.getString("data"));
                            JSONArray jsonArray = object.getJSONArray("data");
                            // Log.e("Data  ",jsonArray.toString());
                            for (int i =0 ; i<jsonArray.length(); i++){
                                cListModel = new colorListModel()   ;
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                cListModel.setId(jsonObject.getString("id"));
                                cListModel.setCode(jsonObject.getString("code"));
                                cListModel.setColorname(jsonObject.getString("colorname"));
                                clist.add(cListModel);
                            }

                            if(clist.size()>0){
                                Log.e("Responce1  ",clist.size()+"");
                                //getcolorList1();
                              //  bottomdilog();

                            }
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(AddVariantsActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(AddVariantsActivity.this,response.getString("message"));
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
    private void getcolorList1(){
        AndroidNetworking.post(ApiUrl.BaseUrl+ApiUrl.ColorList)
                .addBodyParameter("user_id",sessionManager.getPreferences(this,"user_id"))
                .addBodyParameter("device_id",StaticVariables.DeviceID)
                .addBodyParameter("device_token",StaticVariables.Token)
                .addBodyParameter("device_type",StaticVariables.DeviceType)
                .addBodyParameter("page","1")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Log.e("Data  ",response.toString());
                        try {
                            if (response.getString("status").equals("200")) {
                            String title,slug,description,price,discount,cat_id;
                            JSONObject object =new JSONObject(response.getString("data"));
                            JSONArray jsonArray = object.getJSONArray("data");
                            // Log.e("Data  ",jsonArray.toString());
                            for (int i =0 ; i<jsonArray.length(); i++){
                                cListModel = new colorListModel()   ;
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                cListModel.setId(jsonObject.getString("id"));
                                cListModel.setCode(jsonObject.getString("code"));
                                cListModel.setColorname(jsonObject.getString("colorname"));
                                clist.add(cListModel);
                            }

                            Log.e("Responce  ",clist.size()+"");
                            /*if(categoryData1.size()>0){
                                Log.e("Data ",categoryData1.size()+"");
                                orderCategoryAdapter1 = new OrderProduct1Adapter(categoryData1, baseActivity);
                                rev_procuct.setAdapter(orderCategoryAdapter1);
                            }*/
                            }else if (response.getString("status").equals("403")) {
                                StaticVariables.LogoutMessage =response.getString("message");
                                Intent intent=new Intent(AddVariantsActivity.this, DilogActivity.class);
                                startActivity(intent);
                            }else{
                                SnackBar.returnFlashBar(AddVariantsActivity.this,response.getString("message"));
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



    private void bottomdilog() {
        RecyclerView rec_color;
        ImageView close;
        LayoutInflater inflater = (LayoutInflater) getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        View view = inflater.inflate(R.layout.custom_dilog, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        rec_color =view.findViewById(R.id.rec_color);
        close =view.findViewById(R.id.close);
        rec_color.setLayoutManager(new GridLayoutManager(this, 1));
        rec_color.setHasFixedSize(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
        if(clist.size()>0){
            chooseColorAdapter = new ChooseColorAdapter(clist, baseActivity);
            rec_color.setAdapter(chooseColorAdapter);
        }


        rec_color.addOnItemTouchListener(
                new RecyclerItemClickListener(AddVariantsActivity.this, rec_color, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String list = clist.get(position).getId().toString();
                        //txt_catName.setText(data2.get(position).getTitle());
                        //cId =clist.get(position).getId()+"";
                        StaticVariables.Position =position;
                       // txt_bCategory.setText(clist.get(position).getTitle().toString());
                        dialog.cancel();
                        addView1(clist.get(position).getColorname(),clist.get(position).getCode(),clist.get(position).getId());

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                })
        );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}

