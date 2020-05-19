package com.aqratsa.aeqarat.models.search.response;

import com.google.gson.annotations.SerializedName;

public class SearchModelResponse {

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
