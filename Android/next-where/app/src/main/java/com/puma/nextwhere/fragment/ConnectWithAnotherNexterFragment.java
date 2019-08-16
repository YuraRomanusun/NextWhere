package com.puma.nextwhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.puma.nextwhere.R;
import com.puma.nextwhere.adapter.ExploreCityGuidePagerAdapter;
import com.puma.nextwhere.helper.MyInfoWindowAdapter;
import com.puma.nextwhere.interfaces.ReplaceFragment;
import com.puma.nextwhere.model.ExploreCityPagerModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;
import butterknife.Unbinder;


/**
 * Created by rajesh on 31/5/17.
 */

public class ConnectWithAnotherNexterFragment extends Fragment implements OnMapReadyCallback {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager pager;
    @BindView(R.id.mapLayout)
    MapView mapView;
    @BindView(R.id.title)
    TextView back;
    @BindView(R.id.download)
    Button download;
    ExploreCityGuidePagerAdapter exploreCityGuidePagerAdapter;
    ReplaceFragment replaceFragment;
    Unbinder unbinder;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        replaceFragment = (ReplaceFragment) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
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
        View view = inflater.inflate(R.layout.fragment_connectwithanothernexter, container, false);
        unbinder = ButterKnife.bind(this, view);
        exploreCityGuidePagerAdapter = new ExploreCityGuidePagerAdapter(getChildFragmentManager());
        pager.setAdapter(exploreCityGuidePagerAdapter);
        tabLayout.setupWithViewPager(pager, true);
        addItemToPager();
        loadMap();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    private void loadMap() {
        mapView.onCreate(null);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        if (context != null) {
            mapView.setVisibility(View.VISIBLE);
            LatLng location = new LatLng(10, 10);
            LatLng location1 = new LatLng(20, 20);
            LatLng location2 = new LatLng(40, 40);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
            Marker marker = map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
            map.addMarker(new MarkerOptions().position(location1).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
            map.addMarker(new MarkerOptions().position(location2).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            map.setInfoWindowAdapter(new MyInfoWindowAdapter("7893 Merritt Course Chicago, USA", "New Campo Agento", getActivity().getLayoutInflater().inflate(R.layout.custom_info_contents, null)));
            marker.showInfoWindow();
        }

    }

    @OnTouch(R.id.title)
    public boolean performBackClick(MotionEvent event) {
        if (event.getRawX() <= (back.getCompoundDrawables()[0].getBounds().width())) {
            replaceFragment.finishCurrentActivity();
            return true;
        }
        return false;
    }

    private void addItemToPager() {
        ArrayList<ExploreCityPagerModel> data = new ArrayList<>();
        data.add(new ExploreCityPagerModel(new ExploreCityChildsFragment(), getResources().getString(R.string.eat)));
        data.add(new ExploreCityPagerModel(new ExploreCityChildsFragment(), getResources().getString(R.string.drink)));
        data.add(new ExploreCityPagerModel(new ExploreCityChildsFragment(), getResources().getString(R.string.attraction)));
        data.add(new ExploreCityPagerModel(new ExploreCityChildsFragment(), getResources().getString(R.string.events)));
        exploreCityGuidePagerAdapter.setItems(data);
        exploreCityGuidePagerAdapter.notifyDataSetChanged();
        pager.setCurrentItem(1);
    }
}
