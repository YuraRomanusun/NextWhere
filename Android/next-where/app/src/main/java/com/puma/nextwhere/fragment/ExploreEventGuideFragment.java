package com.puma.nextwhere.fragment;

import android.content.Context;
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

import com.puma.nextwhere.R;
import com.puma.nextwhere.adapter.ExploreEventAdapter;
import com.puma.nextwhere.apicallbacks.IEventGuideCallback;
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

/**
 * Created by rajesh on 24/12/17.
 */

public class ExploreEventGuideFragment extends Fragment implements IEventGuideCallback {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.noData)
    TextView noData;
    @BindView(R.id.pbLoading)
    ProgressBar loading;
    private ExploreEventAdapter cityChildAdapter;
    private ArrayList<ExploreCityGuideApiResponse> listData;
    private Context context;
    private Unbinder unbinder;

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
        if (listData == null)
            listData = new ArrayList<>();
        cityChildAdapter = new ExploreEventAdapter(listData);
        recyclerView.setAdapter(cityChildAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeEventCallback(this);
        LoginApiResponse loginApiResponse = com.puma.nextwhere.preference.Preference.getInstance().getUserInfo();
        if (listData.size() == 0) {
            dataPresontar.getEventGuideData(loginApiResponse.getIdDestino());
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
    public void setEventGuideData(List<ExploreCityGuideApiResponse> cityGuideResponse) {
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

}
