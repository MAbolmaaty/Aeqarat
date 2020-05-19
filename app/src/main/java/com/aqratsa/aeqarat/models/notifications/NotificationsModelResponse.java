package com.aqratsa.aeqarat.models.notifications;

import com.google.gson.annotations.SerializedName;

public class NotificationsModelResponse {

    @SerializedName("data")
    private Notification[] mNotifications;

    public Notification[] getNotifications ()
    {
        return mNotifications;
    }
}
