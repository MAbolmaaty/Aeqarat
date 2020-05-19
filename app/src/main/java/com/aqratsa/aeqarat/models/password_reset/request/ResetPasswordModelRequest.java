package com.aqratsa.aeqarat.models.password_reset.request;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordModelRequest {

    private String email;
    @SerializedName("password")
    private String newPassword;
    @SerializedName("password_confirmation")
    private String confirmPassword;
    @SerializedName("lang")
    private String locale;

    public ResetPasswordModelRequest(String email, String newPassword, String confirmPassword, String locale) {
        this.email = email;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
        this.locale = locale;
    }
}
