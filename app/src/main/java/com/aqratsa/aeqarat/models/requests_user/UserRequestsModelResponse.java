package com.aqratsa.aeqarat.models.requests_user;

import com.google.gson.annotations.SerializedName;

public class UserRequestsModelResponse {

    @SerializedName("data")
    private Request[] mRequests;

    private String key;

    public Request[] getRequests ()
    {
        return mRequests;
    }

    public String getKey ()
    {
        return key;
    }
}
