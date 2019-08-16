package com.puma.nextwhere.helper;

/**
 * Created by rajesh on 16/9/17.
 */

public class ExploreCityChildInstanceHolder {
    private Callback callback;

    public interface Callback {
        void setLocationOnMap(double lat, double log, String nombreDestino);
    }

    public Callback getCallback() {
        return callback;
    }


    public void initializeCallback(Callback callback) {
        this.callback = callback;
    }

    private static ExploreCityChildInstanceHolder exploreCityChildInstanceHolder;

    private ExploreCityChildInstanceHolder() {
    }

    public static ExploreCityChildInstanceHolder getInstance() {
        if (exploreCityChildInstanceHolder == null) {
            exploreCityChildInstanceHolder = new ExploreCityChildInstanceHolder();
        }
        return exploreCityChildInstanceHolder;
    }
}
