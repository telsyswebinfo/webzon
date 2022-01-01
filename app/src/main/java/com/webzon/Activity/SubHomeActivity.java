package com.webzon.Activity;

import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.webzon.R;

public class SubHomeActivity extends CustomActivity{
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_home);
       // bottomNavigationView = findViewById(R.id.bottomNavigationView);

      //  bottomNavigationView.setOnNavigationItemSelectedListener(this);
      //  bottomNavigationView.setSelectedItemId(R.id.person);
        setupActionBar("",true);
    }
    //HomeFragment firstFragment = new HomeFragment();
     //SecondFragment secondFragment = new SecondFragment();
    //ThirdFragment thirdFragment = new ThirdFragment();

   /* @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.person:
               // getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, firstFragment).commit();
                return true;

            case R.id.home:
                //getSupportFragmentManager().beginTransaction().replace(R.id.container, secondFragment).commit();
                return true;

            case R.id.settings:
               // getSupportFragmentManager().beginTransaction().replace(R.id.container, thirdFragment).commit();
                return true;
        }
        return false;
    }*/
}