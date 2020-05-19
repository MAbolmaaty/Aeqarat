package com.aqratsa.aeqarat.models.email_confirm.request;

import com.google.gson.annotations.SerializedName;

public class ConfirmEmailModelRequest {

    private String email;
    @SerializedName("lang")
    private String locale;

    public ConfirmEmailModelRequest(String email, String locale) {
        this.email = email;
        this.locale = locale;
    }
}
