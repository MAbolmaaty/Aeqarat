package com.aqratsa.aeqarat.models.real_estates.response;

import com.google.gson.annotations.SerializedName;

public class RealEstatesModelResponse {

    @SerializedName("data")
    private RealEstate[] mRealEstates;

    private String key;

    public RealEstate[] getRealEstates ()
    {
        return mRealEstates;
    }

    public String getKey ()
    {
        return key;
    }
}
