package com.puma.nextwhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.puma.nextwhere.R;
import com.puma.nextwhere.fetchuserlocation.FetchUserLocation;
import com.puma.nextwhere.fragment.ExploreCityGuideFragment;
import com.puma.nextwhere.fragment.FragmentLoadYoutubeVideo;
import com.puma.nextwhere.fragment.UnlockSurpriseFragment;
import com.puma.nextwhere.fragment.UnlockSurpriseWebFragment;
import com.puma.nextwhere.fragment.ViewMyTripDetailFragment;
import com.puma.nextwhere.fragment.ViewTravelGeneralFragment;
import com.puma.nextwhere.fragment.ViewUnlockSupriseFragment;
import com.puma.nextwhere.interfaces.ReplaceFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

import static com.puma.nextwhere.helper.AppConstants.LOAD_VIDEO_IN_WEBVIEW;
import static com.puma.nextwhere.helper.AppConstants.OPEN_EXPLORECITYGUIDE;
import static com.puma.nextwhere.helper.AppConstants.OPEN_SURPRISE_SCREEN;
import static com.puma.nextwhere.helper.AppConstants.OPEN_VIEWMYTRIPDETAIL;
import static com.puma.nextwhere.helper.AppConstants.OPEN_VIEWTRAVELGENERAL;
import static com.puma.nextwhere.helper.AppConstants.OPEN_VIEWUNLOCKSUPRISE;

public class SecondActivity extends AppCompatActivity implements ReplaceFragment {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.title)
    TextView topTitle;

    public static String OPEN_FRAGMENT_KEY = "key";
    public static String DATA = "data";
    Intent intent;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("DATA", intent);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        intent = savedInstanceState.getParcelable("DATA");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        if (savedInstanceState == null)
            intent = getIntent();
        performAction(intent.getIntExtra(OPEN_FRAGMENT_KEY, 0));
    }

    @Override
    public void onBackPressed() {
        backPress();
    }

    private void backPress() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 1)
            fragmentManager.popBackStack();
        else
            finish();
    }

    @OnTouch(R.id.title)
    public boolean performBackClick(MotionEvent event) {
        if (event.getRawX() <= (topTitle.getCompoundDrawables()[0].getBounds().width())) {
            backPress();
            return true;
        }
        return false;
    }

    private void performAction(int intExtra) {
        switch (intExtra) {
            case OPEN_VIEWMYTRIPDETAIL:
                topTitle.setText(getString(R.string.tripDetails));
                replaceFragment(new ViewMyTripDetailFragment(), String.valueOf(OPEN_VIEWMYTRIPDETAIL));
                break;
            case OPEN_VIEWUNLOCKSUPRISE:
                topTitle.setText(getString(R.string.viewunlocksuprise));
                //replaceFragment(new UnlockSurpriseFragment(), String.valueOf(OPEN_VIEWMYTRIPDETAIL));
                replaceFragment(new UnlockSurpriseWebFragment(), String.valueOf(OPEN_VIEWMYTRIPDETAIL));
                break;
            case LOAD_VIDEO_IN_WEBVIEW:
                topTitle.setText(getString(R.string.offerVideo));
                replaceFragment(FragmentLoadYoutubeVideo.getInstance(intent.getStringExtra(DATA)),
                        String.valueOf(LOAD_VIDEO_IN_WEBVIEW));
                break;
            case OPEN_EXPLORECITYGUIDE:
                topTitle.setVisibility(View.GONE);
                replaceFragment(new ExploreCityGuideFragment(), String.valueOf(OPEN_EXPLORECITYGUIDE));
                break;
            /*case OPEN_CONNECTWITHANOTHERNEXTER:
                replaceFragment(new ConnectWithAnotherNexterFragment(), String.valueOf(OPEN_CONNECTWITHANOTHERNEXTER));
                break;*/
            case OPEN_VIEWTRAVELGENERAL:
                topTitle.setVisibility(View.GONE);
                replaceFragment(new ViewTravelGeneralFragment(), String.valueOf(OPEN_VIEWTRAVELGENERAL));
                break;
            case OPEN_SURPRISE_SCREEN:
                topTitle.setText(getString(R.string.surpriseTitle));
                replaceFragment(new ViewUnlockSupriseFragment(), String.valueOf(OPEN_SURPRISE_SCREEN));
        }
    }


    @Override
    public void replaceFragment(Fragment activeFragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        try {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, activeFragment, tag).addToBackStack(null).commit();
        } catch (Exception exp) {
            fragmentManager.beginTransaction().replace(R.id.frameLayout, activeFragment, tag).addToBackStack(null).commitAllowingStateLoss();
        }
    }

    @Override
    public void finishCurrentActivity() {
        finish();
    }

    @Override
    public void openCode(int code) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(SecondActivity.OPEN_FRAGMENT_KEY, code);
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


}
