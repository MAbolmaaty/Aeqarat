package com.aqratsa.aeqarat.models.login.request;

import com.google.gson.annotations.SerializedName;

public class LoginModelRequest {

    @SerializedName("email")
    private String mEmail;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("lang")
    private String mLocale;
    @SerializedName("token")
    private String mFcmToken;
    @SerializedName("device_type")
    private String mDeviceOS;

    public LoginModelRequest(String email, String password, String locale, String fcmToken,
                             String deviceOS) {
        mEmail = email;
        mPassword = password;
        mLocale = locale;
        mFcmToken = fcmToken;
        mDeviceOS = deviceOS;
    }
}
