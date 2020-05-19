package com.aqratsa.aeqarat.models.password_forget.response;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordModelResponse {

    private String code;

    @SerializedName("message")
    private String result;

    private String key;

    private String email;

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

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [code = "+code+", result = "+result+", email = "+email+", key = "+key+"]";
    }
}
