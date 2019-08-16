package com.puma.nextwhere.preference;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.puma.nextwhere.NextwhereApplication;
import com.puma.nextwhere.model.LoginApiResponse;

import static com.puma.nextwhere.preference.PrefConst.RESERVATION_CODE;
import static com.puma.nextwhere.preference.PrefConst.SAVED_IMAGES;

/**
 * Created by HGS on 12/11/2015.
 */
public class Preference {

    private static final String FILE_NAME = "Campus.pref";

    private static Preference mInstance = null;

    public static Preference getInstance() {
        if (mInstance == null) {
            mInstance = new Preference();
        }
        return mInstance;
    }

    private SharedPreferences getDefaultSharedPreference() {
        return PreferenceManager.getDefaultSharedPreferences(NextwhereApplication.getInstance().getContext());
    }

    public void putLoginDetails(LoginApiResponse loginApiResponse) {
        getDefaultSharedPreference().edit().putString(PrefConst.USER_INFO, new Gson().toJson(loginApiResponse)).apply();
    }

    public LoginApiResponse getUserInfo() {
        String defaultValue = getDefaultSharedPreference().getString(PrefConst.USER_INFO, "");
        return defaultValue.isEmpty() ? null : new Gson().fromJson(defaultValue, LoginApiResponse.class);
    }

    public void put(Context context, String key, String value) {


        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putString(key, value);
        editor.apply();
    }

    public void put(Context context, String key, boolean value) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(key, value);
        editor.apply();
    }

    public void put(Context context, String key, int value) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putInt(key, value);
        editor.apply();
    }

    public void put(Context context, String key, long value) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putLong(key, value);
        editor.apply();
    }

    public String getValue(Context context, String key, String defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getString(key, defaultValue);
        } catch (Exception e) {

            return defaultValue;
        }
    }

    public int getValue(Context context, String key, int defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getInt(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean getValue(Context context, String key, boolean defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getBoolean(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public long getValue(Context context, String key, long defaultValue) {

        SharedPreferences pref = context.getSharedPreferences(FILE_NAME, Activity.MODE_PRIVATE);

        try {
            return pref.getLong(key, defaultValue);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public boolean isVideoShownOnDate(String fechaDesde) {
        return getDefaultSharedPreference().getBoolean(fechaDesde, false);
    }

    public void setVideoWatched(String fechaDesde) {
        getDefaultSharedPreference().edit().putBoolean(fechaDesde, true).apply();
    }

    public void saveReservationCode(String reservationValue) {
        getDefaultSharedPreference().edit().putString(RESERVATION_CODE, reservationValue).apply();
    }

    public String getReservationCode() {
       return getDefaultSharedPreference().getString(RESERVATION_CODE, "");
    }

    public void savePicturePath(String absolutePath) {
        String oldImages = getOldImages();
        oldImages = oldImages + "," + absolutePath;
        getDefaultSharedPreference().edit().putString(SAVED_IMAGES, oldImages).apply();
    }

    public String getOldImages() {
        return getDefaultSharedPreference().getString(SAVED_IMAGES, "");
    }
}
