package com.puma.nextwhere.model.DirectionModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kAmikAzE on 1/30/18.
 */

public class RouteLeg {

    @SerializedName("start_location")
    @Expose
    private RouteLegLocation startLocation;

    @SerializedName("end_location")
    @Expose
    private RouteLegLocation endLocation;

    @SerializedName("steps")
    @Expose
    private List<RouteLegStep> steps;

    @SerializedName("polyline")
    @Expose
    private RouteLegPolyline polyline;

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

    public List<RouteLegStep> getSteps() {
        return steps;
    }

    public void setSteps(List<RouteLegStep> steps) {
        this.steps = steps;
    }
}