package com.aqratsa.aeqarat.models.districts;

import com.google.gson.annotations.SerializedName;

public class DistrictsModelResponse {
    @SerializedName("data")
    private String[] districts;

    private String key;

    public String[] getDistricts ()
    {
        return districts;
    }

    public void setDistricts (String[] districts)
    {
        this.districts = districts;
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
        return "ClassPojo [districts = "+districts+", key = "+key+"]";
    }
}
