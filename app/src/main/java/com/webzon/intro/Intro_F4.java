package com.webzon.intro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import com.webzon.Activity.Login.EnterWhatupNoActivity;
import com.webzon.R;


public class Intro_F4 extends Fragment {
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.intro_item, container, false);
        activity = getActivity();
        if (activity == null) return v;
        ImageView imageView = v.findViewById(R.id.intro_item_imageView);
        imageView.setImageResource(R.drawable.intro_5);
        Button button = v.findViewById(R.id.intro_item_button);
        button.setText(getString(R.string.continu));
        button.setOnClickListener(view -> {
            PreferenceManager.getDefaultSharedPreferences(activity)
                    .edit().putBoolean("intro", true).apply();
            startActivity(new Intent(activity, EnterWhatupNoActivity.class));
            activity.finish();
        });
        return v;
    }
}
