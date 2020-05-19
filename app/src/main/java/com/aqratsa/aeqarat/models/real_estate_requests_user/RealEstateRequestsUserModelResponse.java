package com.aqratsa.aeqarat.models.real_estate_requests_user;

import com.google.gson.annotations.SerializedName;

public class RealEstateRequestsUserModelResponse {

    @SerializedName("data")
    private Request[] mRequests;
    @SerializedName("key")
    private String mKey;

    public Request[] getRequests ()
    {
        return mRequests;
    }

    public String getKey ()
    {
        return mKey;
    }


}
