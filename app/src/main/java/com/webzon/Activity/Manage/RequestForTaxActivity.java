package com.webzon.Activity.Manage;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.webzon.Activity.CustomActivity;
import com.webzon.Model.ModelBankDetails;
import com.webzon.R;
import com.webzon.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;



public class RequestForTaxActivity extends CustomActivity {
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
    @BindView(R.id.txt_dob) TextInputEditText txt_dob;

    /////////////////////////////////////////////////////
    ArrayList<ModelBankDetails> list =new ArrayList<ModelBankDetails>();
    ModelBankDetails modelBankDetails;
    BankDetailsAdapter bankDetailsAdapter;
    RequestForTaxActivity baseActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_for_tax);
        ButterKnife.bind(this);
        setupActionBar("Tax",true);

        rev_bank.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rev_bank.setHasFixedSize(true);
       /* li_pan_details.setVisibility(View.VISIBLE);
        li_kyc.setVisibility(View.GONE);
        li_bankDetails.setVisibility(View.GONE);
        img_add.setVisibility(View.GONE);*/

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(li_pan_details.getVisibility() == View.VISIBLE) {
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
                    li_pan_details.setVisibility(View.GONE);
                    li_kyc.setVisibility(View.GONE);
                    li_bankDetails.setVisibility(View.VISIBLE);
                    img_add.setVisibility(View.VISIBLE);
                    next.setText("Submit");
                }
                else if(li_bankDetails.getVisibility() == View.VISIBLE) {
                    img_add.setVisibility(View.VISIBLE);
                }
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
                modelBankDetails = new ModelBankDetails();
                modelBankDetails.setB_name("");
                modelBankDetails.setC_name("");
                modelBankDetails.setConfirm_a_nmuber("");
                modelBankDetails.setIfsc("");
                modelBankDetails.setBranch("");
                modelBankDetails.setA_type("");
                list.add(modelBankDetails);
                bankDetailsAdapter = new BankDetailsAdapter(list, baseActivity);
                rev_bank.setAdapter(bankDetailsAdapter);
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


            holder.txt_bname.setText(data.get(position).getB_name());
            holder.txt_hname.setText(data.get(position).getC_name());
            holder.txt_anumber.setText(data.get(position).getA_number());
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

}

