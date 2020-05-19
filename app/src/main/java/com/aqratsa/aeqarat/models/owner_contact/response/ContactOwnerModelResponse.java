package com.aqratsa.aeqarat.models.owner_contact.response;

import com.google.gson.annotations.SerializedName;

public class ContactOwnerModelResponse {

    @SerializedName("data")
    private Result mResult;

    private String message;

    private String key;

    public Result getData ()
    {
        return mResult;
    }

    public void setData (Result result)
    {
        this.mResult = mResult;
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
        return "ClassPojo [data = "+mResult+", message = "+message+", key = "+key+"]";
    }
}
