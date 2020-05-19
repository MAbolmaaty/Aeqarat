package com.aqratsa.aeqarat.models.email_confirm.response;

import com.google.gson.annotations.SerializedName;

public class ConfirmEmailModelResponse {

    private String code;

    @SerializedName("message")
    private String result;

    private String key;

    public String getCode ()
    {
        return code;
    }

    public void setCode (String code)
    {
        this.code = code;
    }

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
        return "ClassPojo [code = "+code+", message = "+result+", key = "+key+"]";
    }
}
