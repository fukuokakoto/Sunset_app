package com.nifcloud.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeView extends YouTubeBaseActivity {
    Button button2;
    //youtube
    private static final String API_KEY = "AIzaSyCgBoIC_i0xQLisvN560SBiyYgN2Oc3Yrk";
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_view);
        button2 = findViewById(R.id.button2);
        youTubePlayerView = findViewById(R.id.Youtube_view);

        onInitializedListener = new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo("Lo1mf9sTVjg");  //　岡垣観光課ライブ動画のビデオID
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        youTubePlayerView.initialize( API_KEY , onInitializedListener );
    }

    public void main( View view ){
        finish();
    }
}