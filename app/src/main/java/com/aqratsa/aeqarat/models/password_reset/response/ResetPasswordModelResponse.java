package com.aqratsa.aeqarat.models.password_reset.response;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordModelResponse {

    @SerializedName("message")
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
        return "ClassPojo [message = "+result+", key = "+key+"]";
    }
}
