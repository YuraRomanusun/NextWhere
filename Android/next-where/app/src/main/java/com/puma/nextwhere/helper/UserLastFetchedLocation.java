package com.puma.nextwhere.helper;

/**
 * Created by rajesh on 19/11/17.
 */

public class UserLastFetchedLocation {
    static UserLastFetchedLocation userLastFetchedLocation = new UserLastFetchedLocation();

    private UserLastFetchedLocation() {

    }

    public static UserLastFetchedLocation getInstance() {
        return userLastFetchedLocation;
    }



}
