package com.puma.nextwhere.helper;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * Created by rajesh on 18/11/17.
 */

public class CheckPermission {
    private static CheckPermission checkPermission = new CheckPermission();

    public static CheckPermission getCheckPermission() {
        return checkPermission;
    }

    public boolean isLocationPermissionGranted(Context activity) {
        return ContextCompat.checkSelfPermission(activity,

                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(activity,

                Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED;
    }
}
