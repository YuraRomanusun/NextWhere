package com.puma.nextwhere.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.puma.nextwhere.helper.AppConstants;
import com.puma.nextwhere.helper.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.puma.nextwhere.helper.AppConstants.SUCCESS_RESULT;

/**
 * Created by rajesh on 15/11/17.
 */

public class FetchUserAddress extends IntentService {
    protected ResultReceiver mReceiver;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    public FetchUserAddress() {
        super(FetchUserAddress.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        // Get the location passed to this service through an extra.
        // Get the location passed to this service through an extra.
        LatLng location = intent.getParcelableExtra(AppConstants.LOCATION_DATA_EXTRA);
        mReceiver = intent.getParcelableExtra(AppConstants.RECEIVER);

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.latitude, location.longitude,
                    // In this sample, get just a single address.
                    1);
        } catch (IOException | IllegalArgumentException ioException) {
            // Catch network or other I/O problems.
        }

        // Handle case where no address was found.
        if (Utils.validateList(addresses)) {
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<>();

            // Fetch the address lines using getAddressLine,
            // join them, and send them to the thread.
            for (int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                addressFragments.add(address.getAddressLine(i));
            }
            deliverResultToReceiver(TextUtils.join(System.getProperty("line.separator"), addressFragments));
        }
    }

    private void deliverResultToReceiver(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(AppConstants.ADDRESS, message);
        mReceiver.send(SUCCESS_RESULT, bundle);
    }
}


