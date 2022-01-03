package com.webzon.Activity.Manage;



import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.R;
import com.webzon.utils.SessionManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TaxesActivity extends CustomActivity {
    @BindView(R.id.btn_create_tax) Button btn_create_tax;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes);
        ButterKnife.bind(this);
        setupActionBar("Tax",true);

        btn_create_tax.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // bottomdilogbox();
            }
        });


    }

    private void bottomdilogbox() {
        Button btn_submit;
        ImageView close;
        View view = getLayoutInflater().inflate(R.layout.taxes_items, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();
        close =view.findViewById(R.id.close);
        btn_submit =view.findViewById(R.id.btn_submit);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }


}