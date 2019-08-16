package com.puma.nextwhere.helper;

import com.puma.nextwhere.model.LoginApiResponse;
import com.puma.nextwhere.preference.Preference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by rajesh on 8/10/17.
 */

public class NonUiOpertion {

    private static final NonUiOpertion ourInstance = new NonUiOpertion();

    public static NonUiOpertion getInstance() {
        return ourInstance;
    }

    private NonUiOpertion() {
    }

    public boolean didShowTripVideo(LoginApiResponse loginApiResponse) {
        if (loginApiResponse == null || loginApiResponse.getFechaDesde() == null ||
                loginApiResponse.getFechaDesde().isEmpty()) {
            return false;
        }
        if (Preference.getInstance().isVideoShownOnDate(loginApiResponse.getFechaDesde())) {
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = simpleDateFormat.format(calendar.getTime());
        return loginApiResponse.getFechaDesde().equalsIgnoreCase(currentDate);
    }

}
