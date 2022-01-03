package com.webzon.Activity.Manage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.webzon.Activity.CustomActivity;
import com.webzon.Activity.HomeActivity2;
import com.webzon.R;
import com.webzon.utils.SessionManager;

import butterknife.BindView;

public class TaxesActivity extends CustomActivity {
    @BindView(R.id.btn_create_tax) Button btn_create_tax;
    SessionManager sessionManager = new SessionManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_taxes);
        setupActionBar("Tax",true);
    }

    private void bottomdilogbox() {
        Button btn_continue;
        TextView txt_continue;
        View view = getLayoutInflater().inflate(R.layout.taxes_items, null);
        BottomSheetDialog dialog = new BottomSheetDialog(this,R.style.BottomSheetDialog); // Style here
        dialog.setContentView(view);
        dialog.setCancelable(false);
        dialog.show();

        txt_continue =view.findViewById(R.id.txt_continue);
        btn_continue =view.findViewById(R.id.btn_continue);
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                sessionManager.setPreferences(TaxesActivity.this, "trial", "1");
            }
        });

        txt_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                sessionManager.setPreferences(TaxesActivity.this, "trial", "1");
            }
        });


    }
}