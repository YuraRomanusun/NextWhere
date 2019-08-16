package com.puma.nextwhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Guideline;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.puma.nextwhere.R;
import com.puma.nextwhere.activity.RequestingPermission;
import com.puma.nextwhere.adapter.ExploreCityGuidePagerAdapter;
import com.puma.nextwhere.apicallbacks.IDirectionDataCallback;
import com.puma.nextwhere.apicallbacks.ITripDetailCallback;
import com.puma.nextwhere.fetchuserlocation.FetchUserLocation;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.CheckPermission;
import com.puma.nextwhere.helper.ExploreCityChildInstanceHolder;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.interfaces.ReplaceFragment;
import com.puma.nextwhere.model.DirectionData;
import com.puma.nextwhere.model.DirectionModels.Route;
import com.puma.nextwhere.model.DirectionModels.RouteLeg;
import com.puma.nextwhere.model.DirectionModels.RouteLegStep;
import com.puma.nextwhere.model.ExploreCityPagerModel;
import com.puma.nextwhere.model.HotelDetails;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.network.ServiceNetwork;
import com.puma.nextwhere.preference.Preference;
import com.puma.nextwhere.presenter.DataPresontar;
import com.puma.nextwhere.services.FetchUserAddress;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

import static com.puma.nextwhere.activity.RequestingPermission.EXTRA_REQUEST_CODE;

/**
 * Created by rajesh on 31/5/17.
 */

