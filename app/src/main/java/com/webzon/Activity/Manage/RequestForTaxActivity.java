package com.webzon.Activity.Manage;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.webzon.Activity.Account.EditBusinessActivity;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Model.ModelBankDetails;
import com.webzon.R;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.SessionManager;
import com.webzon.utils.SnackBar;
import com.webzon.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RequestForTaxActivity extends CustomActivity implements PaymentResultListener {
    SessionManager sessionManager = new SessionManager();
    JSONArray jsonArray = null;
    @BindView(R.id.next) Button next;
    @BindView(R.id.back) Button back;
    @BindView(R.id.li_pan_details) LinearLayout li_pan_details;
    @BindView(R.id.li_kyc) LinearLayout li_kyc;
    @BindView(R.id.li_bankDetails) LinearLayout li_bankDetails;
    @BindView(R.id.img_add) ImageView img_add;
    @BindView(R.id.rev_bank) RecyclerView rev_bank;
    /////////////////////////////////////////////////////
    @BindView(R.id.spinner) Spinner spinner;
    @BindView(R.id.txt_pannumber) TextInputEditText txt_pannumber;
    @BindView(R.id.txt_adhar) TextInputEditText txt_adhar;
    /////////////////////////////////////////////////////
    @BindView(R.id.txt_fname) TextInputEditText txt_fname;
    @BindView(R.id.txt_mname) TextInputEditText txt_mname;
    @BindView(R.id.txt_lname) TextInputEditText txt_lname;
    @BindView(R.id.txt_frname) TextInputEditText txt_frname;
    @BindView(R.id.txt_dob) TextInputEditText txt_dob;
    @BindView(R.id.txt_number) TextInputEditText txt_number;
    @BindView(R.id.txt_emmail) TextInputEditText txt_emmail;
    @BindView(R.id.txt_fnumber) TextInputEditText txt_fnumber;
    @BindView(R.id.txt_village) TextInputEditText txt_village;
    @BindView(R.id.txt_area) TextInputEditText txt_area;
    @BindView(R.id.txt_road) TextInputEditText txt_road;
    @BindView(R.id.txt_city) TextInputEditText txt_city;
    @BindView(R.id.txt_state) TextInputEditText txt_state;
    @BindView(R.id.txt_pincode) TextInputEditText txt_pincode;
    @BindView(R.id.txt_rfn) TextInputEditText txt_rfn;
    @BindView(R.id.sp_gender) Spinner sp_gender;
    @BindView(R.id.sp_bCate) Spinner sp_bCate;
    @BindView(R.id.sp_empCategory) Spinner sp_empCategory;
    @BindView(R.id.li_btype) LinearLayout li_btype;
    /////////////////////////////////////////////////////
    @BindView(R.id.txt_bname) TextInputEditText txt_bname;
    @BindView(R.id.txt_cname) TextInputEditText txt_cname;
    @BindView(R.id.txt_anumber) TextInputEditText txt_anumber;
    @BindView(R.id.txt_CAnumber) TextInputEditText txt_CAnumber;
    @BindView(R.id.txt_ifsc) TextInputEditText txt_ifsc;
    @BindView(R.id.txt_branch) TextInputEditText txt_branch;
    @BindView(R.id.sp_acType) Spinner sp_acType;
    /////////////////////////////////////////////////////
    ArrayList<ModelBankDetails> list =new ArrayList<ModelBankDetails>();
    ModelBankDetails modelBankDetails;
    BankDetailsAdapter bankDetailsAdapter;
    RequestForTaxActivity baseActivity;
    String bType="",gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_tax);
        ButterKnife.bind(this);
        setupActionBar("Tax",true);
        Checkout.preload(getApplicationContext());
        rev_bank.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rev_bank.setHasFixedSize(true);
        li_pan_details.setVisibility(View.VISIBLE);
        li_kyc.setVisibility(View.GONE);
        li_bankDetails.setVisibility(View.GONE);
        img_add.setVisibility(View.GONE);

        sp_empCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?>arg0, View view, int arg2, long arg3) {
                String selected_val=sp_empCategory.getSelectedItem().toString();
                if(selected_val.equals("Business")){
                    li_btype.setVisibility(View.VISIBLE);
                }else{
                    li_btype.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if(li_pan_details.getVisibility() == View.VISIBLE) {
                    if(txt_pannumber.getText().toString().equals("")){
                        txt_pannumber.setError("Please Enter PAN Number");
                    }
                    else if(Utils.isPanCardValid(txt_pannumber.getText().toString())==false){
                        txt_pannumber.setError("Please Enter Valid PAN Number");
                    }
                    else if(Utils.validateAadharNumber(txt_adhar.getText().toString())==false){
                        txt_adhar.setError("Please Enter Aadhar Number");
                    }
                    else if(Utils.validateAadharNumber(txt_adhar.getText().toString())==false){
                        txt_adhar.setError("Please Enter Valid Aadhar Number");
                    }else{
                        li_pan_details.setVisibility(View.GONE);
                        li_kyc.setVisibility(View.VISIBLE);
                        li_bankDetails.setVisibility(View.GONE);
                        img_add.setVisibility(View.GONE);
                        next.setText("Next");
                    }

                }
                else if(li_kyc.getVisibility() == View.VISIBLE) {

                    if(txt_fname.getText().toString().equals("")){
                        txt_fname.setError("Please Enter First Name");
                    }
                    else if(txt_lname.getText().toString().equals("")){
                        txt_lname.setError("Please Enter Last Name");
                    }
                    else if(txt_frname.getText().toString().equals("")){
                        txt_frname.setError("Please Enter Father's Name");
                    }
                    else if(txt_dob.getText().toString().equals("")){
                        txt_dob.setError("Please Select DOB");
                    }
                    else if(txt_number.getText().toString().equals("")){
                        txt_number.setError("Please Enter Phone Number");
                    }
                    else if(txt_emmail.getText().toString().equals("")){
                        txt_emmail.setError("Please Enter email");
                    }
                    else if(txt_fnumber.getText().toString().equals("")){
                        txt_fnumber.setError("Please Enter Flat number");
                    }
                    else if(txt_village.getText().toString().equals("")){
                        txt_village.setError("Please Enter Village name");
                    }
                    else if(txt_area.getText().toString().equals("")){
                        txt_area.setError("Please Enter Area");
                    }
                    else if(txt_road.getText().toString().equals("")){
                        txt_road.setError("Please Enter Road");
                    }
                    else if(txt_city.getText().toString().equals("")){
                        txt_city.setError("Please Enter City");
                    }
                    else if(txt_state.getText().toString().equals("")){
                        txt_state.setError("Please Enter State");
                    }
                    else if(txt_pincode.getText().toString().equals("")){
                        txt_pincode.setError("Please Enter Pincode");
                    }
                    else{
                        li_pan_details.setVisibility(View.GONE);
                        li_kyc.setVisibility(View.GONE);
                        li_bankDetails.setVisibility(View.VISIBLE);
                        img_add.setVisibility(View.VISIBLE);
                        next.setText("Submit");

                    }
                }
                else if(li_bankDetails.getVisibility() == View.VISIBLE) {
                    if(list.size()>0){
                        apiData();
                    }else {
                        if(txt_bname.getText().toString().equals("")){
                            txt_bname.setError("Please Enter Bank Name");
                        }
                        else if(txt_cname.getText().toString().equals("")){
                            txt_cname.setError("Please Enter Customer Name");
                        }
                        else if(txt_anumber.getText().toString().equals("")){
                            txt_anumber.setError("Please Enter Account Number");
                        }
                        else if(txt_CAnumber.getText().toString().equals("")){
                            txt_CAnumber.setError("Please Enter Confirm Account Number");
                        }
                        else if(!txt_CAnumber.getText().toString().equals(txt_anumber.getText().toString())){
                            txt_CAnumber.setError("Please Enter  Valid Account Number");
                            txt_anumber.setError("Please Enter  Valid Account Number");
                        }
                        else if(txt_ifsc.getText().toString().equals("")){
                            txt_ifsc.setError("Please Enter IFSC Code");
                        }
                        else if(txt_branch.getText().toString().equals("")){
                            txt_branch.setError("Please Enter Branch");
                        }
                        else {
                            apiData();
                        }
                    }


                }*/
                startPayment();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(li_kyc.getVisibility() == View.GONE) {
                    li_pan_details.setVisibility(View.GONE);
                    li_kyc.setVisibility(View.VISIBLE);
                    li_bankDetails.setVisibility(View.GONE);
                    img_add.setVisibility(View.GONE);
                    next.setText("Next");
                }
                else if(li_pan_details.getVisibility() == View.GONE) {
                    li_pan_details.setVisibility(View.VISIBLE);
                    li_kyc.setVisibility(View.GONE);
                    li_bankDetails.setVisibility(View.GONE);
                    img_add.setVisibility(View.GONE);
                    next.setText("Next");
                }

                else if(li_bankDetails.getVisibility() == View.VISIBLE) {
                    img_add.setVisibility(View.VISIBLE);
                }
            }
        });

        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txt_bname.getText().toString().equals("")){
                    txt_bname.setError("Please Enter Bank Name");
                }
                else if(txt_cname.getText().toString().equals("")){
                    txt_cname.setError("Please Enter Customer Name");
                }
                else if(txt_anumber.getText().toString().equals("")){
                    txt_anumber.setError("Please Enter Account Number");
                }
                else if(txt_CAnumber.getText().toString().equals("")){
                    txt_CAnumber.setError("Please Enter Confirm Account Number");
                }
                else if(!txt_CAnumber.getText().toString().equals(txt_anumber.getText().toString())){
                    txt_CAnumber.setError("Please Enter  Valid Account Number");
                    txt_anumber.setError("Please Enter  Valid Account Number");
                }
                else if(txt_ifsc.getText().toString().equals("")){
                    txt_ifsc.setError("Please Enter IFSC Code");
                }
                else if(txt_branch.getText().toString().equals("")){
                    txt_branch.setError("Please Enter Branch");
                }
                else{
                    modelBankDetails = new ModelBankDetails();
                    modelBankDetails.setBank_name(txt_bname.getText().toString());
                    modelBankDetails.setCustomer_name(txt_cname.getText().toString());
                    modelBankDetails.setAccount_number(txt_anumber.getText().toString());
                    modelBankDetails.setIfsc_code(txt_ifsc.getText().toString());
                    modelBankDetails.setBranch(txt_branch.getText().toString());
                    modelBankDetails.setAccount_type(sp_acType.getSelectedItem().toString());
                    list.add(modelBankDetails);
                    bankDetailsAdapter = new BankDetailsAdapter(list, baseActivity);
                    rev_bank.setAdapter(bankDetailsAdapter);
                    txt_bname.setText("");
                    txt_cname.setText("");
                    txt_anumber.setText("");
                    txt_CAnumber.setText("");
                    txt_ifsc.setText("");
                    txt_branch.setText("");

                        Gson gson = new Gson();
                        String listString = gson.toJson(list, new TypeToken<ArrayList<ModelBankDetails>>() {}.getType());

                        try {
                            jsonArray = new JSONArray(listString);
                            Log.e("myCustomArray  ",jsonArray+"");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                }
            }
        });

        txt_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_date_picker();
            }
        });


    }

    public class BankDetailsAdapter extends RecyclerView.Adapter<BankDetailsAdapter.MyViewHolder> {
        private ArrayList<ModelBankDetails> data;
        public  CardView cardview_cat;
        Context context;
        RequestForTaxActivity baseActivity;
        int type;




        public BankDetailsAdapter(ArrayList<ModelBankDetails> data, RequestForTaxActivity mContext) {
            this.data = data;
            this.context = context;
            this.baseActivity = mContext;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView txt_bname,txt_hname,txt_anumber;
            ImageView img_delete;


            public MyViewHolder(View view) {
                super(view);
                context = itemView.getContext();
                txt_bname = itemView.findViewById(R.id.txt_bname);
                txt_hname = itemView.findViewById(R.id.txt_hname);
                txt_anumber = itemView.findViewById(R.id.txt_anumber);
                img_delete = itemView.findViewById(R.id.img_delete);
            }
        }
        @Override
        public BankDetailsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.bank_row, parent, false);
            return new BankDetailsAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(BankDetailsAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {


            holder.txt_bname.setText(data.get(position).getBank_name());
            holder.txt_hname.setText(data.get(position).getCustomer_name());
            holder.txt_anumber.setText(data.get(position).getAccount_number());
            holder.img_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        list.remove(position);
                        bankDetailsAdapter = new BankDetailsAdapter(list, baseActivity);
                        rev_bank.setAdapter(bankDetailsAdapter);
                    }catch (Exception e){}
                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }



    }

    public void show_date_picker() {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker arg0, int year, int month, int day_of_month) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, (month));
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month);
                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
                // show_date_picker=sdf.format(calendar.getTime());
                txt_dob.setText(sdf.format(calendar.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.add(Calendar.YEAR, 0);
        //dialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());// TODO: used to hide future date,month and year
        // dialog.getDatePicker().setMinDate(calendar.getTimeInMillis());// TODO: used to hide past date,month and year
        dialog.show();
    }

    private void apiData() {
        if(li_btype.getVisibility() == View.VISIBLE) {
            bType=sp_empCategory.getSelectedItem().toString();
        }else{
            bType="";
        }
        if(sp_gender.getSelectedItem().toString().equals("Male")){
            gender ="male";
        }else {
            gender ="female";
        }
        Log.e("1 ",spinner.getSelectedItem().toString());
        Log.e("2 ",sp_empCategory.getSelectedItem().toString());
        Log.e("3 ",sp_gender.getSelectedItem().toString());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("shop_id", sessionManager.getPreferences(this,"s_id"));
        hashMap.put("financial_year",spinner.getSelectedItem().toString());
        hashMap.put("pan_nmuber",txt_pannumber.getText().toString());
        hashMap.put("first_name",txt_fname.getText().toString());
        hashMap.put("middle_name",txt_mname.getText().toString());
        hashMap.put("bank_detail", String.valueOf(jsonArray));
        hashMap.put("last_name", txt_lname.getText().toString());
        hashMap.put("dob", txt_dob.getText().toString());
        hashMap.put("mobile_number",txt_number.getText().toString());
        hashMap.put("email",txt_emmail.getText().toString());
        hashMap.put("device_id",StaticVariables.DeviceID);
        hashMap.put("device_token",StaticVariables.Token);
        hashMap.put("device_type",StaticVariables.DeviceType);
        hashMap.put("flat_nmuber",txt_fnumber.getText().toString());
        hashMap.put("village", txt_village.getText().toString());
        hashMap.put("area",txt_area.getText().toString());
        hashMap.put("road",txt_road.getText().toString());
        hashMap.put("city",txt_city.getText().toString());
        hashMap.put("state",txt_state.getText().toString());
        hashMap.put("pincode",txt_pincode.getText().toString());
        hashMap.put("employer_category",sp_empCategory.getSelectedItem().toString());
        hashMap.put("business_category",bType);
        hashMap.put("aadhar_card_number",txt_adhar.getText().toString());
        hashMap.put("father_name",txt_frname.getText().toString());
        hashMap.put("gender",gender);
        hashMap.put("reference_number",txt_rfn.getText().toString());

        WebServiceHandler webServiceHandler = new WebServiceHandler(RequestForTaxActivity.this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        SnackBar.returnFlashBar(RequestForTaxActivity.this,jsonObect.getString("message"));
                        startPayment();
                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(RequestForTaxActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        SnackBar.returnFlashBar(RequestForTaxActivity.this,"message");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.itrFill);
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        /**
         * Add your logic here for a successful payment response
         */
        onBackPressed();
    }

    @Override
    public void onPaymentError(int code, String response) {
        /**
         * Add your logic here for a failed payment response
         */
    }

    public void startPayment() {
        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_NphpFIpuKNfVHy");
        /**
         * Set your logo here
         */
        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            options.put("amount", 1); // amount in the smallest currency unit
            options.put("currency", "INR");
            options.put("theme.color", "#2b405b");
            options.put("receipt", "order_rcptid_11");
            checkout.open(activity, options);
           /* JSONObject options = new JSONObject();
            options.put("name", "Merchant Name");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","9988776655");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);*/

        } catch(Exception e) {
            Log.e("3", "Error in starting Razorpay Checkout", e);
        }
    }
}

