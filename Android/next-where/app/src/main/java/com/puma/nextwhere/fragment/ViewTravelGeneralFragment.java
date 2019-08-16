package com.puma.nextwhere.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.puma.nextwhere.R;
import com.puma.nextwhere.activity.ImagePickerActivity;
import com.puma.nextwhere.adapter.UserImageAdapter;
import com.puma.nextwhere.database.databaseoperation.TravelJournalDbOperation;
import com.puma.nextwhere.database.tables.TravelJournalInfo;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;
import com.puma.nextwhere.imagecompressor.Compressor;
import com.puma.nextwhere.interfaces.ReplaceFragment;
import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.model.LoginOtherInfo;
import com.puma.nextwhere.model.UserImageModel;
import com.puma.nextwhere.preference.Preference;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import butterknife.Unbinder;

/**
 * Created by rajesh on 31/5/17.
 */

public class ViewTravelGeneralFragment extends Fragment implements OnMapReadyCallback, UserImageAdapter.TakePictureCallback {

    @BindView(R.id.edit_moneySpent)
    EditText editMoney;

    @BindView(R.id.et_fav)
    EditText editFavourite;

    @BindView(R.id.et_like)
    EditText editLike;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.mapLayout)
    MapView mapView;

    @BindView(R.id.title)
    TextView back;

    @BindView(R.id.userName)
    EditText userName;

    @BindView(R.id.userPassport)
    EditText userPassport;

    @BindView(R.id.userImage)
    ImageView userImage;

    ArrayList<UserImageModel> userImages;
    UserImageAdapter userImageAdapter;
    ReplaceFragment replaceFragment;
    private Unbinder unbinder;
    Context context;
    private final int IMAGER_RESULT = 1;

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
        View view = inflater.inflate(R.layout.fragment_viewtravelgeneral, container, false);
        unbinder = ButterKnife.bind(this, view);
        LoginApiResponse loginApiResponse = com.puma.nextwhere.preference.Preference.getInstance().getUserInfo();
        if (Utils.validateList(loginApiResponse.getPasajeros())) {
            StringBuilder userNameToSet = new StringBuilder();
            for (LoginOtherInfo loginOtherInfo : loginApiResponse.getPasajeros()) {
                if (loginOtherInfo.getNombre() != null && !loginApiResponse.getNombre().isEmpty()) {
                    userNameToSet.append(loginOtherInfo.getNombre().toUpperCase()).append(",");
                }
            }
            String finalValue = userNameToSet.toString();
            if (finalValue.endsWith(",")) {
                finalValue = finalValue.substring(0, finalValue.length() - 1);
            }
            userName.setText(finalValue);
        }
        userPassport.setText(loginApiResponse.getHastaAsignado() == null ? "" : getString(R.string.traveling_to, loginApiResponse.getHastaAsignado()));
        userImages = new ArrayList<>();
        userImageAdapter = new UserImageAdapter(userImages);
        userImageAdapter.initializeCallback(this);
        recyclerView.setAdapter(userImageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        addItemToList();
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

    @OnTouch(R.id.title)
    public boolean performBackClick(MotionEvent event) {
        if (event.getRawX() <= (back.getCompoundDrawables()[0].getBounds().width())) {
            replaceFragment.finishCurrentActivity();
            return true;
        }
        return false;
    }

    private void addItemToList() {
        TravelJournalDbOperation.getInstance()
                .getTravelJournalData(new TravelJournalDbOperation.DatabaseCallback() {
                    @Override
                    public void onSuccess(Object data) {
                        TravelJournalInfo travelJournalInfo = (TravelJournalInfo) data;
                        editMoney.setText(travelJournalInfo.getPrice());
                        editFavourite.setText(travelJournalInfo.getFavourite());
                        editLike.setText(travelJournalInfo.getLike());
                        if (!travelJournalInfo.getUserName().isEmpty()) {
                            userName.setText(travelJournalInfo.getUserName());
                        }

                        if (!travelJournalInfo.getAddress().isEmpty()) {
                            userPassport.setText(travelJournalInfo.getAddress());
                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });
        String oldImages = Preference.getInstance().getOldImages();
        if (oldImages.isEmpty()) {
            // If user captured image then we pass File otherwise null
            setDefaultImage();
        } else {
            String[] imagePath = oldImages.split(",");
            for (String images : imagePath) {
                if (!images.isEmpty()) {
                    File file = new File(images);
                    if (file.exists()) {
                        userImages.add(new UserImageModel(true, file));
                    }
                }
            }
            userImageAdapter.notifyDataSetChanged();
        }
    }

    private void setDefaultImage() {
        userImages.add(new UserImageModel(false, null));
        userImageAdapter.notifyItemInserted(1);
    }

    private void loadMap() {
        mapView.onCreate(null);
        mapView.getMapAsync(this);
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
        this.context = null;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        String[] values = "10,10".split(",");
        if (values.length != 2)
            return;
        mapView.setVisibility(View.VISIBLE);
        LatLng location = new LatLng(Long.parseLong(values[0]), Long.parseLong(values[1]));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 13f));
        map.addMarker(new MarkerOptions().position(location).icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @OnClick(R.id.more)
    public void addImages() {
        takePicture();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK && requestCode == IMAGER_RESULT) {
            UserImageModel userImageModel = new UserImageModel(true,
                    Compressor.getDefault().compressToFile(new File(data.getStringExtra("filePath"))));
            Preference.getInstance().savePicturePath(userImageModel.getImagePath().getAbsolutePath());
            if (!userImages.get(0).isFile()) {
                userImages.remove(0);
                userImageAdapter.notifyItemRemoved(0);
            }
            userImages.add(userImageModel);
            userImageAdapter.notifyItemInserted(userImages.size());
        }
    }


    @Override
    public void takePicture() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.KEY, AppConstants.CAMERA);
        startActivityForResult(intent, IMAGER_RESULT);
    }

    @Override
    public void removePicture(int position) {

        userImages.remove(position);
        userImageAdapter.notifyItemRemoved(position);
        String images = userImages.toString();

        // Updating images to cache
        if (images.length() > 2) {
            Preference.getInstance().savePicturePath(images.substring(1, images.length() - 1));
        }

        position--;
        if (position > -1) {
            userImageAdapter.notifyItemChanged(position);
        }
        if (userImages.size() == 0) {
            setDefaultImage();
        }

    }

    @OnClick(R.id.downloadTicket)
    public void saveInfo() {
        TravelJournalInfo travelJournalInfo = new TravelJournalInfo(editMoney.getText().toString(),
                editFavourite.getText().toString()
                , editLike.getText().toString(), userName.getText().toString(),
                userPassport.getText().toString());

        TravelJournalDbOperation.getInstance().insert(travelJournalInfo, null);
        Utils.showToast(context, getString(R.string.recordUpdated));
    }
}
