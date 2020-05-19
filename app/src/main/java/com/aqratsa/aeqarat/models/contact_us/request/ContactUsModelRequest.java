package com.aqratsa.aeqarat.models.contact_us.request;

import com.google.gson.annotations.SerializedName;

public class ContactUsModelRequest {

    @SerializedName("name")
    private String username;
    private String email;
    private String code;
    @SerializedName("phone")
    private String phoneNumber;
    private String message;
    private String title;
    @SerializedName("lang")
    private String locale;

    public ContactUsModelRequest(String username, String email, String code, String phoneNumber,
                                 String message, String title, String locale) {
        this.username = username;
        this.email = email;
        this.code = code;
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.title = title;
        this.locale = locale;
    }
}
