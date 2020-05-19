package com.aqratsa.aeqarat.models.request_rent.response;

import com.google.gson.annotations.SerializedName;

public class RequestRentModelResponse {

    @SerializedName("data")
    private String result;

    private String key;

    public String getResult ()
    {
        return result;
    }

    public void setResult (String result)
    {
        this.result = result;
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
        return "ClassPojo [data = "+result+", key = "+key+"]";
    }
}
