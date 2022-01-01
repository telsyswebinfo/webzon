package com.webzon.Yplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.webzon.utils.VideoConfig;

import com.webzon.R;

public class FullScreenLessonPlayerActivity extends YouTubeBaseActivity {
    YouTubePlayerView myouTubePlayerView;
    YouTubePlayer.OnInitializedListener  mOnInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_lesson_player);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int) (width) , (int) (height));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        getWindow().setAttributes(params);
        String mVideoUrl  = (String) getIntent().getSerializableExtra("videoUrl");
        playYouTubeVideo(mVideoUrl);
    }

    private void playYouTubeVideo(String videoUrl) {
        final String youtubeVideoId = VideoConfig.extractYoutubeId(videoUrl);
        myouTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubePlayer);
        myouTubePlayerView.setVisibility(View.VISIBLE);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeVideoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };

        String YouTubeAPIKey = "AIzaSyB_0dcTDPHXcKo0Zq1iSguJJYonCRe7CfI";
        myouTubePlayerView.initialize(YouTubeAPIKey, mOnInitializedListener);
    }
}