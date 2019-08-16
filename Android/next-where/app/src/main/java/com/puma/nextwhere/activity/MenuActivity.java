package com.puma.nextwhere.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.puma.nextwhere.R;
import com.puma.nextwhere.adapter.MenuActivityAdapter;
import com.puma.nextwhere.apicallbacks.IVideoCallback;
import com.puma.nextwhere.fetchuserlocation.FetchUserLocation;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.CheckPermission;
import com.puma.nextwhere.helper.NonUiOpertion;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.HomeVideoModel;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.MenuActivity_Model;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.preference.Preference;
import com.puma.nextwhere.presenter.DataPresontar;
import com.puma.nextwhere.utils.BaseActivity;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.puma.nextwhere.activity.RequestingPermission.EXTRA_REQUEST_CODE;

public class MenuActivity extends BaseActivity implements MenuActivityAdapter.MenuActivityClickCallback,
        IVideoCallback {

    @BindView(R.id.reclerView)
    RecyclerView recyclerView;
    String videoId;
    MenuActivityAdapter menuActivity_adapter;
    ArrayList<Object> homeData;
    LoginApiResponse loginApiResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        loginApiResponse = Preference.getInstance().getUserInfo();

        if(loginApiResponse.getMostrarDestino() == null) {
            this.logOut();
            return;
        }

        if(Integer.parseInt(loginApiResponse.getVistaInmediata()) == 0 && Integer.parseInt(loginApiResponse.getMostrarDestino()) != 1) {
            Intent intent = new Intent(this, WaitMessageActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        homeData = new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (menuActivity_adapter != null) {
                    // For setting chatting layout to center
                    if (homeData.size() > 2) {
                        if (position == homeData.size() - 2) {
                            return 2;
                        }
                    }
                    switch (menuActivity_adapter.getItemViewType(position)) {
                        case MenuActivityAdapter.HOME_VIDEO:
                        case MenuActivityAdapter.SIGN_OUT:
                            return 2;
                        case MenuActivityAdapter.MENU_ITEM:
                            return 1; // divide screen on the basis of span count
                        default:
                            return -1;
                    }
                } else {
                    return -1;
                }
            }
        });
        didShowTripVideo();

        menuActivity_adapter = new MenuActivityAdapter(homeData,
                Utils.getMenuHeight(this, (int) getResources().getDimension(R.dimen.dp_180)));
        recyclerView.setAdapter(menuActivity_adapter);
        menuActivity_adapter.initializeClickCallBack(MenuActivity.this);
    }

    private void didShowTripVideo() {
        if (NonUiOpertion.getInstance().didShowTripVideo(loginApiResponse)) {
            getTripVideo();
        }
    }

    private void getTripVideo() {
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeVideoCallback(this);
        dataPresontar.getTripVideo(loginApiResponse.getIdDestino());
    }


    private void addItemToList() {
        homeData.clear();
        homeData.add(new HomeVideoModel());
        homeData.add(new MenuActivity_Model(getString(R.string.viewmytripdetail),
                R.mipmap.ic_view_my_trip_details, R.mipmap.ic_view_my_trip_details_selected));
        homeData.add(new MenuActivity_Model(getString(R.string.viewunlocksuprise),
                R.mipmap.ic_view_unlocked_surprises, R.mipmap.ic_view_unlocked_surprises_selected));
        homeData.add(new MenuActivity_Model(getString(R.string.explore_city_guide),
                R.mipmap.ic_explorer_guide, R.mipmap.ic_explorer_guide_selected));
        //homeData.add(new MenuActivity_Model(getString(R.string.connectwithothernexters), R.mipmap.ic_connect_with_other_nexters, R.mipmap.ic_connect_with_other_nexters_selected));
        homeData.add(new MenuActivity_Model(getString(R.string.viewtravelgeneral),
                R.mipmap.ic_view_travel_journal, R.mipmap.ic_view_travel_journal_selected));
        homeData.add(new MenuActivity_Model(getString(R.string.textsupport),
                R.mipmap.ic_text_support, R.mipmap.ic_text_support_selected));
        homeData.add(String.valueOf(MenuActivityAdapter.SIGN_OUT));
        menuActivity_adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loginApiResponse != null) {
            addItemToList();
        }
    }


    @Override
    public void onClick(int position) {
        if (position == 4) {
            startActivity(new Intent(this, ZopimChatActivity.class));
        } else {
            Intent intent = new Intent(this, SecondActivity.class);
            intent.putExtra(SecondActivity.OPEN_FRAGMENT_KEY, (position + 1));
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    @Override
    public void showVideo() {
        if (videoId == null) {
            DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
            dataPresontar.initializeVideoCallback(this);
            dataPresontar.getTripVideo(loginApiResponse.getIdDestino());
        } else {
            playVideo();
        }
    }

    @Override
    public void logOut() {
        Intent intent = new Intent(MenuActivity.this, LoginActivity.class);
        startActivity(intent);
        Preference.getInstance().putLoginDetails(null);
        finish();
    }

    @Override
    public void showWait() {
        showProgress();
    }

    @Override
    public void removeWait() {
        closeProgress();
    }

    @Override
    public void onFailure(String appErrorMessage) {
        showToast(appErrorMessage);
    }

    @Override
    public void showVideo(String videoId, String video) {
        this.videoId = videoId;
        playVideo();
    }

    private void playVideo() {
        Intent intent = new Intent(this, TVideoActivity.class);
        intent.putExtra(TVideoActivity.EXTRA_VIDEO_ID, videoId);
        startActivity(intent);
    }

}