package com.puma.nextwhere.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.puma.nextwhere.model.DirectionModels.Route;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kAmikAzE on 1/30/18.
 */

public class DirectionData {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("geocoded_waypoints")
    @Expose
    private List<GeocodeWaypoints> geocodedWaypoints;

    @SerializedName("routes")
    @Expose
    private List<Route> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodeWaypoints> getGeocodedWaypoints() {
        return geocodedWaypoints;
    }

    public void setGeocodedWaypoints(List<GeocodeWaypoints> geocodedWaypoints) {
        this.geocodedWaypoints = geocodedWaypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
