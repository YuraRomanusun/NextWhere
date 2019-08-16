package com.puma.nextwhere.model.DirectionModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by kAmikAzE on 1/30/18.
 */

public class RouteLegStep {

    @SerializedName("travel_mode")
    @Expose
    private String travelMode;

    @SerializedName("start_location")
    @Expose
    private RouteLegLocation startLocation;

    @SerializedName("end_location")
    @Expose
    private RouteLegLocation endLocation;


    @SerializedName("polyline")
    @Expose
    private RouteLegPolyline polyline;

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public RouteLegLocation getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(RouteLegLocation startLocation) {
        this.startLocation = startLocation;
    }

    public RouteLegLocation getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(RouteLegLocation endLocation) {
        this.endLocation = endLocation;
    }

    public RouteLegPolyline getPolyline() {
        return polyline;
    }

    public void setPolyline(RouteLegPolyline polyline) {
        this.polyline = polyline;
    }
}
