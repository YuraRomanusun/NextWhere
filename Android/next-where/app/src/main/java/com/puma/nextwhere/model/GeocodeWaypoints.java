package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kAmikAzE on 1/30/18.
 */

public class GeocodeWaypoints {

    @SerializedName("geocoder_status")
    @Expose
    private String geocoderStatus;

    @SerializedName("place_id")
    @Expose
    private String placeId;

    public String getGeocoderStatus() {
        return geocoderStatus;
    }

    public void setGeocoderStatus(String geocoderStatus) {
        this.geocoderStatus = geocoderStatus;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

}