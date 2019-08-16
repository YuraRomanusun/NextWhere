package com.puma.nextwhere.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.puma.nextwhere.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by rajesh on 11/10/17.
 */

public class FragmentLoadYoutubeVideo extends Fragment {
    @BindView(R.id.loadVideo)
    WebView loadVideo;

    @BindView(R.id.loading)
    ProgressBar loading;

    String videoId;
    private Unbinder unbinder;


    public static FragmentLoadYoutubeVideo getInstance(String videoId) {
        FragmentLoadYoutubeVideo fragmentLoadYoutubeVideo = new FragmentLoadYoutubeVideo();
        fragmentLoadYoutubeVideo.videoId = videoId;
        return fragmentLoadYoutubeVideo;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load_youtube_video, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadVideo.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                loading.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                loading.setVisibility(View.GONE);
                Log.d("finish", "done");
            }
        });
        WebSettings webSettings = loadVideo.getSettings();
        loadVideo.setWebViewClient(new WebViewClient());
        webSettings.setJavaScriptEnabled(true);
        loadVideo.loadUrl(videoId);
        return view;
    }


}
