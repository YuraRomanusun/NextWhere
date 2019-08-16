package com.puma.nextwhere.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.puma.nextwhere.BuildConfig;
import com.puma.nextwhere.R;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.CheckPermission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestingPermission extends AppCompatActivity {

    @BindView(R.id.layout)
    ConstraintLayout layout;
    @BindView(R.id.text_permission)
    TextView permissionFor;
    @BindView(R.id.hidingParent)
    View hidingParent;
    int permissionCheckCode;
    public static final String EXTRA_REQUEST_CODE = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_checker);
        ButterKnife.bind(this);
        permissionCheckCode = getIntent().getIntExtra(EXTRA_REQUEST_CODE, 0);
    }

    private void checkPermissionHere() {
        switch (permissionCheckCode) {
            case AppConstants.PERMISSION_CHECK_CODE.LOCATION:
                permissionFor.setText(getString(R.string.error_permission_location));
                requestLocationPermission();
                break;
        }
    }

    @OnClick(R.id.requestPermissionAgain)
    public void requestPermission() {
        switch (permissionCheckCode) {
            case AppConstants.PERMISSION_CHECK_CODE.LOCATION:
                requestLocationPermission();
                break;
        }

    }

    public void requestLocationPermission() {
        if (!CheckPermission.getCheckPermission().isLocationPermissionGranted(this)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, AppConstants.PERMISSION_CHECK_CODE.LOCATION);
        } else {
            finishActivityNormally();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissionHere();
    }

    private void finishActivityNormally() {
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == AppConstants.PERMISSION_CHECK_CODE.LOCATION) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                finish();
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                finishActivityNormally();
            } else {
                hidingParent.setVisibility(View.GONE);

                Snackbar.make(layout, "Permission Decline, Ask again", Snackbar.LENGTH_LONG)
                        .setAction("Try Again", v -> goToSetting()).show();
            }
        }

    }

    private void goToSetting() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null);
        intent.setData(uri);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
