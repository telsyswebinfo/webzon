package com.webzon.intro;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.webzon.Activity.Login.EnterWhatupNoActivity;
import com.webzon.utils.SessionManager;

import webzon.R;


public class Intro extends AppCompatActivity {
    private static final int max = 5;
    public static final ImageView[] imageViews = new ImageView[max];
    public static ViewPager2 viewPager;
    SessionManager sessionManager = new SessionManager();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            window.setStatusBarColor(Color.WHITE);
            window.setNavigationBarColor(Color.WHITE);
        }
        setContentView(R.layout.intro);
        imageViews[0] = findViewById(R.id.intro_dot_1);
        imageViews[1] = findViewById(R.id.intro_dot_2);
        imageViews[2] = findViewById(R.id.intro_dot_3);
        imageViews[3] = findViewById(R.id.intro_dot_4);
        imageViews[4] = findViewById(R.id.intro_dot_5);
        viewPager = findViewById(R.id.intro_viewPager);
        viewPager.setAdapter(new ScreenSlider(this));
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                changeDot(position);
            }
        });
        findViewById(R.id.intro_skip).setOnClickListener(view -> {
            Toast.makeText(Intro.this, "", Toast.LENGTH_SHORT).show();
            PreferenceManager.getDefaultSharedPreferences(Intro.this).edit().putBoolean("intro", true).apply();
            startActivity(new Intent(Intro.this, EnterWhatupNoActivity.class));
            finish();
        });
    }

    private void changeDot(int c) {
        for (int i = 0; i < imageViews.length; i++) {
            if (i == c) {
                imageViews[i].setImageResource(R.drawable.intro_active);
            } else {
                imageViews[i].setImageResource(R.drawable.intro_inactive);
            }
        }
    }

    private static class ScreenSlider extends FragmentStateAdapter {
        public ScreenSlider(AppCompatActivity fa) {
            super(fa);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            //changeDot(position);
            if (position == 0) {
                return new Intro_F0();
            } else if (position == 1) {
                return new Intro_F1();
            } else if (position == 2) {
                return new Intro_F2();
            } else if (position == 3) {
                return new Intro_F3();
            } else if (position == 4) {
                return new Intro_F4();
            }
            return new Intro_F0();
        }

        @Override
        public int getItemCount() {
            return max;
        }
    }

}