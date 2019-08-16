package com.puma.nextwhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.puma.nextwhere.BuildConfig;
import com.puma.nextwhere.R;
import com.puma.nextwhere.adapter.ViewMyTripDetailAdapter;
import com.puma.nextwhere.apicallbacks.ITripDetailCallback;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.interfaces.ReplaceFragment;
import com.puma.nextwhere.model.HotelDetails;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.ViewMyTripDetailModel;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.preference.Preference;
import com.puma.nextwhere.presenter.DataPresontar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class ViewMyTripDetailFragment extends Fragment implements ITripDetailCallback {
    @BindView(R.id.passengerDetails)
    RecyclerView recyclerView;
    @BindView(R.id.displayImage)
    ImageView displayImage;
    @BindView(R.id.location)
    TextView locationView;
    @BindView(R.id.loading)
    FrameLayout loading;
    ViewMyTripDetailAdapter viewMyTripDetailAdapter;
    private ArrayList<Object> viewMyTripDetailModels;
    ReplaceFragment replaceFragment;
    LoginApiResponse loginApiResponse;
    private Unbinder unbinder;
    Context context;


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        replaceFragment = (ReplaceFragment) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.context = null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        replaceFragment = (ReplaceFragment) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewmytripdetails, container, false);
        unbinder = ButterKnife.bind(this, view);
        loginApiResponse = Preference.getInstance().getUserInfo();
        Glide.with(this).load(BuildConfig.FILEURL.concat(loginApiResponse.getRutaImagenDestino()))
                .into(displayImage);
        locationView.setText(loginApiResponse.getHastaAsignado());
        recyclerView.setBackgroundColor(Color.WHITE);
        if (viewMyTripDetailModels == null) {
            viewMyTripDetailModels = new ArrayList<>();
        }
        viewMyTripDetailAdapter = new ViewMyTripDetailAdapter(viewMyTripDetailModels, loginApiResponse);
        recyclerView.setAdapter(viewMyTripDetailAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeViewMyTripCallback(this);
        dataPresontar.setHotelInfo(loginApiResponse.getIdHotel());
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
            loading.setVisibility(View.GONE);
            Utils.showToast(getActivity(), appErrorMessage);
        }

    }

    @Override
    public void setHotelDetails(HotelDetails data) {
        if (context != null) {
            viewMyTripDetailModels.add(data);
            viewMyTripDetailModels.add(new ViewMyTripDetailModel(ViewMyTripDetailAdapter.DIVIDER));
            viewMyTripDetailModels.add(new ViewMyTripDetailModel(ViewMyTripDetailAdapter.LOCATION));
            viewMyTripDetailModels.add(new ViewMyTripDetailModel(ViewMyTripDetailAdapter.DIVIDER));
            viewMyTripDetailModels.add(new ViewMyTripDetailModel(ViewMyTripDetailAdapter.TITLE));
            if (loginApiResponse != null) {
                viewMyTripDetailModels.addAll(loginApiResponse.getPasajeros());
            }
            viewMyTripDetailAdapter.notifyDataSetChanged();
        }

    }
}
