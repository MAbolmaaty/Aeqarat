package com.aqratsa.aeqarat.models.info_update;

import com.google.gson.annotations.SerializedName;

public class UpdateInfoModelResponse {

    @SerializedName("data")
    private Result result;

    private String message;

    private String key;

    public Result getResult ()
    {
        return result;
    }

    public void setResult (Result result)
    {
        this.result = result;
    }

    public String getMessage ()
    {
        return message;
    }

    public void setMessage (String message)
    {
        this.message = message;
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
        return "ClassPojo [result = "+result+", message = "+message+", key = "+key+"]";
    }
}
