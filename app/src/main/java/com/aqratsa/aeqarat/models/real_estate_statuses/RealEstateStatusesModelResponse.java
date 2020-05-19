package com.aqratsa.aeqarat.models.real_estate_statuses;

import com.google.gson.annotations.SerializedName;

public class RealEstateStatusesModelResponse {

    @SerializedName("data")
    private Status[] mStatuses;

    private String key;

    public Status[] getStatuses ()
    {
        return mStatuses;
    }

    public String getKey ()
    {
        return key;
    }
}
