package com.aqratsa.aeqarat.models.password_forget.request;

import com.google.gson.annotations.SerializedName;

public class ForgetPasswordModelRequest {

    private String email;
    @SerializedName("lang")
    private String locale;

    public ForgetPasswordModelRequest(String email, String locale) {
        this.email = email;
        this.locale = locale;
    }
}
