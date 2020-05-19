package com.aqratsa.aeqarat.models.favorites;

import com.google.gson.annotations.SerializedName;

public class FavoritesModelResponse {

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
