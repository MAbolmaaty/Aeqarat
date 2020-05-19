package com.aqratsa.aeqarat.models.owner_contact.request;

import com.google.gson.annotations.SerializedName;

public class ContactOwnerModelRequest {

    private String message;
    @SerializedName("user_id")
    private String ownerId;
    @SerializedName("lang")
    private String locale;

    public ContactOwnerModelRequest(String message, String ownerId, String locale) {
        this.message = message;
        this.ownerId = ownerId;
        this.locale = locale;
    }
}
