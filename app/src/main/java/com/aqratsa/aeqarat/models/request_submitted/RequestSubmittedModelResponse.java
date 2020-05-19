package com.aqratsa.aeqarat.models.request_submitted;

import com.google.gson.annotations.SerializedName;

public class RequestSubmittedModelResponse {

    @SerializedName("data")
    private Result result;

    private String key;

    public Result getResult ()
    {
        return result;
    }

    public void setResult (Result data)
    {
        this.result = data;
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
