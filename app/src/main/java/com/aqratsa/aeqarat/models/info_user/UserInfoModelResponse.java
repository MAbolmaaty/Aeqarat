package com.aqratsa.aeqarat.models.info_user;

import com.google.gson.annotations.SerializedName;

public class UserInfoModelResponse {

    @SerializedName("data")
    private User user;

    private String key;

    public User getUser ()
    {
        return user;
    }

    public void setUser (User user)
    {
        this.user = user;
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
        return "ClassPojo [user = "+user+", key = "+key+"]";
    }
}
