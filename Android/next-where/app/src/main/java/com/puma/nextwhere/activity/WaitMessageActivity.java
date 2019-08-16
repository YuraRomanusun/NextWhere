package com.puma.nextwhere.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.puma.nextwhere.R;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.preference.Preference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WaitMessageActivity extends AppCompatActivity {

    private TextView tvName, tvMessage1, tvMessage2, tvMessage3, tvLogout;
    private TextView tvCountDown1,tvCountDown2,tvCountDown3,tvCountDown4,tvCountDown5,tvCountDown6, tvCountDownColon1, tvCountDownColon2;
    private ImageView ivLogo;
    private LinearLayout llClock, llHeyName;
    private Animation animAlphaIn;
    private LoginApiResponse loginApiResponse;
    private Object[] views;
    private int nextViewAnim = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginApiResponse = Preference.getInstance().getUserInfo();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        long diffDays = 0, diffHours = 0, diffMinutes = 0;

        try {
            Date dateTemp = sdf.parse(loginApiResponse.getFechaDesde() + " " + loginApiResponse.getHoraIda());
            Date date1 = new Date(dateTemp.getTime() - (2 * 24 * 3600 * 1000));
            Date date2 = new Date();

            //Comparing dates
            long difference = Math.abs(date1.getTime() - date2.getTime());
            diffDays = difference / (24 * 60 * 60 * 1000);
            difference = difference - (diffDays * 24 * 60 * 60 * 1000);
            diffHours = difference / (60 * 60 * 1000);
            difference = difference - (diffHours * 60 * 60 * 1000);
            diffMinutes = difference / (60 * 1000);
        } catch (ParseException ex) {
            Logger.getLogger(WaitMessageActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

        setContentView(R.layout.activity_wait_message);
        ivLogo = findViewById(R.id.iv_logo);
        llClock = findViewById(R.id.ll_clock);
        llHeyName = findViewById(R.id.ll_hey_name);

        tvName = findViewById(R.id.tv_first_name);
        tvMessage1 = findViewById(R.id.tv_message_1);
        tvMessage2 = findViewById(R.id.tv_message_2);
        tvMessage3 = findViewById(R.id.tv_message_3);
        tvLogout = findViewById(R.id.tv_logout);

        tvName.setText(loginApiResponse.getNombre());

        tvLogout.setAlpha(0);
        ivLogo.setAlpha(0f);
        tvMessage1.setAlpha(0);
        tvMessage1.setY(90f);
        tvMessage2.setAlpha(0);
        tvMessage2.setY(90f);
        tvMessage3.setAlpha(0);
        tvMessage3.setY(90f);
        llClock.setY(110f);
        llClock.setAlpha(0);
        llHeyName.setY(90f);
        llHeyName.setAlpha(0);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/digital_7.ttf");

        tvCountDown1 = findViewById(R.id.tv_count_down_1);
        tvCountDown2 = findViewById(R.id.tv_count_down_2);
        tvCountDown3 = findViewById(R.id.tv_count_down_3);
        tvCountDown4 = findViewById(R.id.tv_count_down_4);
        tvCountDown5 = findViewById(R.id.tv_count_down_5);
        tvCountDown6 = findViewById(R.id.tv_count_down_6);
        tvCountDownColon1 = findViewById(R.id.tv_count_down_colon_1);
        tvCountDownColon2 = findViewById(R.id.tv_count_down_colon_2);

        tvCountDown1.setTypeface(font);
        tvCountDown2.setTypeface(font);
        tvCountDown3.setTypeface(font);
        tvCountDown4.setTypeface(font);
        tvCountDown5.setTypeface(font);
        tvCountDown6.setTypeface(font);

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        String days = String.valueOf(diffDays);
        char[] charArray = days.toCharArray();

        if(charArray.length > 1) {
            tvCountDown1.setText(String.valueOf(charArray[0]));
            tvCountDown2.setText(String.valueOf(charArray[1]));
        }
        else if (charArray.length == 1) {
            tvCountDown2.setText(String.valueOf(charArray[0]));
        }

        String hours = String.valueOf(diffHours);
        charArray = hours.toCharArray();

        if(charArray.length > 1) {
            tvCountDown3.setText(String.valueOf(charArray[0]));
            tvCountDown4.setText(String.valueOf(charArray[1]));
        }
        else if (charArray.length == 1) {
            tvCountDown4.setText(String.valueOf(charArray[0]));
        }

        String minutes = String.valueOf(diffMinutes);
        charArray = minutes.toCharArray();

        if(charArray.length > 1) {
            tvCountDown5.setText(String.valueOf(charArray[0]));
            tvCountDown6.setText(String.valueOf(charArray[1]));
        }
        else if (charArray.length == 1) {
            tvCountDown6.setText(String.valueOf(charArray[0]));
        }

        views = new Object[]{llHeyName, tvMessage1, tvMessage2, tvMessage3, llClock};

        startBlink();
        showElement((View) llHeyName);
    }

    private void showElement(View view) {
       //view.animate().alpha(1.0f).translationY(0).setDuration(1500);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", 0f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 1.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhAlpha);
        animator.setDuration(1500);
        animator.start();
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                Handler handler = new Handler();
                Log.d("Wait","Animation finished " + nextViewAnim);
                handler.postDelayed(new Runnable() {
                    public void run() {

                        if(views.length != nextViewAnim)
                            hideElement(view);

                        if(views.length > nextViewAnim) {
                            showElement((View) views[nextViewAnim++]);
                            if(views.length == nextViewAnim) {
                                showElement((View) ivLogo);
                                showElement((View) tvLogout);
                            }
                        }
                        else {
                        }
                    }
                }, 1500);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    private void hideElement(View view) {
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("translationY", -90f);
        PropertyValuesHolder pvhAlpha = PropertyValuesHolder.ofFloat("alpha", 0.0f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(view, pvhY, pvhAlpha);
        animator.setDuration(1500);
        animator.start();
    }

    private boolean isWhite = false;

    private void startBlink() {
        Handler handler = new Handler();
        int delay = 1000; //milliseconds

        handler.postDelayed(new Runnable(){
            public void run(){
                if( ! isWhite) {
                    tvCountDownColon1.setTextColor(Color.TRANSPARENT);
                    tvCountDownColon2.setTextColor(Color.TRANSPARENT);
                }
                else {
                    tvCountDownColon1.setTextColor(Color.parseColor("#0F7E96"));
                    tvCountDownColon2.setTextColor(Color.parseColor("#0F7E96"));
                }

                isWhite = ! isWhite ;

                handler.postDelayed(this, delay);
            }
        }, delay);
    }

    private void logOut() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        Preference.getInstance().putLoginDetails(null);
        finish();
    }
}
