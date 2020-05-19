package com.aqratsa.aeqarat.models.regions;

import com.google.gson.annotations.SerializedName;

public class RegionsModelResponse {
    @SerializedName("data")
    private String[] regions;

    private String key;

    public String[] getRegions ()
    {
        return regions;
    }

    public void setRegions (String[] regions)
    {
        this.regions = regions;
    }

    public String getKey ()
    {
        return key;
    }

    public void setKey (String key)
    {
        this.key = key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [regions = "+regions+", key = "+key+"]";
    }
}