public class ExploreCityGuideFragment extends Fragment implements OnMapReadyCallback,
        ExploreCityChildInstanceHolder.Callback, ITripDetailCallback, IDirectionDataCallback {
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.viewPager)
    ViewPager pager;

    @BindView(R.id.mapLayout)
    MapView mapView;

    @BindView(R.id.guideline)
    Guideline guideLine;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.title)
    TextView back;

    public FetchUserLocation mWorkFragment;
    private ExploreCityGuidePagerAdapter exploreCityGuidePagerAdapter;
    private GoogleMap map;
    private int clickCount;
    private Context context;
    private LatLng latLng;
    private AddressResultReceiver mResultReceiver;
    private Marker marker;
    private ArrayList<Marker> markers = new ArrayList<>();
    private ArrayList<Polyline> polylines = new ArrayList<>();
    private HotelDetails hotelDetails;
    private LoginApiResponse loginApiResponse;
    public static Location location;
    private Unbinder unbinder;
    private ReplaceFragment replaceFragment;


    @OnClick(R.id.extendMap)
    public void extendMap() {
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) guideLine.getLayoutParams();
        if (clickCount % 2 == 0) {
            params.guidePercent = 0.70f; // 45% // range: 0 <-> 1
            guideLine.setLayoutParams(params);
        } else {
            params.guidePercent = 0.30f; // 30% // range: 0 <-> 1
            guideLine.setLayoutParams(params);
        }
        clickCount++;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        replaceFragment = (ReplaceFragment) context;
    }


    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
        replaceFragment = (ReplaceFragment) activity;

    }

    @OnTouch(R.id.title)
    public boolean performBackClick(MotionEvent event) {
        if (event.getRawX() <= (back.getCompoundDrawables()[0].getBounds().width())) {
            replaceFragment.finishCurrentActivity();
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explorecityguide, container, false);
        unbinder = ButterKnife.bind(this, view);
        ExploreCityChildInstanceHolder.getInstance().initializeCallback(this);
        exploreCityGuidePagerAdapter = new ExploreCityGuidePagerAdapter(getChildFragmentManager());
        pager.setAdapter(exploreCityGuidePagerAdapter);
        tabLayout.setupWithViewPager(pager, true);
        loadMap();
        fetchUserLocation();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @OnClick(R.id.hotelImage)
    public void hotelLocation() {
        if (hotelDetails == null) {
            getHotelDetails();
        } else {
            addMarkerToMap(loginApiResponse.getHotel(),
                    new LatLng(Utils.parseDouble(hotelDetails.getLatitud()),
                            Utils.parseDouble(hotelDetails.getLongitud())));
        }
    }


    private void fetchUserLocation() {
        if (CheckPermission.getCheckPermission().isLocationPermissionGranted(context)) {
            FragmentManager fm = replaceFragment.getSupportFragmentManager();
            // Check to see if we have retained the worker fragment.
            mWorkFragment = (FetchUserLocation) fm.findFragmentByTag("work");
            // If not retained (or first time running), we need to create it.
            if (mWorkFragment == null) {
                mWorkFragment = new FetchUserLocation();
                // Tell it who it is working with.
                fm.beginTransaction().add(mWorkFragment, "work").commit();
            }
            mWorkFragment.initializeCallback(new FetchUserLocation.LocationCallbackToClass() {
                @Override
                public void userLocation(Location mCurrentLocation, String address) {
                    location = mCurrentLocation;
                    addItemToPager();
                    // fm.beginTransaction().remove(fm.findFragmentByTag("work")).commit();
                }

                @Override
                public void error() {
                    addItemToPager();
                }
            }, false);
        } else {
            Intent intent = new Intent(context, RequestingPermission.class);
            intent.putExtra(EXTRA_REQUEST_CODE, AppConstants.PERMISSION_CHECK_CODE.LOCATION);
            startActivityForResult(intent, AppConstants.PERMISSION_CHECK_CODE.LOCATION);
        }
    }

    private void getHotelDetails() {
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeViewMyTripCallback(this);
        loginApiResponse = Preference.getInstance().getUserInfo();
        dataPresontar.setHotelInfo(loginApiResponse.getIdHotel());
    }

    private void loadMap() {
        mapView.onCreate(null);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        this.map = map;

        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                if( (int) marker.getTag() == 1) {

                    showDirection();
                }
            }
        });
    }

    private void updateMap(LatLng location, String nombreDestino) {
//        CameraUpdate center = CameraUpdateFactory.newLatLng(location);
//        CameraUpdate zoom = CameraUpdateFactory.newLatLngZoom(location, 20.0f);
//
//        map.moveCamera(center);
//        map.animateCamera(zoom);

        Log.d("Destino",nombreDestino);

        removeMarkers();
        removePolylines();


        // Add Current location to map
        LatLng currentLocation = new LatLng(ExploreCityGuideFragment.location.getLatitude(), ExploreCityGuideFragment.location.getLongitude());
        appendMarketToMap(getResources().getString(R.string.your_location),currentLocation, false, 0);

        // Add destiny location to map
        appendMarketToMap(nombreDestino, location, true, 1);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker1 : markers) {
            builder.include(marker1.getPosition());
        }
        LatLngBounds bounds = builder.build();
        int padding = 100; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        map.moveCamera(cu);
        //map.getUiSettings().setMapToolbarEnabled(true);

        //startIntentService(location);
        drawRoute(currentLocation, location);

    }

    private void showDirection() {
        Log.d("Fragment marker title", markers.get(1).getTitle());
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("geo:0,0?q=" + String.valueOf(markers.get(1).getPosition().latitude)  + "," + String.valueOf(markers.get(1).getPosition().longitude) + " (" + markers.get(1).getTitle() + ")"));
        startActivity(intent);
    }

    private void drawRoute(LatLng location1, LatLng location2) {
        DataPresontar dataPresontar = new DataPresontar(new ServiceNetwork());
        dataPresontar.initializeDirectionDataCallBack(this);

        dataPresontar.getDirectionData(location1.latitude + "," + location1.longitude,
                location2.latitude + "," + location2.longitude,
                false, getString(R.string.google_maps_key)
        );
    }

    private void removeMarkers() {
        for (Marker marker1 : markers) {
            marker1.remove();
        }
        markers.clear();
    }

    private void removePolylines() {
        for (Polyline polyline : polylines) {
            polyline.remove();
        }
        polylines.clear();
    }

    private void appendMarketToMap(String title, LatLng location, boolean setClick, int position) {
        latLng = location;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.title(title);
        markerOptions.position(location);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker));

        if(setClick) {
            markerOptions.snippet(getString(R.string.get_driving_direction).toUpperCase());
        }

        Marker newMarker =  map.addMarker(markerOptions);
        final Handler handler = new Handler();
        final long start = SystemClock.uptimeMillis();
        final long duration = 1500;
        final Interpolator interpolator = new BounceInterpolator();
        handler.post(new Runnable() {
            @Override
            public void run() {
                long elapsed = SystemClock.uptimeMillis() - start;
                float t = Math.max(
                        1 - interpolator.getInterpolation((float) elapsed / duration), 0);
                newMarker.setAnchor(0.5f, 1.0f + 2 * t);
                if (t > 0.0)
                    // Post again 16ms later.
                    handler.postDelayed(this, 16);
            }
        });
        newMarker.showInfoWindow();
        newMarker.setTag(position);
        markers.add(newMarker);

    }

    private void addMarkerToMap(String title, LatLng location) {
//        latLng = location;
//        if (marker != null) {
//            marker.remove();
//        }
//        marker = map.addMarker(new MarkerOptions()
//                .title(title)
//                .position(location)
//                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
//        final Handler handler = new Handler();
//        final long start = SystemClock.uptimeMillis();
//        final long duration = 1500;
//        final Interpolator interpolator = new BounceInterpolator();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                long elapsed = SystemClock.uptimeMillis() - start;
//                float t = Math.max(
//                        1 - interpolator.getInterpolation((float) elapsed / duration), 0);
//                marker.setAnchor(0.5f, 1.0f + 2 * t);
//                if (t > 0.0)
//                    // Post again 16ms later.
//                    handler.postDelayed(this, 16);
//            }
//        });
//        marker.showInfoWindow();
    }


    private void addItemToPager() {
        ArrayList<ExploreCityPagerModel> data = new ArrayList<>();
        data.add(new ExploreCityPagerModel(ExploreCityChildsFragment.getInstance(AppConstants.EAT, location), getResources().getString(R.string.eat)));
        data.add(new ExploreCityPagerModel(ExploreCityChildsFragment.getInstance(AppConstants.DRINK, location), getResources().getString(R.string.drink)));
        data.add(new ExploreCityPagerModel(ExploreCityChildsFragment.getInstance(AppConstants.ATTRACTION, location), getResources().getString(R.string.attraction)));
        data.add(new ExploreCityPagerModel(new ExploreEventGuideFragment(), getResources().getString(R.string.events)));
        exploreCityGuidePagerAdapter.setItems(data);
        exploreCityGuidePagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void setLocationOnMap(double lat, double log, String nombreDestino) {
        LatLng location = new LatLng(lat, log);
        if (map == null) {
            mapView.getMapAsync(googleMap -> {
                map = googleMap;
                updateMap(location, nombreDestino);
            });
        } else {
            updateMap(location, nombreDestino);
        }
    }


    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
        if (mWorkFragment != null) {
            mWorkFragment.removeCallback();
        }
        this.context = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    protected void startIntentService(LatLng location) {
        if (mResultReceiver == null) {
            mResultReceiver = new AddressResultReceiver(new Handler());
        }
        Intent intent = new Intent(context, FetchUserAddress.class);
        intent.putExtra(AppConstants.RECEIVER, mResultReceiver);
        intent.putExtra(AppConstants.LOCATION_DATA_EXTRA, location);
        context.startService(intent);
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
            Utils.showToast(context, appErrorMessage);
        }
    }

    @Override
    public void setHotelDetails(HotelDetails data) {
        this.hotelDetails = data;
        addMarkerToMap(loginApiResponse.getHotel(), new LatLng(Utils.parseDouble(data.getLatitud()),
                Utils.parseDouble(data.getLongitud())));
    }

    @Override
    public void setDirectionData(DirectionData directionData) {
        Log.d("From Fragment", "Answer received");

        if(directionData.getRoutes().size() > 0) {

            Route route = directionData.getRoutes().get(0);

            if(route.getLegs().size() > 0) {

                RouteLeg routeLeg = route.getLegs().get(0);

                if(routeLeg.getSteps().size() > 0) {

                    List<RouteLegStep> routeLegSteps = routeLeg.getSteps();

                    for (RouteLegStep routeLegStep : routeLegSteps) {

                        Log.d("From Fragment - Steps", "Here");

                        Polyline polyline = map.addPolyline(new PolylineOptions()
                                .add(new LatLng(routeLegStep.getStartLocation().getLatitude(), routeLegStep.getStartLocation().getLongitude()),
                                        new LatLng(routeLegStep.getEndLocation().getLatitude(), routeLegStep.getEndLocation().getLongitude()))
                                .width(5)
                                .color(Color.RED));

                        polylines.add(polyline);
                    }

                }

            }
        }

    }

    @Override
    public void showNoDataView() {

    }


    class AddressResultReceiver extends ResultReceiver {
        AddressResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            // Display the address string
            // or an error message sent from the intent service.
            String address = resultData.getString(AppConstants.ADDRESS);
            if (context != null) {
                addMarkerToMap(address, latLng);
            }
        }
    }

}
