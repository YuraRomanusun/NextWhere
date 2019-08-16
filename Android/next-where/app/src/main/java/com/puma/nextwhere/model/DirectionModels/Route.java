package com.puma.nextwhere.model.DirectionModels;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by kAmikAzE on 1/30/18.
 */

public class Route {

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("legs")
    @Expose
    private List<RouteLeg> legs;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<RouteLeg> getLegs() {
        return legs;
    }

    public void setLegs(List<RouteLeg> legs) {
        this.legs = legs;
    }
}
