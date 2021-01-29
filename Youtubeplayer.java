package com.nifcloud.user;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class Youtubeplayer extends View {

    private static final String API_KEY = "AIzaSyA_Gzg6QYTr2rMbgS-w0Gd074EW438fbUw";

    //Youtube  再生の準備
    YouTubePlayerView youTubePlayerView;
    YouTubePlayer.OnInitializedListener onInitializedListener;

    public Youtubeplayer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
}