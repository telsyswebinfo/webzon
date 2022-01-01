package com.webzon.Yplayer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.webzon.Activity.Account.EditBusinessActivity;
import com.webzon.Activity.DilogActivity;
import com.webzon.Activity.Manage.BusinesCardActivity;
import com.webzon.Adapter.BusinesCardAdapter;
import com.webzon.Adapter.FaqListAdapter;
import com.webzon.Adapter.OrderCategoryAdapter;
import com.webzon.Adapter.TutoricalCategoryAdapter;
import com.webzon.ApiClass.ServicesUtils;
import com.webzon.ApiClass.WebServiceHandler;
import com.webzon.ApiClass.WebServiceListener;
import com.webzon.Model.ModelCategory;
import com.webzon.Model.ModelFaq;
import com.webzon.Model.ProductCategoryModel;
import com.webzon.Model.PromotionalListModel;
import com.webzon.halper.StaticVariables;
import com.webzon.utils.ApiUrl;
import com.webzon.utils.SessionManager;
import com.webzon.utils.Utlity;
import com.webzon.utils.VideoConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import webzon.R;

public class TutorialsActivity extends YouTubeBaseActivity {
  //  YouTubePlayerView youtubePlayer;
   // YouTubePlayer.OnInitializedListener  mOnInitializedListener;
    SessionManager sessionManager = new SessionManager();
    ArrayList<ModelFaq> faqlist =new ArrayList<>();
    ArrayList<ModelCategory> categorylist =new ArrayList<>();
    ModelFaq modelFaq;
    ModelCategory modelCategory;
    RecyclerView rev_cate,rev_faq;
    LinearLayout lay_nodata;
    TutoricalCategoryAdapter tutoricalCategoryAdapter;
    TutorialsActivity baseActivity;
    FaqListAdapter faqListAdapter;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);
       // youtubePlayer =  findViewById(R.id.youtubePlayer);
      //  playYouTubeVideo("https://www.youtube.com/watch?v=mXjZQX3UzOs");
        lay_nodata =findViewById(R.id.lay_nodata);
        rev_cate =findViewById(R.id.rev_cate);
        swipe =findViewById(R.id.swipe);
        rev_cate.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rev_cate.setHasFixedSize(true);

        rev_faq =findViewById(R.id.rev_faq);
        rev_faq.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rev_faq.setHasFixedSize(true);
        getproductList1();

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                swipe.setRefreshing(false);
            }
        });

    }

   /* private void playYouTubeVideo(final String videoUrl) {
        final String youtubeVideoId = VideoConfig.extractYoutubeId(videoUrl);
        mOnInitializedListener = new YouTubePlayer.OnInitializedListener(){
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(youtubeVideoId);
                youTubePlayer.setFullscreen(false);
                youTubePlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
                youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
                    @Override
                    public void onFullscreen(boolean b) {
                        Intent intent = new Intent(TutorialsActivity.this, FullScreenLessonPlayerActivity.class);
                        intent.putExtra("videoType", "YouTube");
                        intent.putExtra("videoUrl", videoUrl);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        String YouTubeAPIKey ="AIzaSyB_0dcTDPHXcKo0Zq1iSguJJYonCRe7CfI";
        youtubePlayer.initialize(YouTubeAPIKey, mOnInitializedListener);
    }*/

    private void getproductList1() {
        faqlist.clear();
        categorylist.clear();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("user_id", sessionManager.getPreferences(this,"user_id"));
        hashMap.put("type","Admin");
        hashMap.put("device_id", StaticVariables.DeviceID);
        hashMap.put("device_token", StaticVariables.Token);
        hashMap.put("device_type", StaticVariables.DeviceType);

        WebServiceHandler webServiceHandler = new WebServiceHandler(this);
        webServiceHandler.serviceListener = new WebServiceListener() {
            @Override
            public void onResponse(final String response) {
                Log.e("Financial Response", response);
                try {
                    final JSONObject jsonObect = new JSONObject(response);
                    if (jsonObect.getString("status").equals("200")) {
                        //ArrayList<ProductCategoryModel> categoryData1;
                        //ProductCategoryModel productCategoryModel;

                        JSONObject object =new JSONObject(jsonObect.getString("faq"));
                        JSONArray jsonArray = object.getJSONArray("data");


                        for (int i =0 ; i<jsonArray.length(); i++){
                            modelFaq = new ModelFaq();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            modelFaq.setId(jsonObject.getString("id"));
                            modelFaq.setTitle(jsonObject.getString("title"));
                            modelFaq.setSlug(jsonObject.getString("slug"));
                            modelFaq.setYoutube_link(jsonObject.getString("youtube_link"));
                            StringTokenizer tokens = new StringTokenizer(jsonObject.getString("youtube_link"), "=");
                            String first = tokens.nextToken();
                            String second = tokens.nextToken();
                            modelFaq.setVid(second);
                            faqlist.add(modelFaq);
                        }

                        JSONObject object1 =new JSONObject(jsonObect.getString("category"));
                        JSONArray jsonArray1 = object1.getJSONArray("data");

                        for (int i =0 ; i<jsonArray1.length(); i++){
                            JSONObject jsonObject3 = jsonArray1.getJSONObject(i);
                            JSONArray jsonArray3 = jsonObject3.getJSONArray("tutorials");
                            for (int p =0 ; p<jsonArray3.length(); p++){
                                modelCategory = new ModelCategory();
                                JSONObject jsonObject1 = jsonArray3.getJSONObject(p);
                                modelCategory.setId(jsonObject1.getString("id"));
                                modelCategory.setTitle(jsonObject1.getString("title"));
                                modelCategory.setSlug(jsonObject1.getString("slug"));
                                modelCategory.setYoutube_link(jsonObject1.getString("youtube_link"));
                                StringTokenizer tokens = new StringTokenizer(jsonObject1.getString("youtube_link"), "=");
                                String first = tokens.nextToken();
                                String second = tokens.nextToken();
                                modelCategory.setVid(second);
                                categorylist.add(modelCategory);
                            }

                        }

                        try {
                            if(categorylist.size()>0){
                                lay_nodata.setVisibility(View.GONE);
                                rev_cate.setVisibility(View.VISIBLE);
                                tutoricalCategoryAdapter = new TutoricalCategoryAdapter(categorylist, baseActivity);
                                rev_cate.setAdapter(tutoricalCategoryAdapter);
                                faqListAdapter = new FaqListAdapter(faqlist, baseActivity);
                                rev_faq.setAdapter(faqListAdapter);
                                /*if(!object.getString("next_page_url").equals("null")){
                                    getproductList2(object.getString("next_page_url"));
                                }*/
                            }
                            else{
                                lay_nodata.setVisibility(View.VISIBLE);
                                rev_cate.setVisibility(View.GONE);
                            }
                        }catch (NullPointerException e){e.printStackTrace();}

                    }else if (jsonObect.getString("status").equals("403")) {
                        StaticVariables.LogoutMessage =jsonObect.getString("message");
                        Intent intent=new Intent(TutorialsActivity.this, DilogActivity.class);
                        startActivity(intent);
                    }else{
                        // lay_nodata.setVisibility(View.VISIBLE);
                        // rev_cate.setVisibility(View.GONE);
                        // SnackBar.returnFlashBar(getActivity(),response.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        };

        webServiceHandler.POST(hashMap, ServicesUtils.tutorialsList);
    }

}