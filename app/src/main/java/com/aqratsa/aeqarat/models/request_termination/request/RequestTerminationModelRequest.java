package com.aqratsa.aeqarat.models.request_termination.request;

import com.google.gson.annotations.SerializedName;

public class RequestTerminationModelRequest {

    @SerializedName("akar_id")
    private String mRealEstateId;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("departure_date")
    private String mExitDate;
    @SerializedName("recovery_way")
    private String mRefundMethod;
    @SerializedName("lang")
    private String mLocale;

    public RequestTerminationModelRequest(String realEstateId, String userId, String exitDate, String refundMethod, String locale) {
        mRealEstateId = realEstateId;
        mUserId = userId;
        mExitDate = exitDate;
        mRefundMethod = refundMethod;
        mLocale = locale;
    }
}
