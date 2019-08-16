package com.puma.nextwhere.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.puma.nextwhere.R;
import com.puma.nextwhere.adapter.CityChildAdapter;
import com.puma.nextwhere.apicallbacks.ICityGuideCallback;
import com.puma.nextwhere.fetchuserlocation.FetchUserLocation;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.model.ExploreCityGuideApiResponse;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.presenter.DataPresontar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.puma.nextwhere.helper.AppConstants.ATTRACTION;
import static com.puma.nextwhere.helper.AppConstants.DRINK;
import static com.puma.nextwhere.helper.AppConstants.EAT;
import static com.puma.nextwhere.helper.AppConstants.EVENTS;

/**
 * Created by rajesh on 1/6/17.
 */

public class ExploreCityChildsFragment extends Fragment implements ICityGuideCallback {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.noData)
    TextView noData;
    @BindView(R.id.pbLoading)
    ProgressBar loading;
    private CityChildAdapter cityChildAdapter;
    private ArrayList<ExploreCityGuideApiResponse> listData;
    private int TYPE;
    Context context;
    private Unbinder unbinder;

    public Location location;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDetach() {
        context = null;
        super.onDetach();
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recylerview, container, false);
        unbinder = ButterKnife.bind(this, view);

        location = new Location("dummyprovider");
        location.setLatitude(getArguments().getDouble("latitude"));
        location.setLongitude(getArguments().getDouble("longitude"));


        if (listData == null)
            listData = new ArrayList<>();
        cityChildAdapter = new CityChildAdapter(view.getContext(), listData, location);
        recyclerView.setAdapter(cityChildAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeExploreCityGuideCallback(this);
        LoginApiResponse loginApiResponse = com.puma.nextwhere.preference.Preference.getInstance().getUserInfo();
        if (listData.size() == 0) {
            switch (TYPE) {
                case EAT:
                    dataPresontar.getRestrauntData(loginApiResponse.getIdDestino());
                    break;
                case DRINK:
                    dataPresontar.getBarData(loginApiResponse.getIdDestino());
                    break;
                case ATTRACTION:
                    dataPresontar.getCityGuideData(loginApiResponse.getIdDestino());
                    break;
                case EVENTS:
                    dataPresontar.getEventGuideData(loginApiResponse.getIdDestino());
                    break;
            }
        }
    }

    @Override
    public void showWait() {
        if (context != null) {
            loading.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void removeWait() {
        if (context != null) {
            loading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFailure(String appErrorMessage) {
        if (context != null) {
            Utils.showToast(context, appErrorMessage);
        }
    }

    @Override
    public void cityGuideResponse(List<ExploreCityGuideApiResponse> cityGuideResponse) {
        if (context == null)
            return;
        noData.setVisibility(View.GONE);
        listData.addAll(cityGuideResponse);
        cityChildAdapter.notifyDataSetChanged();
    }


    @Override
    public void setBarData(List<ExploreCityGuideApiResponse> cityGuideResponse) {
        if (context == null)
            return;
        noData.setVisibility(View.GONE);
        listData.addAll(cityGuideResponse);
        cityChildAdapter.notifyDataSetChanged();
    }

    @Override
    public void setRestrauntData(List<ExploreCityGuideApiResponse> cityGuideResponse) {
        if (context == null)
            return;
        noData.setVisibility(View.GONE);
        listData.addAll(cityGuideResponse);
        cityChildAdapter.notifyDataSetChanged();
    }


    @Override
    public void showNoDataView() {
        if(context==null) return;
        noData.setVisibility(View.VISIBLE);
    }

    public static Fragment getInstance(int type, Location location) {
        ExploreCityChildsFragment fr_exploreCityChilds = new ExploreCityChildsFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", location.getLatitude());
        bundle.putDouble("longitude", location.getLongitude());
        fr_exploreCityChilds.setArguments(bundle);
        fr_exploreCityChilds.TYPE = type;
        return fr_exploreCityChilds;
    }
}
