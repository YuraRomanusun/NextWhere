package com.puma.nextwhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.BuildConfig;
import com.puma.nextwhere.R;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.preference.Preference;
import com.puma.nextwhere.utils.BaseActivity;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnTouchListener, View.OnClickListener {

    @BindView(R.id.txv_view_my_trip_details)
    TextView txvTripDetails;

    @BindView(R.id.txv_view_my_trip_details_selected)
    TextView txvTripDetailsSelected;

    @BindView(R.id.txv_view_unlocked_surprises)
    TextView txvUnlock;

    @BindView(R.id.txv_view_unlocked_surprises_selected)
    TextView txvUnlockSelected;

    @BindView(R.id.txv_explore_city_guide)
    TextView txvExploreCity;

    @BindView(R.id.txv_explore_city_guide_selected)
    TextView txvExploreCitySelected;

    @BindView(R.id.txv_view_travel_journal)
    TextView txvTravel;

    @BindView(R.id.txv_view_travel_journal_selected)
    TextView txvTravelSelected;

    @BindView(R.id.txv_text_support)
    TextView txvText;

    @BindView(R.id.txv_text_support_selected)
    TextView txvTextSelected;

    @BindView(R.id.imgDestination)
    ImageView imgDestination;

    @BindView(R.id.txvCity)
    TextView txvCity;

    @BindView(R.id.txvVideoAgain)
    TextView txvVideoAgain;

    @BindView(R.id.txvLogOut)
    TextView txvLogOut;

    LoginApiResponse loginApiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginApiResponse = Preference.getInstance().getUserInfo();

        if(Integer.parseInt(loginApiResponse.getVistaInmediata()) == 1) {
            setContentView(R.layout.activity_main);
        }
        else {
            System.out.print("Exampleas dfasdf asdf asdfa sdf asdf asf");
        }


        ButterKnife.bind(this);

//        loadLayout();
    }

    private void loadLayout() {

        //loginApiResponse = Preference.getInstance().getUserInfo();
        txvTripDetails.setOnTouchListener(this);
        txvUnlock.setOnTouchListener(this);
        txvExploreCity.setOnTouchListener(this);
        txvTravel.setOnTouchListener(this);
        txvText.setOnTouchListener(this);
        txvVideoAgain.setOnClickListener(this);
        txvLogOut.setOnClickListener(this);

        Glide.with(this).load(BuildConfig.FILEURL.concat(loginApiResponse.getRutaImagenDestino())).into(imgDestination);
        txvCity.setText(loginApiResponse.getHastaAsignado());
    }


    private void goAcitivity(int position) {
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(SecondActivity.OPEN_FRAGMENT_KEY, (position + 1));
        startActivity(intent);
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()) {
            case R.id.txv_view_my_trip_details:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    txvTripDetails.setVisibility(View.GONE);
                    txvTripDetailsSelected.setVisibility(View.VISIBLE);
                    goAcitivity(0);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    txvTripDetails.setVisibility(View.VISIBLE);
                    txvTripDetailsSelected.setVisibility(View.GONE);
                }
                break;
            case R.id.txv_view_unlocked_surprises:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    txvUnlock.setVisibility(View.GONE);
                    txvUnlockSelected.setVisibility(View.VISIBLE);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    txvUnlock.setVisibility(View.VISIBLE);
                    txvUnlockSelected.setVisibility(View.GONE);
                }
                break;

            case R.id.txv_explore_city_guide:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    txvExploreCity.setVisibility(View.GONE);
                    txvExploreCitySelected.setVisibility(View.VISIBLE);
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    txvExploreCity.setVisibility(View.VISIBLE);
                    txvExploreCitySelected.setVisibility(View.GONE);
                }
                break;
            case R.id.txv_view_travel_journal:
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    txvTravel.setVisibility(View.GONE);
                    txvTravelSelected.setVisibility(View.VISIBLE);

                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    txvTravel.setVisibility(View.VISIBLE);
                    txvTravelSelected.setVisibility(View.GONE);
                }
                break;

            case R.id.txv_text_support:

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    txvText.setVisibility(View.GONE);
                    txvTextSelected.setVisibility(View.VISIBLE);
                    startActivity(new Intent(this, ZopimChatActivity.class));
                    return true;

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    txvText.setVisibility(View.VISIBLE);
                    txvTextSelected.setVisibility(View.GONE);
                }
                break;
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txvVideoAgain:

                Intent i = new Intent(MainActivity.this, TVideoActivity.class);
                startActivity(i);

                break;
            case R.id.txvLogOut:
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);
                Preference.getInstance().putLoginDetails(null);
                finish();

                break;
        }
    }
}
