package com.puma.nextwhere.activity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayer;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerFullScreenListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;
import com.puma.nextwhere.FullScreenManager;
import com.puma.nextwhere.R;
import com.puma.nextwhere.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TVideoActivity extends BaseActivity {


    private YouTubePlayerView youTubePlayerView;
    private FullScreenManager fullScreenManager;
    public static final String EXTRA_VIDEO_ID = "key";
    String videoId;
    float current;
    float tottal;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_VIDEO_ID, videoId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.t_video);
        if (getIntent() != null) {
            videoId = getIntent().getStringExtra(EXTRA_VIDEO_ID);
        }

        if (savedInstanceState != null) {
            videoId = savedInstanceState.getString(EXTRA_VIDEO_ID);
        }
        ButterKnife.bind(this);
        fullScreenManager = new FullScreenManager(this);
        youTubePlayerView = findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(new AbstractYouTubePlayerListener() {

            @Override
            public void onReady() {
                youTubePlayerView.loadVideo(videoId, 0);
            }

        }, true);

        youTubePlayerView.addYouTubePlayerListener(new YouTubePlayer.YouTubePlayerListener() {
            @Override
            public void onReady() {
            }

            @Override
            public void onStateChange(@YouTubePlayer.PlayerState.State int state) {

            }

            @Override
            public void onPlaybackQualityChange(@YouTubePlayer.PlaybackQuality.Quality int playbackQuality) {

            }

            @Override
            public void onPlaybackRateChange(String rate) {

            }

            @Override
            public void onError(@YouTubePlayer.PlayerError.Error int error) {

            }

            @Override
            public void onApiChange() {

            }

            @Override
            public void onCurrentSecond(float second) {
                current = second;
                if (Math.abs(45.0467 - current) < 0.000001) {
                    finish();
                }

            }

            @Override
            public void onVideoDuration(float duration) {
                tottal = duration;
            }

            @Override
            public void onMessage(String log) {

            }

            @Override
            public void onVideoTitle(String videoTitle) {
            }

            @Override
            public void onVideoId(String videoId) {

            }
        });

        youTubePlayerView.addFullScreenListener(new YouTubePlayerFullScreenListener() {
            @Override
            public void onYouTubePlayerEnterFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                fullScreenManager.enterFullScreen();
                youTubePlayerView.getPlayerUIController().setCustomAction1(ContextCompat.getDrawable(TVideoActivity.this, R.drawable.pause), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        youTubePlayerView.pause();
                    }
                });
            }

            @Override
            public void onYouTubePlayerExitFullScreen() {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                fullScreenManager.exitFullScreen();
                youTubePlayerView.getPlayerUIController().showCustomAction1(false);
            }
        });


    }

    @OnClick(R.id.text_skip)
    public void skipVideo() {
        finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        youTubePlayerView.release();
    }
}
