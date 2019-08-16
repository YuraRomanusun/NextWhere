package com.puma.nextwhere.interfaces;

/**
 * Created by PingChoi on 2/7/2018.
 */

public interface OnWebViewListener {
    // Scroll event
    void onScroll(int l, int t, int oldl, int oldt);

    // Load finish
    void onFinish(String url);

    // Url change event
    void onUrlChange(String url);

    // Progress change event
    void onProgressChanged(int newProgress);
}

