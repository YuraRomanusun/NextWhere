package com.puma.nextwhere.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.VideoView;

import com.puma.nextwhere.R;
import com.puma.nextwhere.preference.Preference;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntroActivity extends AppCompatActivity {
    @BindView(R.id.videoView)
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ButterKnife.bind(this);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.intro1;
        videoView.setVideoURI(Uri.parse(path));
        videoView.setZOrderOnTop(true);
        videoView.start();
        videoView.setOnCompletionListener(mp -> {
            if (Preference.getInstance().getUserInfo() != null) {
                Intent intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                finish();
            } else {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
                IntroActivity.this.finish();
            }
        });
    }

}
