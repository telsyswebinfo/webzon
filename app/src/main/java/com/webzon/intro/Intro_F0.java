package com.webzon.intro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

import com.webzon.R;


public class Intro_F0 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.intro_item, container, false);
        ImageView imageView = v.findViewById(R.id.intro_item_imageView);
        imageView.setImageResource(R.drawable.intro_1);
        Button button = v.findViewById(R.id.intro_item_button);
        button.setText(getString(R.string.next));
        button.setOnClickListener(view -> Intro.viewPager.setCurrentItem(1));

        return v;
    }
}
