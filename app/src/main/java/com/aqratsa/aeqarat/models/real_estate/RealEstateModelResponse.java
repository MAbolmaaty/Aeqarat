package com.aqratsa.aeqarat.models.real_estate;

import com.google.gson.annotations.SerializedName;

public class RealEstateModelResponse {

    @SerializedName("data")
    private RealEstate mRealEstate;

    @SerializedName("akar_type")
    private String mType;

    private String key;

    public RealEstate getRealEstate() {
        return mRealEstate;
    }

    public String getType() {
        return mType;
    }

    public String getKey() {
        return key;
    }
}
