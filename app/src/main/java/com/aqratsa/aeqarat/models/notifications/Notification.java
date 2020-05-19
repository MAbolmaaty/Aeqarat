package com.aqratsa.aeqarat.models.notifications;

import com.google.gson.annotations.SerializedName;

public class Notification {

    private String updated_at;

    private String user_id;

    private String created_at;

    private String id;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("message")
    private String mMessage;

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public String getUser_id ()
    {
        return user_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public String getId ()
    {
        return id;
    }

    public String getTitle ()
    {
        return mTitle;
    }

    public String getMessage ()
    {
        return mMessage;
    }
}
