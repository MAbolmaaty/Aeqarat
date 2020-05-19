package com.aqratsa.aeqarat.models.login.response;

import com.google.gson.annotations.SerializedName;

public class LoginModelResponse {

    @SerializedName("data")
    private User mUser;

    private String message;

    private String key;

    public LoginModelResponse(String message, String key) {
        this.message = message;
        this.key = key;
    }

    public User getUser()
    {
        return mUser;
    }

    public String getMessage ()
    {
        return message;
    }

    public String getKey ()
    {
        return key;
    }
}
