package com.aqratsa.aeqarat.models.real_estates_owned;

import com.google.gson.annotations.SerializedName;

public class RealEstatesOwnedModelResponse {

    @SerializedName("data")
    private RealEstatesOwned[] mRealEstatesOwned;

    @SerializedName("key")
    private String mStatus;

    public RealEstatesOwned[] getRealEstatesOwned ()
    {
        return mRealEstatesOwned;
    }

    public String getStatus ()
    {
        return mStatus;
    }
}
